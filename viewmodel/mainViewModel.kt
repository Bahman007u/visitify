package com.example.shopify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shopify.model.Place
import com.example.shopify.model.Trip
import com.example.shopify.repository.TripRepository

class mainViewModel(
    private val repository:TripRepository=TripRepository()
):ViewModel(){
    val upcomingTrips:LiveData<List<Trip>> = repository.getUpcomingTrips()
    val recommendedPlaces:LiveData<List<Place>> = repository.getRecommendedTrips()
}



