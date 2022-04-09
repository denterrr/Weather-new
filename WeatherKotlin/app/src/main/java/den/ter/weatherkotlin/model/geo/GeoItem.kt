package den.ter.weatherkotlin.model.geo

data class GeoItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)