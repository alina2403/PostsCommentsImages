package nmodel


data class Weather(
        var id: Int,
        var main: String? = null,
        var description: String? = null,
        var icon: String? = null)