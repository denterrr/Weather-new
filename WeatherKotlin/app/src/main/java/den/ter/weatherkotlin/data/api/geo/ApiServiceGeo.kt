package den.ter.weatherkotlin.data.api.geo

import den.ter.weatherkotlin.model.geo.Geo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceGeo{

    @GET("geo/1.0/direct?lang=ru&limit=1")
    suspend fun getGeo(@Query("q") city: String,
                           @Query("appid") appid: String):Response<Geo>


}