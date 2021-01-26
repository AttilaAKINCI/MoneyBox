package com.akinci.moneybox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var btnSignIn : Button = findViewById(R.id.btn_sign_in)
        var animation : LottieAnimationView = findViewById(R.id.animation)

        btnSignIn.setOnClickListener {
            animation.playAnimation()
        }
    }
}