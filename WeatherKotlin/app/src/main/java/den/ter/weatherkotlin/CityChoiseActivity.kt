package den.ter.weatherkotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import den.ter.weatherkotlin.data.utils.ConstObjects
import den.ter.weatherkotlin.data.utils.ListOfCities
import den.ter.weatherkotlin.databinding.ActivityCityChoiseBinding
import den.ter.weatherkotlin.model.city.CityAdapter
import den.ter.weatherkotlin.model.city.CityModel
import den.ter.weatherkotlin.viewmodel.GeoViewModel
import kotlinx.coroutines.delay

class CityChoiseActivity : AppCompatActivity() {

    lateinit var binding: ActivityCityChoiseBinding
    private var city: String = ""
    private lateinit var geoViewModel: GeoViewModel
    private var allGood: Boolean = true
    private lateinit var rv: RecyclerView
    private lateinit var adapter: CityAdapter
    private lateinit var myList: ArrayList<CityModel>
    private lateinit var myList2: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {


        binding = ActivityCityChoiseBinding.inflate(layoutInflater)
        rv = binding.rvCity
        adapter = CityAdapter()
        rv.adapter = adapter
        adapter.setEditText(binding.etSearch)

        val lists = ListOfCities()
        lists.setList()
        myList = lists.listCities
        myList2 = lists.listStrings

        binding.etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                val imm: InputMethodManager =
                    textView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                return@OnEditorActionListener true
            } else {
                return@OnEditorActionListener false
            }
        })

        binding.tvBack.setOnClickListener {
            onBackPressed()
        }

        binding.tvSave.setOnClickListener {
            city = binding.etSearch.text.toString()
            geoViewModel = GeoViewModel(city, ConstObjects.APP_ID_KEY)
            geoViewModel.geoResp.observe(this) { geos ->
                try {
                    binding.tvFail.visibility = View.GONE
                    val lat = geos[0].lat.toString()
                    val lon = geos[0].lon.toString()
                    city = geos[0].local_names.ru
                    if ("округ" in city) {// городской округ
                        city = city.substring(15, city.length)
                    }
                    allGood = true
                } catch (e: Exception) {
                    allGood = false
                    binding.tvFail.visibility = View.VISIBLE

                }
                lifecycleScope.launchWhenCreated {
                    delay(100)
                    if(allGood){
                        goWithCity()
                    }
                }
            }
        }




        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


    override fun onStart() {
        binding.etSearch.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.etSearch.text.isEmpty()){
                    adapter.setList(myList.filter {
                        it.name.startsWith("faouhfaufhaufa",true)} as ArrayList<CityModel>)
                }else{
                    search(binding.etSearch.text,adapter,myList)
                }

            }
        })


        super.onStart()
    }

    private fun search(text: Editable?, adapter: CityAdapter, myList: ArrayList<CityModel>) {
        if(myList2.contains(text.toString())){
            adapter.setList(myList.filter {
                it.name.startsWith("kgfofififi",true)} as ArrayList<CityModel>)
        }else{
        adapter.setList(myList.filter {
            it.name.startsWith(text.toString(),true) ||
                    it.name.contains(text.toString(),true)} as ArrayList<CityModel>)}

        }


    private fun goWithCity() {
        val intent2: Intent = Intent(this,MainActivity::class.java)
        intent2.putExtra(ConstObjects.CITY_KEY,city)
        startActivity(intent2)
    }
}

