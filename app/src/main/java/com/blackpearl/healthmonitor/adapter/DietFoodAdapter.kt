package com.blackpearl.healthmonitor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.blackpearl.healthmonitor.databinding.DietFoodLayoutBinding
import com.blackpearl.healthmonitor.model.DietFood

class DietFoodAdapter(private var foodList: ArrayList<DietFood>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class DietViewHolder(private val binding: DietFoodLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bindData(dietFood: DietFood) {

            binding.imgDietFood.setImageResource(dietFood.image)
            binding.txtFoodName.text = dietFood.name
            binding.txtFoodCalories.text = "Calories: ${dietFood.calories} Cal per 100 g"
            binding.txtFoodProteins.text = "Proteins: ${dietFood.proteins} g per 100 g"
            binding.txtFoodFats.text = "Fats: ${dietFood.fats} g per 100 g"
            binding.txtFoodCarbs.text = "Carbs: ${dietFood.carbs} g per 100 g"
            binding.txtFoodFiber.text = "Fiber: ${dietFood.fiber} g per 100 g"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DietFoodLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DietViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dietViewHolder = holder as DietViewHolder
        dietViewHolder.bindData(foodList[position])
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}