package com.example.weatherapp.domain.weather

data class WeatherInfo(
    val today: CurrentWeather,
    val days: List<WeatherData>,
)