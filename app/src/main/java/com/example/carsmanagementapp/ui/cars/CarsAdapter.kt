package com.example.carsmanagementapp.ui.cars

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import kotlinx.android.synthetic.main.item_allcars.view.*

class CarsAdapter(var allCars: List<Car>) : RecyclerView.Adapter<CarsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_allcars, parent, false)

        return CarsViewHolder(cellForRow)
    }

    override fun getItemCount(): Int = allCars.size

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        holder.bind(allCars.get(position))

        holder.itemView.setOnClickListener {
            /*val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("MAIN_ID", allCars.get(position).id)
            holder.itemView.context.startActivity(intent)*/
        }
    }
}
class CarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var brand: String
    lateinit var model: String
    var year: Int? = null

    fun bind(car: Car) = with(itemView) {
        brandText.text = car.brand
        modelText.text = car.model
        yearText.text = car.year.toString()
    }
}