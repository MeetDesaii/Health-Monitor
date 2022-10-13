package com.blackpearl.healthmonitor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blackpearl.healthmonitor.databinding.ActivityWaterSuggestionBinding
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class WaterSuggestionActivity : AppCompatActivity() {

    private var binding: ActivityWaterSuggestionBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWaterSuggestionBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding?.apply {

            val userUid = firebaseAuth.currentUser!!.uid

            btnBack.setOnClickListener {
                onBackPressed()
            }

            ProgressBarDialog.showProgressDialog(this@WaterSuggestionActivity)

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

                    val weightInDouble : Double = dataUserWeight.toString().toDouble()


                    username.text = dataUsername.toString()
                    gender.text = dataUserGender.toString()
                    age.text = "${dataUserAge.toString()} years"
                    height.text = "${dataUserHeight.toString()} cm"
                    weight.text = "${dataUserWeight.toString()} kg"

                    txtSuggestionToUser.text = "Water suggestion to $dataUsername"


                    val weightInPound : Double = weightInDouble * 2.2
                    val volumeInFluidOunces : Double = weightInPound / 2
                    val volumeInL : Double = (volumeInFluidOunces * 29.57) / 1000

                    val finalVolumeInL = "%,.2f".format(Locale.US, volumeInL)

                    suggestion.text = "$finalVolumeInL  Litre/Day"
                }
                .addOnFailureListener {
                    ProgressBarDialog.dismissProgressDialog()
                }
        }
    }
}