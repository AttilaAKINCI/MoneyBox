package com.akinci.moneybox.common.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("notEmptyValid")
fun notEmptyValid (textInputEditText: TextInputEditText, isValid: Boolean) {
    if(isValid){
        textInputEditText.error = null
    }else{
        textInputEditText.error = "Please fill required fields."
    }
}