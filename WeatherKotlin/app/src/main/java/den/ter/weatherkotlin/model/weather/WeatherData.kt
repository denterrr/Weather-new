package den.ter.weatherkotlin.model.weather

data class WeatherData(
    val alerts: List<Alert>,
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)