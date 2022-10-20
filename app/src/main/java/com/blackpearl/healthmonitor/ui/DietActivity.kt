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
            foodList.add(DietFood(R.drawable.diet_img1, "Cabbage and Onion Salad", "65", "1.5", "3.7", "6.7", "2.1"))
            foodList.add(DietFood(R.drawable.diet_img2, "Roasted Black Chana Vegetable Chaat", "224", "7.9", "4.2","38.9","6.3"))
            foodList.add(DietFood(R.drawable.diet_img3, "Cucumber Sweet Corn Peanut Salad", "107", "4.4", "4.6", "15.2", "2.4"))
            foodList.add(DietFood(R.drawable.diet_img4, "Greek Salad with Feta Crumble", "126", "2.9", "11.2", "4.1", "1.4"))
            foodList.add(DietFood(R.drawable.diet_img5, "Soyabean Salad", "221", "15.6", "15.7", "8.0", "5.4"))
            foodList.add(DietFood(R.drawable.diet_img6, "Masala Rice", "106", "2.1", "3.2", "17.3", "1.1"))
            foodList.add(DietFood(R.drawable.diet_img7, "Peas Corn Pulao", "110", "2.3", "2.6", "19.3", "1.2"))
            foodList.add(DietFood(R.drawable.diet_img8, "Palak Pulao", "111", "2.1", "3.3","18.1", "1.3"))
            foodList.add(DietFood(R.drawable.diet_img9, "Vegetable Biryani", "115", "2.0", "4.6", "16.4", "1.2"))
            foodList.add(DietFood(R.drawable.diet_img10, "Mix Vegetable Paratha", "198", "3.3", "11.3", "20.7", "3.9"))
            foodList.add(DietFood(R.drawable.diet_img11, "Onion Raita", "54", "2.7", "3.1", "4.1", "0.4"))
            foodList.add(DietFood(R.drawable.diet_img12, "Besan ki Sabji", "150", "4.6", "8.2", "14.8", "3.3"))
            foodList.add(DietFood(R.drawable.diet_img13, "Chilli Paneer", "186", "6.9", "13.1", "10.3", "0.8"))
            foodList.add(DietFood(R.drawable.diet_img14, "Roasted Moong Dal", "364", "18.8", "11.6", "46.2", "6.4"))
            foodList.add(DietFood(R.drawable.diet_img15, "Roasted Soya Chaap", "184", "17.2", "7.7", "12.2", "6.1"))
            foodList.add(DietFood(R.drawable.diet_img16, "Bhindi Masala", "87", "1.7", "6.0", "7.0", "3.0"))
            foodList.add(DietFood(R.drawable.diet_img17, "Cabbage Capsicum Sabji", "42", "1.5", "1.9", "4.9", "2.6"))
            foodList.add(DietFood(R.drawable.diet_img18, "Mooli Stuffed Roti", "176", "6.3", "0.9", "35.6", "5.8"))
            foodList.add(DietFood(R.drawable.diet_img19, "Oats Roti", "186", "6.1", "5.4", "28.2", "4.9"))
            foodList.add(DietFood(R.drawable.diet_img20, "Quinoa Roti", "224", "7.7", "5.3", "34.6", "3.8"))
            foodList.add(DietFood(R.drawable.diet_img21, "Brown Rice Dosa", "189", "4.6", "6.4", "28.1", "2.3"))
            foodList.add(DietFood(R.drawable.diet_img22, "Vegetable Idli", "245", "5.9", "4.4", "46.0", "2.4"))
            foodList.add(DietFood(R.drawable.diet_img23, "Oats Vegetable Uttapam", "133", "3.6", "6.1", "16.2", "1.6"))
            foodList.add(DietFood(R.drawable.diet_img24, "Capsicum Dosa", "120", "3.4", "4.3", "17.1", "2.0"))


            dietRecyclerView.layoutManager = LinearLayoutManager(this@DietActivity)
            val dietAdapter = DietFoodAdapter(foodList)
            dietRecyclerView.adapter = dietAdapter


            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}