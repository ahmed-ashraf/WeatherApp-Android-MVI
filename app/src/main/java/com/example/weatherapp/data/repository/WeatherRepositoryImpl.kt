package com.example.weatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.data.mappers.toCitiesInfo
import com.example.weatherapp.data.mappers.toWeatherInfo
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Resource
import com.example.weatherapp.domain.weather.CityInfo
import com.example.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(city: String): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    city = city
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getCities(city: String): Resource<List<CityInfo>> {
        return try {
            Resource.Success(
                data = api.getCities(
                    city = city
                ).toCitiesInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}