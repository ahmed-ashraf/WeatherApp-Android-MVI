package com.example.weatherapp.data.remote

data class WeatherDataDto(
    val location: WeatherLocation,
    val current: CurrentWeatherDto,
    val forecast: ForecastDto,
)

data class WeatherLocation(
    val name: String,
    val region: String,
    val country: String,
    val lat: String,
    val lon: String,
    val localtime: String,
)

data class ForecastDto(
    val forecastday: List<ForecastdayDto>
)

data class ForecastdayDto(
    val date: String,
    val day: DayWeatherDto
)

data class CurrentWeatherDto(
    val temp_f: Double,
    val wind_mph: Double,
    val humidity: Double,
    val condition: WeatherCondition,
)

data class WeatherCondition(
    val text: String,
    val icon: String,
)

data class DayWeatherDto(
    val maxtemp_f: Double,
    val mintemp_f: Double,
    val avgtemp_f: Double,
    val maxwind_mph: Double,
    val avghumidity: Double,
    val condition: WeatherCondition,
)


