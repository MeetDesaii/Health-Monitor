package com.blackpearl.healthmonitor.model

import android.graphics.drawable.Drawable

data class DietFood(
    var image: Int,
    var name : String? = null,
    var calories : String? = null,
    var proteins : String? = null,
    var fats : String? = null
)
