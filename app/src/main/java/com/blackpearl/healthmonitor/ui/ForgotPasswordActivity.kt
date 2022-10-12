package com.blackpearl.healthmonitor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.blackpearl.healthmonitor.R
import com.blackpearl.healthmonitor.databinding.ActivityForgotPasswordBinding
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class ForgotPasswordActivity : AppCompatActivity() {

    private var binding : ActivityForgotPasswordBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {

            firebaseAuth = FirebaseAuth.getInstance()

            btnBack.setOnClickListener {
                onBackPressed()
            }

            btnSendMail.setOnClickListener {

                email = edtTxtEmail.text.toString()

                ProgressBarDialog.showProgressDialog(this@ForgotPasswordActivity)

                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        if(it.isSuccessful){

                            ProgressBarDialog.dismissProgressDialog()

                            MotionToast.Companion.createColorToast(this@ForgotPasswordActivity,
                                "Success",
                                "Password reset link has been sent to $email!",
                                MotionToastStyle.SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(this@ForgotPasswordActivity, R.font.poppins_regular))

                        }
                    }
                    .addOnFailureListener {
                        ProgressBarDialog.dismissProgressDialog()

                        MotionToast.Companion.createColorToast(this@ForgotPasswordActivity,
                            "Error",
                            "Invalid email address!",
                            MotionToastStyle.ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(this@ForgotPasswordActivity, R.font.poppins_regular))

                    }
            }
        }
    }
}