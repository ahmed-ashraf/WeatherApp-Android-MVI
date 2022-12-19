package com.example.weatherapp.presentation

import com.example.weatherapp.domain.weather.CityInfo
import com.example.weatherapp.domain.weather.WeatherInfo


data class WeatherState(
    val cities: List<CityInfo>? = null,
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
