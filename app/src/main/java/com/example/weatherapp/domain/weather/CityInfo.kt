package com.example.weatherapp.domain.weather

data class CityInfo(
    val id: Double,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
)