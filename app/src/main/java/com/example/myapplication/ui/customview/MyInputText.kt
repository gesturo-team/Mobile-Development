////package com.example.myapplication.ui.customview
////
////import android.content.Context
////import android.text.Editable
////import android.text.TextWatcher
////import android.util.AttributeSet
////import android.view.MotionEvent
////import android.view.View
////import androidx.appcompat.widget.AppCompatEditText
////
////class MyInputText @JvmOverloads constructor(
////    context: Context, attrs: AttributeSet? = null
////) : AppCompatEditText(context, attrs), View.OnTouchListener {
////
////    init {
////        addTextChangedListener(object : TextWatcher {
////            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
////                TODO("Not yet implemented")
////            }
////
////            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
////                if (s.toString().length < 8) {
////                    setError("Password should not less than 8 characters", null)
////                } else {
////                    error = null
////                }
////            }
////
////            override fun afterTextChanged(s: Editable?) {
////                TODO("Not yet implemented")
////            }
////
////        })
////    }
////    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
////        TODO("Not yet implemented")
////    }
////
////}
//
//package com.example.myapplication.ui.customview
//
//import android.content.Context
//import android.graphics.drawable.Drawable
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.AttributeSet
//import android.view.MotionEvent
//import android.view.View
//import androidx.appcompat.widget.AppCompatEditText
//import androidx.core.content.ContextCompat
//import com.example.myapplication.R
//
//class MyInputText @JvmOverloads constructor(
//    context: Context, attrs: AttributeSet? = null
//) : AppCompatEditText(context, attrs), View.OnTouchListener {
//
//    private var isPasswordVisible: Boolean = false
//    private var eyeIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_eye)
//
//    init {
//        // Setting the onTouchListener to the current view
//        setOnTouchListener(this)
//
//        // Adding a TextWatcher to handle text changes
//        addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                // Implementation can be added here if needed
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                // Check the length of the text and set an error if it's less than 8 characters
//                if (s.toString().length < 8) {
//                    setError("Password should not be less than 8 characters", null)
//                } else {
//                    error = null
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                // Implementation can be added here if needed
//            }
//        })
//
//        // Set the initial icon
//        setCompoundDrawablesWithIntrinsicBounds(null, null, eyeIcon, null)
//    }
//
//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        // Handle touch events for the eye icon
//        if (event?.action == MotionEvent.ACTION_UP) {
//            val drawableRight = 2
//            eyeIcon?.let {
//                if (event.rawX >= (right - it.bounds.width())) {
//                    // Toggle password visibility
//                    isPasswordVisible = !isPasswordVisible
//                    updatePasswordVisibility()
//                    return true
//                }
//            }
//        }
//        return false
//    }
//
//    private fun updatePasswordVisibility() {
//        // Update the input type to show or hide the password
//        if (isPasswordVisible) {
//            inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//            eyeIcon = ContextCompat.getDrawable(context, R.drawable.ic_eye_off)
//        } else {
//            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
//            eyeIcon = ContextCompat.getDrawable(context, R.drawable.ic_eye)
//        }
//        // Move cursor to the end of the text
//        setSelection(text?.length ?: 0)
//        // Update the icon
//        setCompoundDrawablesWithIntrinsicBounds(null, null, eyeIcon, null)
//    }
//}

package com.example.myapplication.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MyInputText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        // Adding a TextWatcher to handle text changes
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Implementation can be added here if needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check the length of the text and set an error if it's less than 8 characters
                if (s.toString().length < 8) {
                    setError("Password should not be less than 8 characters", null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Implementation can be added here if needed
            }
        })
    }
}

