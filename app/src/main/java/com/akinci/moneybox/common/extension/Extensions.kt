package com.akinci.moneybox.common.extension

import android.text.TextUtils
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.isValid() : Boolean {
    return TextUtils.isEmpty(error) && !TextUtils.isEmpty(text)
}