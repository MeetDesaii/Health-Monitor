package com.blackpearl.healthmonitor.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.blackpearl.healthmonitor.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var binding : ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val firebaseAuth = FirebaseAuth.getInstance()

        binding?.apply {

            Handler(Looper.myLooper()!!).postDelayed({

                val currentUser = firebaseAuth.currentUser

                if(currentUser != null){
                    val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
                else{
                    val signInIntent = Intent(this@SplashActivity, SignInActivity::class.java)
                    startActivity(signInIntent)
                    finish()
                }
            }, 2500)
        }
    }
}
