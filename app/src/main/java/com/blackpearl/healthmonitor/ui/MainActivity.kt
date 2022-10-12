package com.blackpearl.healthmonitor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.core.content.res.ResourcesCompat
import com.blackpearl.healthmonitor.R
import com.blackpearl.healthmonitor.databinding.ActivityMainBinding
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database: FirebaseFirestore

    private var userAge = ""
    private var userHeight = ""
    private var userWeight = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding?.apply {

            val userUid = firebaseAuth.currentUser!!.uid

            ProgressBarDialog.showProgressDialog(this@MainActivity)

            icLogOut.setOnClickListener {

                ProgressBarDialog.dismissProgressDialog()

                firebaseAuth.signOut()

                val signInIntent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(signInIntent)
                finish()
            }

            database.collection("Users")
                .document(userUid)
                .get()
                .addOnSuccessListener { document ->

                    ProgressBarDialog.dismissProgressDialog()

                    val username = document.data?.get("Username")
                    txtWelcomeUSer.text = "Welcome $username,\nPlease fill in the required details for Smart health monitoring system!"
                }

            btnSaveDetails.setOnClickListener {

                userAge = edtTxtAge.text.toString()
                userHeight = edtTxtHeight.text.toString()
                userWeight = edtTxtWeight.text.toString()

                if(TextUtils.isEmpty(userAge) || TextUtils.isEmpty(userHeight) || TextUtils.isEmpty(userWeight)){
                    MotionToast.Companion.createColorToast(this@MainActivity,
                        "Info",
                        "Fill required details!",
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@MainActivity, R.font.poppins_regular))
                }
                else if((userAge.toInt() < 1) || (userAge.toInt() > 130)){
                    edtTxtAge.error = "Range: 1-130 year"
                }
                else if((userHeight.toInt() <= 50) || (userHeight.toInt() >= 250)){
                    edtTxtHeight.error = "Range: 50-250 cm"
                }
                else if((userWeight.toDouble() <= 5) || (userWeight.toDouble() >= 600)){
                    edtTxtWeight.error = "Range: 5-600 kg"
                }
                else{

                    val updateRef = database.collection("Users").document(userUid)
                    updateRef.update("UserAge", userAge)
                    updateRef.update("UserHeight", userHeight)
                    updateRef.update("UserWeight", userWeight)

                    MotionToast.Companion.createColorToast(this@MainActivity,
                        "Success",
                        "Details saved successfully!",
                        MotionToastStyle.SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@MainActivity, R.font.poppins_regular))

                    val homeIntent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(homeIntent)
                    finish()
                }
            }
        }
    }
}