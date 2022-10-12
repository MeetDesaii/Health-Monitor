package com.blackpearl.healthmonitor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.core.content.res.ResourcesCompat
import com.blackpearl.healthmonitor.R
import com.blackpearl.healthmonitor.databinding.ActivitySignInBinding
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class SignInActivity : AppCompatActivity() {

    private var binding : ActivitySignInBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database: FirebaseFirestore

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding?.apply {

            btnSignIn.setOnClickListener {

                email = edtTxtEmail.text.toString()
                password = edtTxtPassword.text.toString()

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    MotionToast.Companion.createColorToast(this@SignInActivity,
                        "Info",
                        "Fill required details!",
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@SignInActivity, R.font.poppins_regular))
                }
                else{

                    ProgressBarDialog.showProgressDialog(this@SignInActivity)

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {

                            if(it.isSuccessful){

                                ProgressBarDialog.dismissProgressDialog()

                                MotionToast.Companion.createColorToast(this@SignInActivity,
                                    "Success",
                                    "Logged in successfully!",
                                    MotionToastStyle.SUCCESS,
                                    MotionToast.GRAVITY_BOTTOM,
                                    MotionToast.LONG_DURATION,
                                    ResourcesCompat.getFont(this@SignInActivity, R.font.poppins_regular))

                                val mainIntent = Intent(this@SignInActivity, MainActivity::class.java)
                                startActivity(mainIntent)
                                finish()
                            }
                        }
                        .addOnFailureListener {
//                            emailStateCheck()

                            MotionToast.Companion.createColorToast(this@SignInActivity,
                                "Error",
                                "Invalid credentials!",
                                MotionToastStyle.ERROR,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(this@SignInActivity, R.font.poppins_regular))

                            ProgressBarDialog.dismissProgressDialog()
                        }
                }
            }

            txtForgotPassword.setOnClickListener {
                val forgotPwIntent = Intent(this@SignInActivity, ForgotPasswordActivity::class.java)
                startActivity(forgotPwIntent)
            }

            txtSignUp.setOnClickListener {
                val signUpIntent = Intent(this@SignInActivity, SignUpActivity::class.java)
                startActivity(signUpIntent)
                finish()
            }
        }
    }
}