package com.example.carsmanagementapp.ui.cars

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.repositories.DatabaseRepository

class CarsFragment : Fragment() {

    lateinit var carsAdapter: CarsAdapter
    lateinit var carsMenager: RecyclerView.LayoutManager
    lateinit var recyclerView: RecyclerView
    lateinit var carsViewModelFactory: CarsViewModelFactory
    private lateinit var mContext: Context

    private lateinit var carsViewModel: CarsViewModel
    var cars: ArrayList<Car> = ArrayList<Car>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cars_fragment, container, false)
        val repository = DatabaseRepository()
        carsViewModelFactory = CarsViewModelFactory(repository)
        carsViewModel = ViewModelProviders.of(this, carsViewModelFactory).get(CarsViewModel::class.java)
        carsMenager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = view.findViewById(R.id.recyclerAllCars) as RecyclerView

        recyclerView.layoutManager = carsMenager


        carsViewModel.carsLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                cars = it
                carsAdapter = CarsAdapter((cars))
                recyclerView.adapter = carsAdapter
            }
        })
        carsViewModel.carsMessageLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(mContext, it, Toast.LENGTH_LONG).show()
        })

        return view
    }

    override fun onStart() {
        super.onStart()
        carsViewModel.getDatabase()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}