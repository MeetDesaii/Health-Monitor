package com.blackpearl.healthmonitor.model

data class DietFood(
    var image: Int,
    var name : String? = null,
    var calories : String? = null,
    var proteins : String? = null,
    var fats : String? = null,
    var carbs : String? = null,
    var fiber : String? = null
)
