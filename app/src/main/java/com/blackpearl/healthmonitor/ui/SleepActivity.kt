package com.blackpearl.healthmonitor.ui

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import com.blackpearl.healthmonitor.databinding.ActivitySleepBinding
import com.blackpearl.healthmonitor.utils.AppUtils
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class SleepActivity : AppCompatActivity() {

    private var binding: ActivitySleepBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySleepBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding?.apply{

            val userUid = firebaseAuth.currentUser!!.uid

            ProgressBarDialog.showProgressDialog(this@SleepActivity)

            database.collection("Users")
                .document(userUid)
                .get()
                .addOnSuccessListener { document ->

                    ProgressBarDialog.dismissProgressDialog()

                    username = document.data?.get("Username").toString()

                    val userSleepTime = document.data?.get("UserSleepTime").toString()
                    val userAwakeTime = document.data?.get("UserAwakeTime").toString()

                    tvSleepTime.text = userSleepTime
                    tvWakeTime.text = userAwakeTime
                }

            btnBack.setOnClickListener {
                onBackPressed()
            }

            btnEditSleepTime.setOnClickListener {

                val cal = Calendar.getInstance()
                cal.timeInMillis = System.currentTimeMillis()
                val hour = cal[Calendar.HOUR_OF_DAY]
                val min = cal[Calendar.MINUTE]


                TimePickerDialog(this@SleepActivity, object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

                        cal.set(Calendar.HOUR_OF_DAY, p1)
                        cal.set(Calendar.MINUTE, p2)

                        val formatter = SimpleDateFormat("hh:mm a")
                        tvSleepTime.text = formatter.format(cal.time)
                        val sleepTime = formatter.format(cal.time)

                        database.collection("Users")
                            .document(userUid)
                            .update("UserSleepTime", sleepTime)


                        AppUtils.setReminder(
                            this@SleepActivity,
                            cal.timeInMillis,
                            1,
                            "Good night $username,",
                            "It's time to sleep..."
                        )
                    }
                }, hour, min, false).show()

            }

            btnEditWakeTime.setOnClickListener {

                val cal = Calendar.getInstance()
                cal.timeInMillis = System.currentTimeMillis()
                val hour = cal[Calendar.HOUR_OF_DAY]
                val min = cal[Calendar.MINUTE]


                TimePickerDialog(this@SleepActivity, object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

                        cal.set(Calendar.HOUR_OF_DAY, p1)
                        cal.set(Calendar.MINUTE, p2)

                        val formatter = SimpleDateFormat("hh:mm a")
                        tvWakeTime.text = formatter.format(cal.time)
                        val awakeTime = formatter.format(cal.time)

                        database.collection("Users")
                            .document(userUid)
                            .update("UserAwakeTime", awakeTime)


                        AppUtils.setReminder(
                            this@SleepActivity,
                            cal.timeInMillis,
                            2,
                            "Good morning $username,",
                            "Have a nice day..."
                        )
                    }
                }, hour, min, false).show()
            }
        }
    }
}