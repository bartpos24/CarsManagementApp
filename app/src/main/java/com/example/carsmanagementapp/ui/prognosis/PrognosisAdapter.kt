package com.example.carsmanagementapp.ui.prognosis

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.ui.cars.dateails.DetailsActivity
import kotlinx.android.synthetic.main.activity_details.view.*

class PrognosisAdapter(var allCars: List<Car>, var clickListner: OnCarClickListner) : RecyclerView.Adapter<PrognosisViewHolder>(){

    var list: ArrayList<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrognosisViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.prognosis_item, parent, false)

        return PrognosisViewHolder(cellForRow)
    }

    override fun getItemCount(): Int = allCars.size

    override fun onBindViewHolder(holder: PrognosisViewHolder, position: Int) {
        val currentItem = allCars[position]


        /*holder.brand.text = currentItem.brand
        holder.model.text = currentItem.model
        holder.year.text = currentItem.year.toString()
        holder.power.text = currentItem.power.toString()
        holder.colorTV.text = currentItem.color
        holder.cap.text = currentItem.engCap.toString()
        holder.engType.text = currentItem.engType.toString()
        holder.carType.text = currentItem.carType.toString()*/
        holder.initialize(currentItem, clickListner)
        holder.itemView.setOnClickListener {
            clickListner.onItemClick(currentItem, position)
            if (list.size < 2) {

                if (list.size == 1 && list[0] == currentItem.id) {
                    list.remove(list[0])
                    holder.colorBackground("#FFFFFF")
                }
                else {
                    list.add(currentItem.id)
                    holder.colorBackground("#c5cae9")
                }
            }
            else{
                if (list[0] == currentItem.id) {
                    list.remove(list[0])
                    holder.colorBackground("#FFFFFF")
                }
                else if (list[1] == currentItem.id) {
                    list.remove(list[1])
                    holder.colorBackground("#FFFFFF")
                }

            }
            Log.e("ss", list.toString())
        }

        /*holder.itemView.setOnClickListener {
            if (list.size < 2) {

                if (list.size == 1 && list[0] == currentItem.id) {
                    list.remove(list[0])
                    holder.colorBackground("#FFFFFF")
                }
                else {
                    list.add(currentItem.id)
                    holder.colorBackground("#c5cae9")
                }


            }
            else {
                if (list[0] == currentItem.id) {
                    list.remove(list[0])
                    holder.colorBackground("#FFFFFF")
                }
                else if (list[1] == currentItem.id) {
                    list.remove(list[1])
                    holder.colorBackground("#FFFFFF")
                }
                else {
                    list.remove(list[0])
                    holder.colorBackground("#FFFFFF")
                    list.add(currentItem.id)
                    holder.colorBackground("#c5cae9")
                }

            }
            //if ()
            *//*val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("id", allCars.get(position).id)
            holder.itemView.context.startActivity(intent)*//*
        }*/
    }
}
class PrognosisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var brand: TextView = itemView.findViewById<TextView>(R.id.brandPrognosisTV)
    var model: TextView = itemView.findViewById<TextView>(R.id.modelPrognosisTV)
    var year: TextView = itemView.findViewById<TextView>(R.id.yearPrognosisTV)
    var cap: TextView = itemView.findViewById<TextView>(R.id.capPrognosisTV)
    var power: TextView = itemView.findViewById<TextView>(R.id.powerPrognosisTV)
    var colorTV: TextView = itemView.findViewById<TextView>(R.id.colorPrognosisTV)
    var engType: TextView = itemView.findViewById<TextView>(R.id.engPrognosisTV)
    var carType: TextView = itemView.findViewById<TextView>(R.id.carPrognosisTV)


    fun colorBackground(color: String) {
        brand.setBackgroundColor(Color.parseColor(color))
        model.setBackgroundColor(Color.parseColor(color))
        year.setBackgroundColor(Color.parseColor(color))
        cap.setBackgroundColor(Color.parseColor(color))
        power.setBackgroundColor(Color.parseColor(color))
        colorTV.setBackgroundColor(Color.parseColor(color))
        engType.setBackgroundColor(Color.parseColor(color))
        carType.setBackgroundColor(Color.parseColor(color))
    }
    fun initialize(item: Car, action: OnCarClickListner) {
        brand.text = item.brand
        model.text = item.model
        year.text = item.year.toString()
        power.text = item.power.toString()
        colorTV.text = item.color
        cap.text = item.engCap.toString()
        engType.text = item.engType.toString()
        carType.text = item.carType.toString()
        /*itemView.setOnClickListener {
            action.onItemClick(item, adapterPosition)
            if (list.size < 2) {

                if (list.size == 1 && list[0] == itemView) {
                    list.remove(list[0])
                    colorBackground("#FFFFFF")
                }
                else{
                    list.add(itemView)
                    colorBackground("#c5cae9")
                }
            }
        }*/

    }

}
interface OnCarClickListner{
    fun onItemClick(item: Car, position: Int)
}