package den.ter.weatherkotlin.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import den.ter.weatherkotlin.R
import den.ter.weatherkotlin.model.weather.WeatherData
import den.ter.weatherkotlin.data.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel(
                       private val lat: String, private val lon: String,
                       private val exclude: String,private val appid: String): ViewModel() {
    private val _resp = MutableLiveData<WeatherData>()


    val weatherResp:LiveData<WeatherData>
        get()=_resp
    val repo = WeatherRepository()

    init {
        getWeather()
    }

    private fun getWeather() = viewModelScope.launch {
        repo.getWeather(lat,lon,exclude,appid).let { response ->
            if(response.isSuccessful){
                _resp.postValue(response.body())
            }else{
                Log.d("Tag","Error response: ${response.message()}")
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun getDate(s: String): String{
        return try{
            val sdf = SimpleDateFormat("EEE, d MMM")
            val date = Date(s.toLong()*1000)
            sdf.format(date).toString()
        }catch (e:Exception){
            e.toString()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getHours(s: Int): String{
        return try{
            val sdf = SimpleDateFormat("HH")
            val date = Date(s.toLong()*1000)
            sdf.format(date).toString()
        }catch (e:Exception){
            e.toString()
        }
    }


    fun setWeatherIcon(iv: ImageView,desc: String){
        if(desc.contains("ясно")){
            iv.setImageResource(R.drawable.ic_sun)
        }
        else if(desc.contains("небольшой дождь")){
            iv.setImageResource(R.drawable.ic_cloud_sun_rain)
        }
        else if(desc.contains("дождь")){
            iv.setImageResource(R.drawable.ic_rain)
        }
        else if(desc.contains("прояснения") || desc.contains("переменная")||
            desc.contains("небольшая облачность")){
            iv.setImageResource(R.drawable.ic_cloud_sun)
        }
        else if(desc.contains("облачно")||desc.contains("пасмурно")){
            iv.setImageResource(R.drawable.ic_cloud)
        }
        else if(desc.contains("снег")){
            iv.setImageResource(R.drawable.ic_snow)
        }
        else{
            iv.setImageResource(R.drawable.ic_cloud)
        }
    }



}