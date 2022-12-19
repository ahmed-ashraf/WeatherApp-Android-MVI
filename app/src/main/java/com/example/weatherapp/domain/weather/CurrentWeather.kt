package com.example.weatherapp.domain.weather

import java.time.LocalDateTime

data class CurrentWeather(
    val temperature: Double,
    val humidity: Double,
    val description: String,
    val mph: Double,
    val city: String,
    val localTime: LocalDateTime,
    val icon: String,
)