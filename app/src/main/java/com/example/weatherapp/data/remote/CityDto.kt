package com.example.weatherapp.data.remote

data class CityDto(
    val id: Double,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
)