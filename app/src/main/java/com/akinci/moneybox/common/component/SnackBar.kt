package com.akinci.moneybox.common.component

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class SnackBar {
    companion object{
        const val LENGTH_INDEFINITE = Snackbar.LENGTH_INDEFINITE
        const val LENGTH_SHORT = Snackbar.LENGTH_SHORT
        const val LENGTH_LONG = Snackbar.LENGTH_LONG

        fun make(view : View, text: CharSequence, duration : Int) : Snackbar {
            return Snackbar.make(view, text, duration)
        }
        fun makeLarge(view : View, text: CharSequence, duration : Int) : Snackbar {
            val snackBar = make(view, text, duration)
            snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 8
            return snackBar
        }
    }
}