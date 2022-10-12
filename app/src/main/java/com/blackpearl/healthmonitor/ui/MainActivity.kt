package com.blackpearl.healthmonitor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blackpearl.healthmonitor.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding?.apply {

            val userUid = firebaseAuth.currentUser!!.uid

            icLogOut.setOnClickListener {
                firebaseAuth.signOut()

                val signInIntent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(signInIntent)
                finish()
            }

            database.collection("Users")
                .document(userUid)
                .get()
                .addOnSuccessListener { document ->

                    val username = document.data?.get("Username")
                    txtWelcomeUSer.text = "Welcome $username,"
                }
        }
    }
}