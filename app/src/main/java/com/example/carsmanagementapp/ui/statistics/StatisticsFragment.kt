package com.example.carsmanagementapp.ui.statistics

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import java.util.ArrayList

class StatisticsFragment : Fragment() {

    private lateinit var statisticsViewModel: StatisticsViewModel
    private var list: ArrayList<Car> = ArrayList()
    private var soldList: ArrayList<Car> = ArrayList()
    private lateinit var hetchbackActual: TextView
    private lateinit var hetchbackSold: TextView
    private lateinit var sedanActual: TextView
    private lateinit var sedanSold: TextView
    private lateinit var combiActual: TextView
    private lateinit var combiSold: TextView
    private lateinit var suvActual: TextView
    private lateinit var suvSold: TextView
    private lateinit var cabrioletActual: TextView
    private lateinit var cabrioletSold: TextView
    private lateinit var vanActual: TextView
    private lateinit var vanSold: TextView
    private lateinit var pickupActual: TextView
    private lateinit var pickupSold: TextView
    private lateinit var coupeActual: TextView
    private lateinit var coupeSold: TextView

    private lateinit var dieselActual: TextView
    private lateinit var dieselSold: TextView
    private lateinit var benzineActual: TextView
    private lateinit var benzineSold: TextView
    private lateinit var lpgActual: TextView
    private lateinit var lpgSold: TextView
    private lateinit var electricActual: TextView
    private lateinit var electricSold: TextView
    private lateinit var hybridActual: TextView
    private lateinit var hybridSold: TextView

    private lateinit var minA: TextView
    private lateinit var minS: TextView
    private lateinit var hundredA: TextView
    private lateinit var hundredS: TextView
    private lateinit var hundred50A: TextView
    private lateinit var hundred50S: TextView
    private lateinit var p200A: TextView
    private lateinit var p200S: TextView
    private lateinit var p300A: TextView
    private lateinit var p300S: TextView
    private lateinit var p400A: TextView
    private lateinit var p400S: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.statistics_fragment, container, false)
        hetchbackActual = view.findViewById(R.id.ha)
        hetchbackSold = view.findViewById(R.id.hs)
        sedanActual = view.findViewById(R.id.sa)
        sedanSold = view.findViewById(R.id.ss)
        combiActual = view.findViewById(R.id.ca)
        combiSold = view.findViewById(R.id.cs)
        suvActual = view.findViewById(R.id.suva)
        suvSold = view.findViewById(R.id.suvs)
        cabrioletActual = view.findViewById(R.id.caba)
        cabrioletSold = view.findViewById(R.id.cabs)
        vanActual = view.findViewById(R.id.va)
        vanSold = view.findViewById(R.id.vs)
        pickupActual = view.findViewById(R.id.pa)
        pickupSold = view.findViewById(R.id.ps)
        coupeActual = view.findViewById(R.id.coupea)
        coupeSold = view.findViewById(R.id.coupes)

        dieselActual = view.findViewById(R.id.dieselA)
        dieselSold = view.findViewById(R.id.dieselS)
        benzineActual = view.findViewById(R.id.benzineA)
        benzineSold = view.findViewById(R.id.benzineS)
        lpgActual = view.findViewById(R.id.lpgA)
        lpgSold = view.findViewById(R.id.lpgS)
        electricActual = view.findViewById(R.id.elektricA)
        electricSold = view.findViewById(R.id.elektricS)
        hybridActual = view.findViewById(R.id.hybridA)
        hybridSold = view.findViewById(R.id.hybridS)

        minA = view.findViewById(R.id.minA)
        minS = view.findViewById(R.id.minS)
        hundredA = view.findViewById(R.id.hundredA)
        hundredS = view.findViewById(R.id.hundredS)
        hundred50A = view.findViewById(R.id.hundred50A)
        hundred50S = view.findViewById(R.id.hundred50S)
        p200A = view.findViewById(R.id.p200A)
        p200S = view.findViewById(R.id.p200S)
        p300A = view.findViewById(R.id.p300A)
        p300S = view.findViewById(R.id.p300S)
        p400A = view.findViewById(R.id.p400A)
        p400S = view.findViewById(R.id.p400S)





        //carTypeChart = view.findViewById(R.id.carTypeChart)
        statisticsViewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)

        statisticsViewModel.loadDatabase()
        statisticsViewModel.loadSoldDatabase()
        statisticsViewModel.actualCars.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                list = it
                var carList = statisticsViewModel.loadCarType(list)
                var engineList = statisticsViewModel.loadEngineType(list)
                var powerList = statisticsViewModel.loadPower(list)

                hetchbackActual.text = carList[1].toString()
                sedanActual.text = carList[2].toString()
                combiActual.text = carList[3].toString()
                suvActual.text = carList[4].toString()
                cabrioletActual.text = carList[5].toString()
                vanActual.text = carList[6].toString()
                pickupActual.text = carList[7].toString()
                coupeActual.text = carList[8].toString()

                dieselActual.text = engineList[1].toString()
                benzineActual.text = engineList[2].toString()
                lpgActual.text = engineList[3].toString()
                electricActual.text = engineList[4].toString()
                hybridActual.text = engineList[5].toString()

                minA.text = powerList[0].toString()
                hundredA.text = powerList[1].toString()
                hundred50A.text = powerList[2].toString()
                p200A.text = powerList[3].toString()
                p300A.text = powerList[4].toString()
                p400A.text = powerList[5].toString()

            }
        })

        statisticsViewModel.soldCars.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                soldList = it
                var carList = statisticsViewModel.loadCarType(soldList)
                var engineList = statisticsViewModel.loadEngineType(soldList)
                var powerList = statisticsViewModel.loadPower(soldList)
                hetchbackSold.text = carList[1].toString()
                sedanSold.text = carList[2].toString()
                combiSold.text = carList[3].toString()
                suvSold.text = carList[4].toString()
                cabrioletSold.text = carList[5].toString()
                vanSold.text = carList[6].toString()
                pickupSold.text = carList[7].toString()
                coupeSold.text = carList[8].toString()

                dieselSold.text = engineList[1].toString()
                benzineSold.text = engineList[2].toString()
                lpgSold.text = engineList[3].toString()
                electricSold.text = engineList[4].toString()
                hybridSold.text = engineList[5].toString()

                minS.text = powerList[0].toString()
                hundredS.text = powerList[1].toString()
                hundred50S.text = powerList[2].toString()
                p200S.text = powerList[3].toString()
                p300S.text = powerList[4].toString()
                p400S.text = powerList[5].toString()
            }
        })




        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        statisticsViewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}