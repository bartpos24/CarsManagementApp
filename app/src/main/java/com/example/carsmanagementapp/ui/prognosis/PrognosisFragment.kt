package com.example.carsmanagementapp.ui.prognosis

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.repositories.DatabaseRepository

class PrognosisFragment : Fragment(), OnCarClickListner, OnCarLongClickListener {

    private lateinit var prognosisViewModelFactory: PrognosisViewModelFactory
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

        val repository = DatabaseRepository()
        prognosisViewModelFactory = PrognosisViewModelFactory(repository)
        prognosisViewModel = ViewModelProviders.of(this, prognosisViewModelFactory).get(PrognosisViewModel::class.java)

        prognosisViewModel.carsLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                cars = it
                prognosisAdapter = PrognosisAdapter(cars, this, this)
                recyclerView.adapter = prognosisAdapter
            }
        })
        prognosisViewModel.carsMessageLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(mContext, it, Toast.LENGTH_LONG).show()
        })


        compareBtn.setOnClickListener {
            if (listToPrognosis.size == 2) {


                var betterCar = prognosisViewModel.otherPrognosis(listToPrognosis[0], listToPrognosis[1])
                var worseCar:Car

                if(betterCar.equals(listToPrognosis[0])) {

                    worseCar=listToPrognosis[1]

                }
                else
                {

                    worseCar=listToPrognosis[0]
                }
                showBetterDialog(betterCar,worseCar)
            }
            else {
                Toast.makeText(mContext, R.string.compareEror, Toast.LENGTH_LONG).show()
            }
        }




        return view
    }

    override fun onStart() {
        super.onStart()
        prognosisViewModel.loadDatabase()
    }

    override fun onItemClick(item: Car, position: Int) {
        //Toast.makeText(mContext, item.brand, Toast.LENGTH_LONG).show()
        carToCompare(item)

    }
    
    private fun carToCompare(item: Car) {
        if (listToPrognosis.size < 2) {

            if (listToPrognosis.size == 1 && listToPrognosis[0] == item) {
                listToPrognosis.remove(listToPrognosis[0])
            }
            else {
                listToPrognosis.add(item)
            }

        }
        else {
            if (listToPrognosis[0] == item) {
                listToPrognosis.remove(listToPrognosis[0])
            }
            else if (listToPrognosis[1] == item) {
                listToPrognosis.remove(listToPrognosis[1])
            }

        }
    }
    override fun onItemLongClick(item: Car, position: Int)
    {
        showCarDetails(item)
    }
    private fun showCarDetails(Car: Car)
    {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(R.string.prognosisCarDetails)

        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.car_detail_layout, null)

        val selectedBrandTV = view.findViewById<TextView>(R.id.selectedBrandTV)
        val selectedModelTV = view.findViewById<TextView>(R.id.selectedModelTV)
        val selectedEngTypeTV = view.findViewById<TextView>(R.id.selectedEngineTypeTV)
        val selectedCarTypeTV = view.findViewById<TextView>(R.id.selectedCarTypeTV)
        val selectedColorTV = view.findViewById<TextView>(R.id.selectedColorTV)
        val selectedCapacityTV = view.findViewById<TextView>(R.id.selectedCapacityTV)
        val selectedPowerTV = view.findViewById<TextView>(R.id.selectedPowerTV)
        val selectedYearTV = view.findViewById<TextView>(R.id.selectedYearTV)

        selectedBrandTV.text = Car.brand
        selectedModelTV.text = Car.model
        selectedColorTV.setBackgroundColor(Color.parseColor(Car.color))
        selectedPowerTV.text = Car.power.toString()
        selectedCapacityTV.text = Car.engCap.toString()
        selectedEngTypeTV.text = Car.engType.toString()
        selectedYearTV.text = Car.year.toString()
        selectedCarTypeTV.text = Car.carType.toString()



        builder.setView(view)
        val alert = builder.create()

        builder.setPositiveButton("X", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                alert.cancel()
            }
        })
        alert.show()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun showBetterDialog(betterCar: Car, worseCar: Car) {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(R.string.prognosisResult)


        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.better_car_layout, null)

        val betterBrandTV = view.findViewById<TextView>(R.id.betterBrandTV)
        val betterModelTV = view.findViewById<TextView>(R.id.betterModelTV)
        val betterEngTypeTV = view.findViewById<TextView>(R.id.betterEngineTypeTV)
        val betterCarTypeTV = view.findViewById<TextView>(R.id.betterCarTypeTV)
        val betterCapacityTV = view.findViewById<TextView>(R.id.betterCapacityTV)
        val betterPowerTV = view.findViewById<TextView>(R.id.betterPowerTV)
        val betterYearTV = view.findViewById<TextView>(R.id.betterYearTV)

        val worseBrandTV = view.findViewById<TextView>(R.id.worseBrandTV)
        val worseModelTV = view.findViewById<TextView>(R.id.worseModelTV)
        val worseEngTypeTV = view.findViewById<TextView>(R.id.worseEngineTypeTV)
        val worseCarTypeTV = view.findViewById<TextView>(R.id.worseCarTypeTV)
        val worseCapacityTV = view.findViewById<TextView>(R.id.worseCapacityTV)
        val worsePowerTV = view.findViewById<TextView>(R.id.worsePowerTV)
        val worseYearTV = view.findViewById<TextView>(R.id.worseYearTV)

        betterBrandTV.text = betterCar.brand
        betterModelTV.text = betterCar.model
        betterPowerTV.text = betterCar.power.toString()
        betterCapacityTV.text = betterCar.engCap.toString()
        betterEngTypeTV.text = betterCar.engType.toString()
        betterYearTV.text = betterCar.year.toString()
        betterCarTypeTV.text = betterCar.carType.toString()

        worseBrandTV.text = worseCar.brand
        worseModelTV.text = worseCar.model
        worsePowerTV.text = worseCar.power.toString()
        worseCapacityTV.text = worseCar.engCap.toString()
        worseEngTypeTV.text = worseCar.engType.toString()
        worseYearTV.text = worseCar.year.toString()
        worseCarTypeTV.text = worseCar.carType.toString()

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