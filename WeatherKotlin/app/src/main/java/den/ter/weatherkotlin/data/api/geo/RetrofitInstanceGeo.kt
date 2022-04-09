package den.ter.weatherkotlin.data.api.geo

import den.ter.weatherkotlin.data.utils.ConstObjects
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceGeo {

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(ConstObjects.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ApiServiceGeo by lazy {
        retrofit.create(ApiServiceGeo::class.java)
    }
}