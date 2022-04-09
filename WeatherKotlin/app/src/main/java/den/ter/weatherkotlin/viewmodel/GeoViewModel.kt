package den.ter.weatherkotlin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import den.ter.weatherkotlin.model.geo.Geo
import den.ter.weatherkotlin.data.repository.GeoRepository
import kotlinx.coroutines.launch

class GeoViewModel(
                       private val city: String, private val appid: String): ViewModel() {
    private val _resp = MutableLiveData<Geo>()


    val geoResp:LiveData<Geo>
        get()=_resp
    val repo = GeoRepository()

    init {
        getGeo()
    }

    private fun getGeo() = viewModelScope.launch {
        repo.getGeo(city,appid).let { response ->
            if(response.isSuccessful){
                _resp.postValue(response.body())
            }else{
                Log.d("Tag","Error response: ${response.message()}")
            }
        }
    }

}