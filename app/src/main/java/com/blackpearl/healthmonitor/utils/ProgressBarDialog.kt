package com.blackpearl.healthmonitor.utils

import android.app.ProgressDialog
import android.content.Context
import com.blackpearl.healthmonitor.R

class ProgressBarDialog {

    companion object{

        lateinit var progressDialog : ProgressDialog

        fun showProgressDialog(context : Context){
            progressDialog = ProgressDialog(context)
            progressDialog.show()
            progressDialog.setContentView(R.layout.progress_dialog)
            progressDialog.window!!.setBackgroundDrawableResource(
                android.R.color.transparent
            )
            progressDialog.setCancelable(false)
        }

        fun dismissProgressDialog(){
            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
        }
    }
}