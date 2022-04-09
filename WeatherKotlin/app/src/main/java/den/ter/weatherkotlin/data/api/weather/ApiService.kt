package den.ter.weatherkotlin.data.api.weather

import den.ter.weatherkotlin.model.weather.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{

    @GET("data/2.5/onecall?lang=ru")
    suspend fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String,
                           @Query("exclude") exclude: String,
                           @Query("appid") appid: String):Response<WeatherData>


}