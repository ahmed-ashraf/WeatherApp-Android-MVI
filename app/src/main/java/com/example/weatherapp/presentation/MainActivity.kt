package com.example.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.domain.weather.WeatherData
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo(null)
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: WeatherViewModel) {

    Box(Modifier.fillMaxHeight()) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background1),
            contentDescription = "Background image",
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .background(Color(0xC0002762))
                .matchParentSize()
        )

        if (viewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        viewModel.state.error?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (viewModel.state.weatherInfo != null)
            Box(Modifier.fillMaxHeight()) {

                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = viewModel.state.weatherInfo!!.today.localTime.format(
                                DateTimeFormatter.ofPattern(
                                    "H:mm a"
                                )
                            ),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )
                        Spacer(modifier = Modifier.height(60.dp))
                        Text(
                            text = viewModel.state.weatherInfo!!.today.city,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.W700
                        )
                        Text(
                            text = viewModel.state.weatherInfo!!.today.localTime.format(
                                DateTimeFormatter.ofPattern(
                                    "EEEE, dd MMM yyyy"
                                )
                            ),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(viewModel.state.weatherInfo!!.today.icon),
                            contentDescription = null,
                            modifier = Modifier.width(90.dp),
                            contentScale = ContentScale.FillWidth
                        )

                        Row {
                            Text(
                                text = viewModel.state.weatherInfo!!.today.temperature.toString(),
                                fontSize = 56.sp,
                                fontWeight = FontWeight.W700
                            )
                            Text(
                                text = "°F",
                                fontSize = 56.sp,
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "It’s a ${viewModel.state.weatherInfo!!.today.description.lowercase()} day.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row {
                            Row {
                                Text(
                                    text = "${viewModel.state.weatherInfo!!.today.humidity.toInt()}%",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W400
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.ic_drop),
                                    contentDescription = null,
                                    modifier = Modifier.width(13.dp),
                                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.inverseSurface)
                                )
                            }
                            Spacer(modifier = Modifier.width(40.dp))
                            Row {
                                Text(
                                    text = "${viewModel.state.weatherInfo!!.today.mph.toInt()} mph",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W400
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.ic_wind),
                                    contentDescription = null,
                                    modifier = Modifier.width(16.dp),
                                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.inverseSurface)
                                )
                            }
                        }
                    }


                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        WeatherDayItem(
                            viewModel.state.weatherInfo!!.days[0],
                            stringResource(R.string.today)
                        )
                        WeatherDayItem(
                            viewModel.state.weatherInfo!!.days[1],
                            stringResource(R.string.tomorrow)
                        )
                        WeatherDayItem(
                            viewModel.state.weatherInfo!!.days[2],
                            viewModel.state.weatherInfo!!.days[2].date.format(
                                DateTimeFormatter.ofPattern("EEEE")
                            )
                        )
                    }

                }

                SearchView(viewModel)

            }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(viewModel: WeatherViewModel) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val focusRequester = FocusRequester()

    val keyboardController = LocalSoftwareKeyboardController.current

    val text by viewModel.text.collectAsState()

    Row(Modifier.fillMaxWidth(), Arrangement.End) {
        if (expanded) Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp)
                )
                .defaultMinSize(minHeight = 110.dp)
        ) {
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = if (text.isEmpty()) 30.dp else 10.dp)
                ) {
                    IconButton(onClick = {
                        expanded = !expanded
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.back
                            ),
                            tint = Color.Black
                        )
                    }
                    SearchTextField(
                        Modifier
                            .weight(1f)
                            .padding(end = 20.dp)
                            .focusRequester(focusRequester)
                            .onFocusChanged {
                                if (it.isFocused) {
                                    keyboardController?.show()
                                }
                            },
                        onValueChange = {
                            viewModel.text.value = it
                        },
                        text = text
                    )
                }

                if (viewModel.state.cities != null)
                    if (viewModel.state.cities!!.isNotEmpty())
                        LazyColumn(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .heightIn(0.dp, 232.dp)
                        ) {
                            items(
                                items = viewModel.state.cities!!.map {
                                    it.name
                                }
                            ) { city ->
                                CityItemView(city = city) {
                                    viewModel.loadWeatherInfo(city)
                                    viewModel.text.value = ""
                                    expanded = !expanded
                                }
                            }
                        }
            }
        } else
            IconButton(
                onClick = {
                    expanded = !expanded
                },
                modifier = Modifier.padding(all = 18.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(
                        id = R.string.search_icon
                    ),
                    tint = Color.White
                )
            }
    }
}


@Composable
fun CityItemView(city: String, onClick: (msg: String) -> Unit) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 8.dp)
        .clickable { onClick(city) }) {
        Text(text = city, color = Color.Black)
    }
}

@Composable
fun SearchTextField(modifier: Modifier, text: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = text,
        onValueChange = {
            onValueChange.invoke(it)
        },
        placeholder = { Text("Search city") },
        shape = RoundedCornerShape(15.dp),
        modifier = modifier,
        trailingIcon = {
            if (text.isNotEmpty())
                IconButton(onClick = { onValueChange.invoke("") }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(
                            id = R.string.clearText
                        ),
                        tint = Color.Black
                    )
                }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color.Black,
            textColor = Color.Black
        ),
    )
}


@Composable
fun WeatherDayItem(data: WeatherData, day: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = rememberAsyncImagePainter(data.icon),
            contentDescription = null,
            modifier = Modifier.width(50.dp),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = "${data.minTemp}°/${data.maxTemp}°F",
            fontSize = 12.sp,
            fontWeight = FontWeight.W400
        )

        Text(
            text = day,
            fontSize = 12.sp,
            fontWeight = FontWeight.W700
        )
    }
}