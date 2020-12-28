package com.example.carsmanagementapp.ui.cars

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.Model.Enum.CarType
import com.example.carsmanagementapp.Model.Enum.EngineType
import com.example.carsmanagementapp.R

class CarsFragment : Fragment() {

    lateinit var carsAdapter: CarsAdapter
    lateinit var carsMenager: RecyclerView.LayoutManager
    lateinit var recyclerView: RecyclerView

    private lateinit var carsViewModel: CarsViewModel
    var cars: List<Car> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.cars_fragment, container, false)
        carsViewModel = ViewModelProvider(this).get(CarsViewModel::class.java)
        carsMenager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = root.findViewById(R.id.recyclerAllCars) as RecyclerView

        recyclerView.layoutManager = carsMenager

        carsViewModel.loadDatabase()

        cars = carsViewModel.actualListOfCars
        carsAdapter = CarsAdapter(cars)
        recyclerView.adapter = carsAdapter





        return root
    }

}