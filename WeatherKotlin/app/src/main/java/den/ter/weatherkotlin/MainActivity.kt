package den.ter.weatherkotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import den.ter.weatherkotlin.databinding.ActivityMainBinding
import den.ter.weatherkotlin.data.utils.ConstObjects
import den.ter.weatherkotlin.viewmodel.GeoViewModel
import den.ter.weatherkotlin.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private var allGood = true

    private var city = ""
    private var lat = ""
    private var lon = ""
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var geoViewModel: GeoViewModel
    private lateinit var sharedPreferences: SharedPreferences

    private val preferencesListener =
        SharedPreferences.OnSharedPreferenceChangeListener { preferences, s ->
            if (s == ConstObjects.CITY_KEY) {
                city = preferences.getString(s, "").toString()
            }
        }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = this.getSharedPreferences(ConstObjects.APP_PREFS, Context.MODE_PRIVATE)
        city = sharedPreferences.getString(ConstObjects.CITY_KEY, "Казань").toString()
        setCity(city)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.tvChangeCity.setOnClickListener {
            val intentGo: Intent = Intent(this,CityChoiseActivity::class.java)
            startActivity(intentGo)
        }

        val extras: Bundle? = intent.extras
        if (extras!=null) {
            city = extras.getString(ConstObjects.CITY_KEY).toString()
            setCity(city)
            saveCity()
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }


    @SuppressLint("SetTextI18n")
    private fun setCity(chosen_city: String) {
        allGood = true
        geoViewModel = GeoViewModel(chosen_city, ConstObjects.APP_ID_KEY)

        geoViewModel.geoResp.observe(this) { geos ->
            try {
                lat = geos[0].lat.toString()
                lon = geos[0].lon.toString()
                city = geos[0].local_names.ru
                if ("округ" in city) {// городской округ
                    city = city.substring(15, city.length)
                }
                viewModel = WeatherViewModel(
                    lat, lon, ConstObjects.WEATHER_MODE,
                    ConstObjects.APP_ID_KEY
                )
                viewModel.weatherResp.observe(this) { weather ->
                    binding.apply {
                        //TODAY
                        curCity.text = city
                        tempMain.text = "${weather.current.temp.roundToInt() - 273}°"
                        tempMainMaxmin.text = "${weather.daily[0].temp.max.roundToInt() - 273}°/" +
                                "${weather.daily[0].temp.min.roundToInt() - 273}°"
                        descMain.text = weather.current.weather[0].description.replaceFirstChar {it.titlecase(
                            Locale.getDefault())}
                        val cur_hours: Int = viewModel.getHours(weather.current.dt).toInt()
                        if(cur_hours>21||cur_hours<6){
                            ivMain.setImageResource(R.drawable.ic_night)
                        }else{
                            viewModel.setWeatherIcon(ivMain,weather.current.weather[0].description)
                        }


                        //FUTURE DAYS
                        date1.text = "Завтра, ${viewModel.getDate(weather.daily[1].dt.toString()).substring(4,10)}"
                        date2.text = viewModel.getDate(weather.daily[2].dt.toString())
                        date3.text = viewModel.getDate(weather.daily[3].dt.toString())
                        date4.text = viewModel.getDate(weather.daily[4].dt.toString())
                        date5.text = viewModel.getDate(weather.daily[5].dt.toString())

                        temp1.text = "${weather.daily[1].temp.max.roundToInt() - 273}°/" +
                                "${weather.daily[1].temp.min.roundToInt() - 273}°"
                        temp2.text = "${weather.daily[2].temp.max.roundToInt() - 273}°/" +
                                "${weather.daily[2].temp.min.roundToInt() - 273}°"
                        temp3.text = "${weather.daily[3].temp.max.roundToInt() - 273}°/" +
                                "${weather.daily[3].temp.min.roundToInt() - 273}°"
                        temp4.text = "${weather.daily[4].temp.max.roundToInt() - 273}°/" +
                                "${weather.daily[4].temp.min.roundToInt() - 273}°"
                        temp5.text = "${weather.daily[5].temp.max.roundToInt() - 273}°/" +
                                "${weather.daily[5].temp.min.roundToInt() - 273}°"

                        viewModel.setWeatherIcon(iv1,weather.daily[1].weather[0].description)
                        viewModel.setWeatherIcon(iv2,weather.daily[2].weather[0].description)
                        viewModel.setWeatherIcon(iv3,weather.daily[3].weather[0].description)
                        viewModel.setWeatherIcon(iv4,weather.daily[4].weather[0].description)
                        viewModel.setWeatherIcon(iv5,weather.daily[5].weather[0].description)

                    }
                }
            } catch (e: Exception) {
                allGood = false

            }
        }

    }


    @SuppressLint("CommitPrefEdits")
    fun saveCity() {
        sharedPreferences = this.getSharedPreferences(ConstObjects.APP_PREFS, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(ConstObjects.CITY_KEY, city)
            .apply()
    }

    override fun onBackPressed() {

    }
}

