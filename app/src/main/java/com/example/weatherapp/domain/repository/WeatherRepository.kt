package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.util.Resource
import com.example.weatherapp.domain.weather.CityInfo
import com.example.weatherapp.domain.weather.WeatherInfo


interface WeatherRepository {
    suspend fun getWeatherData(city: String): Resource<WeatherInfo>

    suspend fun getCities(city: String): Resource<List<CityInfo>>
}