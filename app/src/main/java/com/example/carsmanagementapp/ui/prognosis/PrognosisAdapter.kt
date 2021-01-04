package com.example.carsmanagementapp.ui.prognosis

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.ui.cars.dateails.DetailsActivity

class PrognosisAdapter(var allCars: List<Car>) : RecyclerView.Adapter<PrognosisViewHolder>(){

    var list: ArrayList<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrognosisViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.prognosis_item, parent, false)

        return PrognosisViewHolder(cellForRow)
    }

    override fun getItemCount(): Int = allCars.size

    override fun onBindViewHolder(holder: PrognosisViewHolder, position: Int) {
        val currentItem = allCars[position]


        holder.brand.text = currentItem.brand
        holder.model.text = currentItem.model
        holder.year.text = currentItem.year.toString()
        holder.power.text = currentItem.power.toString()
        holder.color.text = currentItem.color
        holder.cap.text = currentItem.engCap.toString()
        holder.engType.text = currentItem.engType.toString()
        holder.carType.text = currentItem.carType.toString()

        holder.itemView.setOnClickListener {
            if (list.size < 2) {

                if (list.size == 1 && list[0] == currentItem.id) {
                    list.remove(list[0])
                }
                else
                    list.add(currentItem.id)

            }
            else {
                if (list[0] == currentItem.id) {
                    list.remove(list[0])
                }
                else if (list[1] == currentItem.id) {
                    list.remove(list[1])
                }
                else {
                    list.remove(list[0])
                    list.add(currentItem.id)
                }

            }
            //if ()
            /*val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("id", allCars.get(position).id)
            holder.itemView.context.startActivity(intent)*/
        }
    }
}
class PrognosisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var brand: TextView = itemView.findViewById<TextView>(R.id.brandPrognosisTV)
    var model: TextView = itemView.findViewById<TextView>(R.id.modelPrognosisTV)
    var year: TextView = itemView.findViewById<TextView>(R.id.yearPrognosisTV)
    var cap: TextView = itemView.findViewById<TextView>(R.id.capPrognosisTV)
    var power: TextView = itemView.findViewById<TextView>(R.id.powerPrognosisTV)
    var color: TextView = itemView.findViewById<TextView>(R.id.colorPrognosisTV)
    var engType: TextView = itemView.findViewById<TextView>(R.id.engPrognosisTV)
    var carType: TextView = itemView.findViewById<TextView>(R.id.carPrognosisTV)


}