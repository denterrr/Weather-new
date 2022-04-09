package den.ter.weatherkotlin.data.repository

import den.ter.weatherkotlin.data.api.weather.RetrofitInstance
import den.ter.weatherkotlin.model.weather.WeatherData
import retrofit2.Response

class WeatherRepository {
    suspend fun getWeather(lat: String, lon: String,
                           exclude: String, appid: String): Response<WeatherData>{
        return RetrofitInstance.apiService.getWeather(lat, lon, exclude, appid)
    }
}