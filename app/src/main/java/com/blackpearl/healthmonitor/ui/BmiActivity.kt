package com.blackpearl.healthmonitor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blackpearl.healthmonitor.databinding.ActivityBmiBinding
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class BmiActivity : AppCompatActivity() {

    private var binding : ActivityBmiBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {

            firebaseAuth = FirebaseAuth.getInstance()
            database = FirebaseFirestore.getInstance()

            val userUid = firebaseAuth.currentUser!!.uid

            ProgressBarDialog.showProgressDialog(this@BmiActivity)

            database.collection("Users")
                .document(userUid)
                .get()
                .addOnSuccessListener { document ->

                    val underweight = "Underweight"
                    val normal = "Normal"
                    val overweight = "Overweight"
                    val obesity = "Obesity"

                    ProgressBarDialog.dismissProgressDialog()

                    val dataUsername = document.data?.get("Username")
                    val dataUserGender = document.data?.get("UserGender")
                    val dataUserAge = document.data?.get("UserAge")
                    val dataUserHeight = document.data?.get("UserHeight")
                    val dataUserWeight = document.data?.get("UserWeight")


                    val weightInDouble : Double = dataUserWeight.toString().toDouble()
                    val heightInDouble : Double = (dataUserHeight.toString().toDouble()) / 100

                    val bmiValue = (weightInDouble / (heightInDouble * heightInDouble))
                    val roundedBmiValue = "%,.2f".format(Locale.US, bmiValue)
                    val roundedBmiValueInDouble = roundedBmiValue.toDouble()


                    username.text = dataUsername.toString()
                    gender.text = dataUserGender.toString()
                    age.text = "${dataUserAge.toString()} years"
                    height.text = "${dataUserHeight.toString()} cm"
                    weight.text = "${dataUserWeight.toString()} kg"

                    txtCalculatedBmi.text = "${dataUsername}'s calculated BMI"
                    bmi.text = "$roundedBmiValue kg/m^2"

                    if(roundedBmiValueInDouble < 18.5){
                        category.text = underweight
                    }
                    else if(roundedBmiValueInDouble>=18.5 && roundedBmiValueInDouble<25){
                        category.text = normal
                    }
                    else if(roundedBmiValueInDouble>=25 && roundedBmiValueInDouble<30){
                        category.text = overweight
                    }
                    else{
                        category.text = obesity
                    }


                }
                .addOnFailureListener {
                    ProgressBarDialog.dismissProgressDialog()
                }

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}