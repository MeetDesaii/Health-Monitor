package com.blackpearl.healthmonitor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blackpearl.healthmonitor.databinding.ActivityHomeBinding
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    private var binding : ActivityHomeBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {

            firebaseAuth = FirebaseAuth.getInstance()
            database = FirebaseFirestore.getInstance()

            val userUid = firebaseAuth.currentUser!!.uid

            icLogOut.setOnClickListener {
                firebaseAuth.signOut()

                val signInIntent = Intent(this@HomeActivity, SignInActivity::class.java)
                startActivity(signInIntent)
                finish()
            }

            ProgressBarDialog.showProgressDialog(this@HomeActivity)

            database.collection("Users")
                .document(userUid)
                .get()
                .addOnSuccessListener { document ->

                    ProgressBarDialog.dismissProgressDialog()
                    val username = document.data?.get("Username")

                    txtLetsExplore.text = "Hello $username, Let's Explore!"
                }
                .addOnFailureListener {
                    ProgressBarDialog.dismissProgressDialog()
                }

            cardViewBMI.setOnClickListener {
                val bmiIntent = Intent(this@HomeActivity, BmiActivity::class.java)
                startActivity(bmiIntent)
            }
        }
    }
}