package com.akinci.moneybox.common.extension

import android.text.TextUtils
import com.akinci.moneybox.R
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.validateNotEmpty() : Boolean {
    error = if(text.isNullOrEmpty()){
        resources.getString(R.string.validation_text_input_edit_text_not_empty)
    }else{ null }

    return TextUtils.isEmpty(error) && !TextUtils.isEmpty(text)
}