package den.ter.weatherkotlin.model.city

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import den.ter.weatherkotlin.CityChoiseActivity
import den.ter.weatherkotlin.R
import den.ter.weatherkotlin.databinding.ActivityCityChoiseBinding
import kotlinx.android.synthetic.main.city_item.view.*

class CityAdapter: RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private var listCities = ArrayList<CityModel>()
    private lateinit var et: EditText
    private lateinit var rv: RecyclerView

    class CityViewHolder(view: View): RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item,parent,false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.itemView.city_name.text = listCities[position].name

    }

    override fun getItemCount() = listCities.size


    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<CityModel>){
        listCities = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: CityViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener{
            et.setText(listCities[holder.adapterPosition].name)
        }
    }

    override fun onViewDetachedFromWindow(holder: CityViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    fun setEditText(editText: EditText){
        et = editText
    }
}