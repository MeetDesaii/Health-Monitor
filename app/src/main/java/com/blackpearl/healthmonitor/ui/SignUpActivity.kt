package com.blackpearl.healthmonitor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.core.content.res.ResourcesCompat
import com.blackpearl.healthmonitor.R
import com.blackpearl.healthmonitor.databinding.ActivitySignUpBinding
import com.blackpearl.healthmonitor.utils.ProgressBarDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class SignUpActivity : AppCompatActivity() {

    private var binding : ActivitySignUpBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database : FirebaseFirestore

    private var email = ""
    private var username = ""
    private var enteredPassword = ""
    private var reEnteredPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {

            firebaseAuth = FirebaseAuth.getInstance()
            database = FirebaseFirestore.getInstance()

            btnSignUp.setOnClickListener {

                email = edtTxtEmail.text.toString()
                username = edtTxtUsername.text.toString()
                enteredPassword = edtTxtEnterPassword.text.toString()
                reEnteredPassword = edtTxtReEnterPassword.text.toString()

                val emailRegex = "([a-z0-9][a-z0-9\\.]*[a-z0-9])@([a-z0-9][-a-z0-9\\.]*[a-z0-9]\\.(arpa|root|aero|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|um|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)|([0-9]{1,3}\\.{3}[0-9]{1,3}))".toRegex()
                val nameRegex1 = "^[a-zA-Z ]*$".toRegex()
                val nameRegex2 = "^(?=[\\S\\s]{2,36}\$)[\\S\\s]*".toRegex()

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(enteredPassword) || TextUtils.isEmpty(reEnteredPassword)){
                    MotionToast.Companion.createColorToast(this@SignUpActivity,
                        "Info",
                        "Fill required details!",
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this@SignUpActivity, R.font.poppins_regular))
                }
                else if(!email.matches(emailRegex)){
                    edtTxtEmail.error = "Invalid email address!"
                }
                else if(!username.matches(nameRegex1) || !username.matches(nameRegex2)){
                    edtTxtUsername.error = "Invalid username!"
                }
                else if(enteredPassword.length < 8){
                    edtTxtEnterPassword.error = "Minimum length 8!"
                }
                else if(enteredPassword != reEnteredPassword){
                    edtTxtReEnterPassword.error = "Passwords do not match!"
                }
                else{

                    ProgressBarDialog.showProgressDialog(this@SignUpActivity)

                    firebaseAuth.createUserWithEmailAndPassword(email, enteredPassword)
                        .addOnCompleteListener {

                            if(it.isSuccessful){

                                ProgressBarDialog.dismissProgressDialog()

                                val userUID = firebaseAuth.currentUser!!.uid

                                val userData : MutableMap<String, Any> = HashMap()
                                userData["UserUID"] = userUID
                                userData["UserEmail"] = email
                                userData["Username"] = username
                                userData["UserAge"] = ""
                                userData["UserHeight"] = ""
                                userData["UserWeight"] = ""
                                userData["UserGender"] = ""

                                database.collection("Users")
                                    .document(userUID)
                                    .set(userData)

                                MotionToast.Companion.createColorToast(this@SignUpActivity,
                                    "Success",
                                    "Account created successfully!",
                                    MotionToastStyle.SUCCESS,
                                    MotionToast.GRAVITY_BOTTOM,
                                    MotionToast.LONG_DURATION,
                                    ResourcesCompat.getFont(this@SignUpActivity, R.font.poppins_regular))

                                val mainIntent = Intent(this@SignUpActivity, MainActivity::class.java)
                                startActivity(mainIntent)
                                finish()
                            }
                            else if(!it.isSuccessful) {

                                emailStateCheck()

                                ProgressBarDialog.dismissProgressDialog()
                            }
                        }
                        .addOnFailureListener {

                            ProgressBarDialog.dismissProgressDialog()
                        }
                }
            }

            txtSignIn.setOnClickListener {
                val signInIntent = Intent(this@SignUpActivity, SignInActivity::class.java)
                startActivity(signInIntent)
                finish()
            }
        }
    }

    private fun emailStateCheck(){
        firebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener {

                val isNotNewUser = it.result.signInMethods!!.isNotEmpty()

                if(isNotNewUser){
                    binding!!.edtTxtEmail.error = "Account already exists!"
                }
            }
    }
}