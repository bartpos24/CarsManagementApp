package com.example.carsmanagementapp.ui.prognosis

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.ui.cars.CarsAdapter

class PrognosisFragment : Fragment() {

    private lateinit var prognosisViewModel: PrognosisViewModel
    private lateinit var recyclerView: RecyclerView
    lateinit var prognosisAdapter: PrognosisAdapter
    lateinit var prognosisMenager: RecyclerView.LayoutManager
    var cars: ArrayList<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.prognosis_fragment, container, false)
        recyclerView = view!!.findViewById(R.id.recyclerPrognosis)

        prognosisMenager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = prognosisMenager

        prognosisViewModel = ViewModelProvider(this).get(PrognosisViewModel::class.java)

        prognosisViewModel.loadDatabase()

        prognosisViewModel.actualCarList.observe(viewLifecycleOwner, {
            if (it != null) {
                cars = it
                Log.i("Cars", it.size.toString())
                prognosisAdapter = PrognosisAdapter(cars)
                recyclerView.adapter = prognosisAdapter
            }
        })


        return view
    }

}