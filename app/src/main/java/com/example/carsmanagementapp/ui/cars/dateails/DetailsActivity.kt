package com.example.carsmanagementapp.ui.cars.dateails

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.ui.cars.CarsViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var brandTV: TextView
    private lateinit var modelTV: TextView
    private lateinit var colorTV: TextView
    private lateinit var capacityTV: TextView
    private lateinit var powerTV: TextView
    private lateinit var yearTV: TextView
    private lateinit var carTypeTV: TextView
    private lateinit var engineTypeTV: TextView

    //private lateinit var detailsViewModel: ViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initUI()

        val detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        var id = intent.getStringExtra("id")

        detailsViewModel.loadCar(id!!)

        detailsViewModel.carDetails.observe(this, {
            brandTV.text = it.brand
            modelTV.text = it.model
            colorTV.text = it.color
            capacityTV.text = it.engCap.toString()
            powerTV.text = it.power.toString()
            yearTV.text = it.year.toString()
            carTypeTV.text = it.carType.toString()
            engineTypeTV.text = it.engType.toString()
        })
    }
    fun initUI() {
        brandTV = findViewById(R.id.brandDetailsTV)
        modelTV = findViewById(R.id.modelDetailsTV)
        colorTV = findViewById(R.id.colorDetailsTV)
        capacityTV = findViewById(R.id.capacityDetailsTV)
        powerTV = findViewById(R.id.powerDetailsTV)
        yearTV = findViewById(R.id.yearDetailsTV)
        carTypeTV = findViewById(R.id.carTypeDetailsTV)
        engineTypeTV = findViewById(R.id.engineTypeDetailsTV)



    }
}