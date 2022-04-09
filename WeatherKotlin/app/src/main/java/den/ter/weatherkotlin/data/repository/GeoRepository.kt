package den.ter.weatherkotlin.data.repository

import den.ter.weatherkotlin.data.api.geo.RetrofitInstanceGeo
import den.ter.weatherkotlin.model.geo.Geo
import retrofit2.Response

class GeoRepository {
    suspend fun getGeo(city: String, appid: String): Response<Geo> {
        return RetrofitInstanceGeo.apiService.getGeo(city, appid)
    }
}