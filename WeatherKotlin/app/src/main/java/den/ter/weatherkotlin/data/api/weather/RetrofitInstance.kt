package den.ter.weatherkotlin.data.api.weather

import den.ter.weatherkotlin.data.utils.ConstObjects
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(ConstObjects.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}