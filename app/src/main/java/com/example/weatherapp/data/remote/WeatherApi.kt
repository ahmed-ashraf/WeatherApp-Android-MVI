package com.example.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast.json?key=d196c1ddf06c4bd7aa3170941221812&days=4&aqi=no&alerts=no")
    suspend fun getWeatherData(
        @Query("q") city: String,
    ): WeatherDataDto

    @GET("v1/search.json?key=d196c1ddf06c4bd7aa3170941221812")
    suspend fun getCities(
        @Query("q") city: String,
    ): List<CityDto>

}