package com.example.myapplication.detection

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityCameraBinding
import com.example.myapplication.detection.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
                startCamera()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        } else {
            startCamera()
        }

        binding.btnSwitch.setOnClickListener {
            cameraSelector =
                if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }
    }

    public override fun onResume() {
        super.onResume()
        if (allPermissionsGranted()) {
            startCamera()
        }
    }

    private fun startCamera() {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@CameraActivity, error, Toast.LENGTH_SHORT).show()
                    }
                    Log.e(TAG, "Classifier Error: $error")
                }

                override fun onResult(result: List<Classifications>?) {
                    runOnUiThread {
                        result?.let { classifications ->
                            if (classifications.isNotEmpty() && classifications[0].categories.isNotEmpty()) {
                                val categories =
                                    classifications[0].categories.sortedByDescending { it?.score }
                                val showResult = categories.joinToString("\n") {
                                    "${it.label} "
//                                    + NumberFormat.getPercentInstance()
//                                        .format(it.score).trim()
                                }
                                binding.tvResult.text = showResult
                                Log.d(TAG, "Classification result: $showResult")
                            } else {
                                binding.tvResult.text = ""
                                Log.d(TAG, "No classification result.")
                            }
                        }
                    }
                }
            }
        )

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val resolutionSelector = ResolutionSelector.Builder()
                .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
                .build()
            val imageAnalyzer = ImageAnalysis.Builder()
                .setResolutionSelector(resolutionSelector)
                .setTargetRotation(binding.viewCamera.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
            imageAnalyzer.setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
                imageClassifierHelper.classifyImage(image)
            }

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewCamera.surfaceProvider)
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
                Log.d(TAG, "Camera started successfully.")
            } catch (exc: Exception) {
                Toast.makeText(this@CameraActivity, "Failed to show camera", Toast.LENGTH_SHORT)
                    .show()
                Log.e(TAG, "Failed to bind camera use cases", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val TAG = "CameraActivity"
    }
}
