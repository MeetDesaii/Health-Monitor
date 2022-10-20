package com.blackpearl.healthmonitor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.blackpearl.healthmonitor.R
import com.blackpearl.healthmonitor.databinding.ActivityEditProfileBinding
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class EditProfileActivity : AppCompatActivity() {

    private var binding: ActivityEditProfileBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {

            firebaseAuth = FirebaseAuth.getInstance()
            database = FirebaseFirestore.getInstance()

            val userUid = firebaseAuth.currentUser!!.uid

            ProgressBarDialog.showProgressDialog(this@EditProfileActivity)

            database.collection("Users")
                .document(userUid)
                .get()
                .addOnSuccessListener { document ->

                    ProgressBarDialog.dismissProgressDialog()

                    val username = document.data?.get("Username").toString()
                    val userAge = document.data?.get("UserAge").toString()
                    val userHeight = document.data?.get("UserHeight").toString()
                    val userWeight = document.data?.get("UserWeight").toString()
                    val userGender = document.data?.get("UserGender").toString()

                    edtTxtUsername.setText(username, TextView.BufferType.EDITABLE)
                    edtTxtAge.setText(userAge, TextView.BufferType.EDITABLE)
                    edtTxtHeight.setText(userHeight, TextView.BufferType.EDITABLE)
                    edtTxtWeight.setText(userWeight, TextView.BufferType.EDITABLE)

                    if(userGender == "Male"){
                        radioBtnMale.isChecked = true
                    }
                    else{
                        radioBtnFemale.isChecked = true
                    }
                }
                .addOnFailureListener {
                    ProgressBarDialog.dismissProgressDialog()
                }


            btnSaveChanges.setOnClickListener {

                ProgressBarDialog.showProgressDialog(this@EditProfileActivity)

                val nameRegex1 = "^[a-zA-Z ]*$".toRegex()
                val nameRegex2 = "^(?=[\\S\\s]{2,36}\$)[\\S\\s]*".toRegex()

                val username = edtTxtUsername.text.toString()
                val userAge = edtTxtAge.text.toString()
                val userHeight = edtTxtHeight.text.toString()
                val userWeight = edtTxtWeight.text.toString()

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(userAge) || TextUtils.isEmpty(userHeight) || TextUtils.isEmpty(userWeight) || (!radioBtnMale.isChecked && !radioBtnFemale.isChecked)) {

                    ProgressBarDialog.dismissProgressDialog()

                    MotionToast.Companion.createColorToast(
                        this@EditProfileActivity,
                        "Info",
                        "Fill required details!",
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@EditProfileActivity, R.font.poppins_regular)
                    )
                }
                else if(!username.matches(nameRegex1) || !username.matches(nameRegex2)){
                    ProgressBarDialog.dismissProgressDialog()
                    edtTxtUsername.error = "Invalid username!"
                }
                else if((userAge.toInt() < 2) || (userAge.toInt() > 120)){
                    ProgressBarDialog.dismissProgressDialog()
                    edtTxtAge.error = "Range: 2-120 year"
                }
                else if((userHeight.toInt() < 50) || (userHeight.toInt() > 250)){
                    ProgressBarDialog.dismissProgressDialog()
                    edtTxtHeight.error = "Range: 50-250 cm"
                }
                else if((userWeight.toDouble() < 5) || (userWeight.toDouble() > 600)){
                    ProgressBarDialog.dismissProgressDialog()
                    edtTxtWeight.error = "Range: 5-600 kg"
                }
                else{

                    ProgressBarDialog.dismissProgressDialog()

                    val updateRef = database.collection("Users")
                        .document(userUid)

                    updateRef.update("Username", username)
                    updateRef.update("UserAge", userAge)
                    updateRef.update("UserHeight", userHeight)
                    updateRef.update("UserWeight", userWeight)
                    if(radioBtnMale.isChecked){
                        updateRef.update("UserGender", "Male")
                    }
                    else{
                        updateRef.update("UserGender", "Female")
                    }

                    MotionToast.Companion.createColorToast(this@EditProfileActivity,
                        "Success",
                        "Changes saved successfully!",
                        MotionToastStyle.SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@EditProfileActivity, R.font.poppins_regular))

                    onBackPressed()
                }
            }


            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}