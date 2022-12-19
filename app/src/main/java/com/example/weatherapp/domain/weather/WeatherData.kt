package com.example.weatherapp.domain.weather

import java.time.LocalDate

data class WeatherData(
    val maxTemp: Double,
    val minTemp: Double,
    val avgHumidity: Double,
    val mph: Double,
    val date: LocalDate,
    val icon: String,
)