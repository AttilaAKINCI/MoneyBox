package com.akinci.moneybox.common.binding

import android.text.TextUtils
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("notEmptyValid")
fun notEmptyValid (textInputEditText: TextInputEditText, text : String?) {
    text?.let {
        if (TextUtils.isEmpty(it)) {
            textInputEditText.error = "Please fill required fields."
        }else{
            textInputEditText.error = null
        }
    }
}