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

