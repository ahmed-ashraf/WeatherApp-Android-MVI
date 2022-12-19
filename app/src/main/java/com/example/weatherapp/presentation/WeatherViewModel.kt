package com.example.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    val text = MutableStateFlow("").apply {
        debounce(800)
            .distinctUntilChanged().onEach {
                if (it.isNotEmpty()) loadCities(it)
            }.launchIn(viewModelScope)
    }


    fun loadWeatherInfo(city: String?) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            if (city != null)
                loadWeatherData(city)
            else
                locationTracker.getCurrentLocation()?.let { location ->
                    loadWeatherData(location)
                } ?: kotlin.run {
                    state = state.copy(
                        isLoading = false,
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                    )
                }
        }
    }

    suspend fun loadWeatherData(city: String) {
        when (val result = repository.getWeatherData(city)) {
            is Resource.Success -> {
                state = state.copy(
                    weatherInfo = result.data,
                    isLoading = false,
                    error = null
                )
            }
            is Resource.Error -> {
                state = state.copy(
                    weatherInfo = null,
                    isLoading = false,
                    error = result.message
                )
            }
        }
    }

    private suspend fun loadCities(city: String) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = false,
                error = null
            )
            when (val result = repository.getCities(city)) {
                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        error = null,
                        cities = result.data
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = null,
                        cities = null
                    )
                }
            }
        }
    }
}