package com.blackpearl.healthmonitor.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.blackpearl.healthmonitor.R
import com.blackpearl.healthmonitor.databinding.ActivityIntroductionBinding
import com.blackpearl.healthmonitor.databinding.ProgressDialogBinding
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.saadahmedsoft.popupdialog.PopupDialog
import com.saadahmedsoft.popupdialog.Styles
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class IntroductionActivity : AppCompatActivity() {

    private var binding: ActivityIntroductionBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {

            firebaseAuth = FirebaseAuth.getInstance()
            database = FirebaseFirestore.getInstance()

            val userUID = firebaseAuth.currentUser!!.uid

            ProgressBarDialog.showProgressDialog(this@IntroductionActivity)

            database.collection("Users")
                .document(userUID)
                .get()
                .addOnSuccessListener {

                    ProgressBarDialog.dismissProgressDialog()

                    val dataUsername = it.data?.get("Username")
                    val dataUserGender = it.data?.get("UserGender")
                    val dataUserAge = it.data?.get("UserAge")
                    val dataUserHeight = it.data?.get("UserHeight")
                    val dataUserWeight = it.data?.get("UserWeight")

                    txtWelcomeUser.text = "Welcome, $dataUsername"
                    username.text = dataUsername.toString()
                    gender.text = dataUserGender.toString()
                    age.text = "${dataUserAge.toString()} years"
                    height.text = "${dataUserHeight.toString()} cm"
                    weight.text = "${dataUserWeight.toString()} kg"
                }
                .addOnFailureListener {

                    MotionToast.Companion.createColorToast(this@IntroductionActivity,
                        "Error",
                        "Something went wrong!",
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@IntroductionActivity, R.font.poppins_regular))
                }

            icLogOut.setOnClickListener {
                signOutDialog(firebaseAuth)
            }
        }
    }

    private fun signOutDialog(firebaseAuth: FirebaseAuth) {
        PopupDialog.getInstance(this)
            .setStyle(Styles.IOS)
            .setHeading("Sign out")
            .setPositiveButtonText("Confirm")
            .setHeadingTextColor(R.color.blue)
            .setDescriptionTextColor(R.color.gray)
            .setPositiveButtonTextColor(R.color.blue)
            .setNegativeButtonTextColor(R.color.blue)
            .setDescription(
                "Are you confirm you want to sign out?"
            )
            .setCancelable(false)
            .showDialog(object : OnDialogButtonClickListener() {
                override fun onPositiveClicked(dialog: Dialog?) {
                    super.onPositiveClicked(dialog)

                    firebaseAuth.signOut()
                    val signInIntent = Intent(this@IntroductionActivity, SignInActivity::class.java)
                    startActivity(signInIntent)
                    finish()
                }

                override fun onNegativeClicked(dialog: Dialog?) {
                    super.onNegativeClicked(dialog)

                    dialog!!.dismiss()
                }
            })
    }
}