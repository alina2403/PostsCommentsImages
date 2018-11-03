package model


data class WeatherInfo(var weather: List<Weather>? = null,
                       var main: Main? = null,
                       var id: Int,
                       var name: String? = null,
                       var cod: Int)