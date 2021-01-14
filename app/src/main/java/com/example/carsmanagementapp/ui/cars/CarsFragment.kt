package com.example.carsmanagementapp.ui.cars

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R

class CarsFragment : Fragment() {

    lateinit var carsAdapter: CarsAdapter
    lateinit var carsMenager: RecyclerView.LayoutManager
    lateinit var recyclerView: RecyclerView
    private lateinit var mContext: Context

    private lateinit var carsViewModel: CarsViewModel
    var cars: ArrayList<Car> = ArrayList<Car>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cars_fragment, container, false)
        carsViewModel = ViewModelProvider(this).get(CarsViewModel::class.java)
        carsMenager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = view.findViewById(R.id.recyclerAllCars) as RecyclerView

        recyclerView.layoutManager = carsMenager


        carsViewModel.loadDatabase()

        carsViewModel.actualListOfCar.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                cars = it
                carsAdapter = CarsAdapter(cars)
                recyclerView.adapter = carsAdapter

            }

        })

        return view
    }

}