package com.blackpearl.healthmonitor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blackpearl.healthmonitor.databinding.ActivityDietBinding

class DietActivity : AppCompatActivity() {

    private var binding: ActivityDietBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDietBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {


        }
    }
}