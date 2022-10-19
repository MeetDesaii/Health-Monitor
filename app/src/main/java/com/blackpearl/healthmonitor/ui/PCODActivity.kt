package com.blackpearl.healthmonitor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blackpearl.healthmonitor.databinding.ActivityPcodBinding

class PCODActivity : AppCompatActivity() {

    private var binding: ActivityPcodBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPcodBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {

        }
    }
}