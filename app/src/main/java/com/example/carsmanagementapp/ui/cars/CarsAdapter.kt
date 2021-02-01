package com.example.carsmanagementapp.ui.cars

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.ui.cars.dateails.DetailsActivity

class CarsAdapter(var allCars: List<Car>) : RecyclerView.Adapter<CarsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_allcars, parent, false)

        return CarsViewHolder(cellForRow)
    }
    override fun getItemCount(): Int = allCars.size
    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val currentItem = allCars[position]
        holder.brand.text = currentItem.brand
        holder.model.text = currentItem.model
        holder.year.text = currentItem.year.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("id", allCars.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }
}
class CarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var brand: TextView = itemView.findViewById<TextView>(R.id.brandText)
    var model: TextView = itemView.findViewById<TextView>(R.id.modelText)
    var year: TextView = itemView.findViewById<TextView>(R.id.yearText)

}