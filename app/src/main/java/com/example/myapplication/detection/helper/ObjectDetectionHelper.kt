package com.example.myapplication.detection.helper

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.view.Surface
import androidx.camera.core.ImageProxy
import com.example.myapplication.R
import com.google.android.gms.tflite.client.TfLiteInitializationOptions
import com.google.android.gms.tflite.gpu.support.TfLiteGpu
import org.tensorflow.lite.DataType
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.gms.vision.TfLiteVision
import org.tensorflow.lite.task.gms.vision.classifier.Classifications
import org.tensorflow.lite.task.gms.vision.classifier.ImageClassifier

class ImageClassifierHelper(
    private var threshold: Float = 0.4f,
    private var maxResult: Int = 1,
    private var modelName: String = "alphabet_v1_metadata.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    interface ClassifierListener {
        fun onError(error: String)
        fun onResult(result: List<Classifications>?)
    }


    private var imageClassifier: ImageClassifier? = null

    init {
        TfLiteGpu.isGpuDelegateAvailable(context).onSuccessTask { gpuAvailable ->
            val optionsBuilder = TfLiteInitializationOptions.builder()
            if (gpuAvailable) {
                optionsBuilder.setEnableGpuDelegateSupport(true)
            }
            TfLiteVision.initialize(context, optionsBuilder.build())
        }.addOnSuccessListener {
            setupImageClassifier()
        }.addOnFailureListener {
            classifierListener?.onError(context.getString(R.string.tflitevision_is_not_initialized_yet))
        }
    }

    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResult)
        val baseOptions = BaseOptions.builder()
        if (CompatibilityList().isDelegateSupportedOnThisDevice){
            //pakai gpu
            baseOptions.useGpu()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1){
            baseOptions.useNnapi()
        } else {
            //pakai cpu
            baseOptions.setNumThreads(4)
        }
//            .setNumThreads(4)
        optionsBuilder.setBaseOptions(baseOptions.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build()
            )
            Log.d(TAG, "ImageClassifier setup successfully.")
        } catch (e: IllegalStateException) {
            classifierListener?.onError(context.getString(R.string.img_class_failed))
            Log.e(TAG, "Error setting up ImageClassifier: ${e.message}")
        }
    }

    fun classifyAlphabet(image: ImageProxy) {
        if (!TfLiteVision.isInitialized()) {
            val errorMessage = context.getString(R.string.tflitevision_is_not_initialized_yet)
            Log.e(TAG, errorMessage)
            classifierListener?.onError(errorMessage)
            return
        }
        modelName = "alphabet_v1_metadata.tflite"
        setupImageClassifier()
        imageProcessing(image)
    }

    fun classifyNumber(image: ImageProxy) {
        if (!TfLiteVision.isInitialized()) {
            val errorMessage = context.getString(R.string.tflitevision_is_not_initialized_yet)
            Log.e(TAG, errorMessage)
            classifierListener?.onError(errorMessage)
            return
        }
        modelName = "number_modelv2_metadata.tflite"
        setupImageClassifier()
        imageProcessing(image)
    }

    private fun imageProcessing(image: ImageProxy) {
        if (imageClassifier == null) {
            Log.w(TAG, "ImageClassifier is not initialized. Attempting to setup again.")
            setupImageClassifier()
        }

        val imageProcessor = org.tensorflow.lite.support.image.ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.FLOAT32))
            .build()

        val tensorImage: TensorImage
        try {
            tensorImage = imageProcessor.process(TensorImage.fromBitmap(toBitmap(image)))
        } catch (e: Exception) {
            classifierListener?.onError("Error processing image: ${e.message}")
            Log.e(TAG, "Error processing image: ${e.message}")
            image.close()
            return
        }

        val imageProcessingOptions = ImageProcessingOptions.builder()
            .setOrientation(getOrientationFromRotation(image.imageInfo.rotationDegrees))
            .build()

        try {
            val results = imageClassifier?.classify(tensorImage, imageProcessingOptions)
            classifierListener?.onResult(results)
            Log.d(TAG, "Classification results: ${results.toString()}")
        } catch (e: Exception) {
            classifierListener?.onError("Error during classification: ${e.message}")
            Log.e(TAG, "Error during classification: ${e.message}")
        } finally {
            image.close()
        }
    }

    private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation {
        return when (rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }
    }

    private fun toBitmap(image: ImageProxy): Bitmap {
        val bitmapBuffer = Bitmap.createBitmap(
            image.width,
            image.height,
            Bitmap.Config.ARGB_8888
        )
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
        return bitmapBuffer
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}