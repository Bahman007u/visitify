package com.example.shopify.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopify.adapters.RecommendedAdapter
import com.example.shopify.adapters.TripsAdapter
import com.example.shopify.databinding.ActivityMainBinding
import com.example.shopify.viewmodel.mainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel:mainViewModel by viewModels() //in here i have error
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      observeViewModel()
        setupCategoryListener()
    }

    private fun setupCategoryListener() {
        binding.cat1.setOnClickListener {
            val intent = Intent(this, SearchActivity2::class.java)
            intent.putExtra("cat", "1")
            startActivity(intent)
        }

        binding.cat2.setOnClickListener {
            val intent = Intent(this, SearchActivity2::class.java)
            intent.putExtra("cat", "2")
            startActivity(intent)
        }

        binding.cat3.setOnClickListener {
            val intent = Intent(this, SearchActivity2::class.java)
            intent.putExtra("cat", "3")
            startActivity(intent)
        }

        binding.cat4.setOnClickListener {
            val intent = Intent(this, SearchActivity2::class.java)
            intent.putExtra("cat", "4")
            startActivity(intent)
        }
    }


    private fun observeViewModel() {
        viewModel.upcomingTrips.observe(this){
            list->
            binding.progressBarUpcoming.visibility= View.GONE
            binding.viewUpcoming.apply {
                layoutManager=LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,false
                )
                adapter=TripsAdapter(list)
            }
        }
        viewModel.recommendedPlaces.observe(this){ list ->
            binding.progressBarRecommanded.visibility = View.GONE

            binding.viewRecommanded.apply {
                layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = RecommendedAdapter(list)
            }

        }
    }
}