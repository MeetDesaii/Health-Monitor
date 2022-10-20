package com.blackpearl.healthmonitor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blackpearl.healthmonitor.R
import com.blackpearl.healthmonitor.adapter.DietFoodAdapter
import com.blackpearl.healthmonitor.databinding.ActivityDietBinding
import com.blackpearl.healthmonitor.model.DietFood
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DietActivity : AppCompatActivity() {

    private var binding: ActivityDietBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDietBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {

            firebaseAuth = FirebaseAuth.getInstance()
            database = FirebaseFirestore.getInstance()


            val foodList = ArrayList<DietFood>()
            foodList.add(DietFood(R.drawable.diet_img1, "Cabbage and Onion Salad", "65", "1.5", "3.7"))
            foodList.add(DietFood(R.drawable.diet_img2, "Roasted Black Chana Vegetable Chaat", "224", "7.9", "4.2"))
            foodList.add(DietFood(R.drawable.diet_img3, "Cucumber Sweet Corn Peanut Salad", "107", "4.4", "4.6"))
            foodList.add(DietFood(R.drawable.diet_img4, "Greek Salad with Feta Crumble", "126", "2.9", "11.2"))
            foodList.add(DietFood(R.drawable.diet_img5, "Soyabean Salad", "221", "15.6", "15.7"))
            foodList.add(DietFood(R.drawable.diet_img6, "Masala Rice", "106", "2.1", "3.2"))
            foodList.add(DietFood(R.drawable.diet_img7, "Peas Corn Pulao", "110", "2.3", "2.6"))
            foodList.add(DietFood(R.drawable.diet_img8, "Palak Pulao", "111", "2.1", "3.3"))
            foodList.add(DietFood(R.drawable.diet_img9, "Vegetable Biryani", "115", "2.0", "4.6"))
            foodList.add(DietFood(R.drawable.diet_img10, "Mix Vegetable Paratha", "198", "3.3", "11.3"))
            foodList.add(DietFood(R.drawable.diet_img11, "Onion Raita", "54", "2.7", "3.1"))
            foodList.add(DietFood(R.drawable.diet_img12, "Besan ki Sabji", "150", "4.6", "8.2"))
            foodList.add(DietFood(R.drawable.diet_img13, "Chilli Paneer", "186", "6.9", "13.1"))
            foodList.add(DietFood(R.drawable.diet_img14, "Roasted Moong Dal", "364", "18.8", "11.6"))
            foodList.add(DietFood(R.drawable.diet_img15, "Roasted Soya Chaap", "184", "17.2", "7.7"))
            foodList.add(DietFood(R.drawable.diet_img16, "Bhindi Masala", "87", "1.7", "6.0"))
            foodList.add(DietFood(R.drawable.diet_img17, "Cabbage Capsicum Sabji", "42", "1.5", "1.9"))


            dietRecyclerView.layoutManager = LinearLayoutManager(this@DietActivity)
            val dietAdapter = DietFoodAdapter(foodList)
            dietRecyclerView.adapter = dietAdapter


            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}