package com.blackpearl.healthmonitor.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.blackpearl.healthmonitor.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var binding : ActivitySplashBinding? = null

    private lateinit var database : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding?.apply {

            Handler(Looper.myLooper()!!).postDelayed({

                val currentUser = firebaseAuth.currentUser

                if(currentUser != null){

                    val userUid = currentUser.uid

                    database.collection("Users")
                        .document(userUid)
                        .get()
                        .addOnSuccessListener { document ->
                            val userAge = document.data?.get("UserAge")

                            if(userAge == ""){
                                val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                                startActivity(mainIntent)
                                finish()
                            }
                            else{
//                                val homeIntent = Intent(this@SplashActivity, HomeActivity::class.java)
//                                startActivity(homeIntent)
//                                finish()

                                val introductionIntent = Intent(this@SplashActivity, IntroductionActivity::class.java)
                                startActivity(introductionIntent)
                                finish()
                            }
                        }
                }
                else{
                    val signInIntent = Intent(this@SplashActivity, SignInActivity::class.java)
                    startActivity(signInIntent)
                    finish()
                }
            }, 2000)
        }
    }
}
