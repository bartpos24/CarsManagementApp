package com.example.carsmanagementapp.ui.prognosis

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    var list: ArrayList<Car> = ArrayList()
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
            if (list.size == 2) {

            }
            else {
                Toast.makeText(mContext, "Please select two cars to compare", Toast.LENGTH_LONG).show()
            }
        }




        return view
    }

    override fun onItemClick(item: Car, position: Int) {
        //Toast.makeText(mContext, item.brand, Toast.LENGTH_LONG).show()
        carToCompare(item)

    }

    private fun carToCompare(item: Car) {
        if (list.size < 2) {

            if (list.size == 1 && list[0] == item) {
                list.remove(list[0])
                Toast.makeText(mContext, list.toString(), Toast.LENGTH_LONG).show()
            }
            else {
                list.add(item)
                Toast.makeText(mContext, list.toString(), Toast.LENGTH_LONG).show()
            }

        }
        else {
            if (list[0] == item) {
                list.remove(list[0])
                Toast.makeText(mContext, list.toString(), Toast.LENGTH_LONG).show()
            }
            else if (list[1] == item) {
                list.remove(list[1])
                Toast.makeText(mContext, list.toString(), Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(mContext, "Selected two cars. Uncheck one of them and check other or compare them.", Toast.LENGTH_LONG).show()
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

}