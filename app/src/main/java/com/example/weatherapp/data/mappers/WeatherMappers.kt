package com.example.weatherapp.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.data.remote.CityDto
import com.example.weatherapp.data.remote.WeatherDataDto
import com.example.weatherapp.domain.weather.CityInfo
import com.example.weatherapp.domain.weather.CurrentWeather
import com.example.weatherapp.domain.weather.WeatherData
import com.example.weatherapp.domain.weather.WeatherInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDto.toWeatherInfo(): WeatherInfo = WeatherInfo(
    today = CurrentWeather(
        city = location.name,
        description = current.condition.text,
        humidity = current.humidity,
        localTime = LocalDateTime.parse(
            location.localtime,
            DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm")
        ),
        icon = "https:${current.condition.icon}",
        mph = current.wind_mph,
        temperature = current.temp_f
    ),
    days = listOf(
        WeatherData(
            mph = forecast.forecastday[0].day.maxwind_mph,
            icon = "https:${forecast.forecastday[0].day.condition.icon}",
            maxTemp = forecast.forecastday[0].day.maxtemp_f,
            avgHumidity = forecast.forecastday[0].day.avghumidity,
            minTemp = forecast.forecastday[0].day.mintemp_f,
            date = LocalDate.parse(
                forecast.forecastday[0].date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            )
        ),
        WeatherData(
            mph = forecast.forecastday[1].day.maxwind_mph,
            icon = "https:${forecast.forecastday[1].day.condition.icon}",
            maxTemp = forecast.forecastday[1].day.maxtemp_f,
            avgHumidity = forecast.forecastday[1].day.avghumidity,
            minTemp = forecast.forecastday[1].day.mintemp_f,
            date = LocalDate.parse(
                forecast.forecastday[1].date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            )
        ),
        WeatherData(
            mph = forecast.forecastday[2].day.maxwind_mph,
            icon = "https:${forecast.forecastday[2].day.condition.icon}",
            maxTemp = forecast.forecastday[2].day.maxtemp_f,
            avgHumidity = forecast.forecastday[2].day.avghumidity,
            minTemp = forecast.forecastday[2].day.mintemp_f,
            date = LocalDate.parse(
                forecast.forecastday[2].date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            )
        ),
    )
)

fun List<CityDto>.toCitiesInfo(): List<CityInfo> {
    return map{
        CityInfo(
            id = it.id,
            name = it.name,
            country = it.country,
            lat = it.lat,
            lon = it.lon,
            region = it.region
        )
    }
}