package com.example.carsmanagementapp.ui.prognosis

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.ui.cars.CarsAdapter

class PrognosisFragment : Fragment(), OnCarClickListner {

    private lateinit var prognosisViewModel: PrognosisViewModel
    private lateinit var recyclerView: RecyclerView
    lateinit var prognosisAdapter: PrognosisAdapter
    lateinit var prognosisMenager: RecyclerView.LayoutManager
    private lateinit var compareBtn: Button
    private lateinit var mContext: Context
    var cars: ArrayList<Car> = ArrayList()

    var listToPrognosis: ArrayList<Car> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.prognosis_fragment, container, false)
        recyclerView = view!!.findViewById(R.id.recyclerPrognosis)
        compareBtn = view!!.findViewById(R.id.compareButton)

        prognosisMenager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = prognosisMenager

        prognosisViewModel = ViewModelProvider(this).get(PrognosisViewModel::class.java)

        prognosisViewModel.loadDatabase()

        prognosisViewModel.actualCarList.observe(viewLifecycleOwner, {
            if (it != null) {
                cars = it
                Log.i("Cars", it.size.toString())
                prognosisAdapter = PrognosisAdapter(cars, this)
                recyclerView.adapter = prognosisAdapter
            }
        })
        compareBtn.setOnClickListener {
            if (listToPrognosis.size == 2) {
                var betterCar = prognosisViewModel.prognosisValidation(listToPrognosis[0], listToPrognosis[1])

                var otherPrognosis = prognosisViewModel.otherPrognosis(listToPrognosis[0], listToPrognosis[1])
                showBetterDialog(betterCar)
            }
            else {
                Toast.makeText(mContext, R.string.compareEror, Toast.LENGTH_LONG).show()
            }
        }




        return view
    }

    override fun onItemClick(item: Car, position: Int) {
        //Toast.makeText(mContext, item.brand, Toast.LENGTH_LONG).show()
        carToCompare(item)

    }

    private fun carToCompare(item: Car) {
        if (listToPrognosis.size < 2) {

            if (listToPrognosis.size == 1 && listToPrognosis[0] == item) {
                listToPrognosis.remove(listToPrognosis[0])
                Toast.makeText(mContext, listToPrognosis.toString(), Toast.LENGTH_LONG).show()
            }
            else {
                listToPrognosis.add(item)
                Toast.makeText(mContext, listToPrognosis.toString(), Toast.LENGTH_LONG).show()
            }

        }
        else {
            if (listToPrognosis[0] == item) {
                listToPrognosis.remove(listToPrognosis[0])
                Toast.makeText(mContext, listToPrognosis.toString(), Toast.LENGTH_LONG).show()
            }
            else if (listToPrognosis[1] == item) {
                listToPrognosis.remove(listToPrognosis[1])
                Toast.makeText(mContext, listToPrognosis.toString(), Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(mContext, R.string.chooseCars, Toast.LENGTH_LONG).show()
        }
    }
    /*holder.itemView.setOnClickListener {

            //if ()
            *//*val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("id", allCars.get(position).id)
            holder.itemView.context.startActivity(intent)*//*
        }*/
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun showBetterDialog(car: Car) {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(R.string.prognosisResult)

        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.better_car_layout, null)

        val brandTV = view.findViewById<TextView>(R.id.brandTV)
        val modelTV = view.findViewById<TextView>(R.id.modelTV)
        val engTypeTV = view.findViewById<TextView>(R.id.engineTypeTV)
        val carTypeTV = view.findViewById<TextView>(R.id.carTypeTV)
        val colorTV = view.findViewById<TextView>(R.id.colorTV)
        val capacityTV = view.findViewById<TextView>(R.id.capacityTV)
        val powerTV = view.findViewById<TextView>(R.id.powerTV)
        val yearTV = view.findViewById<TextView>(R.id.yearTV)

        brandTV.text = car.brand
        modelTV.text = car.model
        colorTV.text = car.color
        powerTV.text = car.power.toString()
        capacityTV.text = car.engCap.toString()
        engTypeTV.text = car.engType.toString()
        yearTV.text = car.year.toString()
        carTypeTV.text = car.carType.toString()

        builder.setView(view)
        val alert = builder.create()

        builder.setPositiveButton("X", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                alert.cancel()
            }
        })
        alert.show()
    }

}