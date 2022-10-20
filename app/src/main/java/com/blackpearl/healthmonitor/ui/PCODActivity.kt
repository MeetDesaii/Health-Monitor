package com.blackpearl.healthmonitor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.blackpearl.healthmonitor.databinding.ActivityPcodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class PCODActivity : AppCompatActivity() {

    private var binding: ActivityPcodBinding? = null

    private lateinit var database: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        binding = ActivityPcodBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {

            val userUid = firebaseAuth.currentUser!!.uid

            btnBack.setOnClickListener {
                onBackPressed()
            }

            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val currentDate = dateFormat.format(Date())

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 28)
            val prDate: Date = calendar.time

            val predictedDate = dateFormat.format(prDate)

            txtCurrentDate.text = "Suppose, Period Just Started: $currentDate"

            btnPredict.setOnClickListener {

                txtPredictedDate.text = "Predicted Next Period Date: $predictedDate"

                txtPredictInfo.visibility = View.INVISIBLE
            }
        }
    }
}