package com.blackpearl.healthmonitor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blackpearl.healthmonitor.databinding.ActivityBmrBinding
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BmrActivity : AppCompatActivity() {

    private var binding : ActivityBmrBinding? = null

    private lateinit var database: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        binding = ActivityBmrBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {

            val userUid = firebaseAuth.currentUser!!.uid

            btnBack.setOnClickListener {
                onBackPressed()
            }

            ProgressBarDialog.showProgressDialog(this@BmrActivity)

            database.collection("Users")
                .document(userUid)
                .get()
                .addOnSuccessListener { document ->

                    ProgressBarDialog.dismissProgressDialog()

                    val dataUsername = document.data?.get("Username")
                    val dataUserGender = document.data?.get("UserGender")
                    val dataUserAge = document.data?.get("UserAge")
                    val dataUserHeight = document.data?.get("UserHeight")
                    val dataUserWeight = document.data?.get("UserWeight")


                    val weightInDouble: Double = dataUserWeight.toString().toDouble()
                    val heightInDouble: Double = dataUserHeight.toString().toDouble()
                    val ageInDouble: Double = dataUserAge.toString().toDouble()

                    val bmrMale = (10*weightInDouble) + (6.25*heightInDouble) - (5*ageInDouble) + 5
                    val bmrFemale = (10*weightInDouble) + (6.25*heightInDouble) - (5*ageInDouble) - 161


                    username.text = dataUsername.toString()
                    gender.text = dataUserGender.toString()
                    age.text = "${dataUserAge.toString()} years"
                    height.text = "${dataUserHeight.toString()} cm"
                    weight.text = "${dataUserWeight.toString()} kg"

                    txtCalculatedBmr.text = "${dataUsername}'s calculated BMR"

                    if(dataUserGender == "Male"){
                        bmr.text = "$bmrMale Calories/Day"
                    }
                    else if(dataUserGender == "Female"){
                        bmr.text = "$bmrFemale Calories/Day"
                    }
                }
        }
    }
}