package com.example.carsmanagementapp.ui.cars.dateails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.ui.cars.CarsViewModel
import com.example.carsmanagementapp.ui.cars.dateails.update.UpdateActivity
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
    private lateinit var updateBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var soldBtn: Button

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

        updateBtn.setOnClickListener {
            if (modelTV.text != "") {
                var car = detailsViewModel.carDetails.value
                val intent = Intent(this, UpdateActivity::class.java)
                intent.putExtra("carToUpdate", car)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "Car was solded or deleted", Toast.LENGTH_LONG).show()

        }
        soldBtn.setOnClickListener {
            if (modelTV.text != "") {
                var car = detailsViewModel.carDetails.value
                detailsViewModel.soldCar(car!!)

                detailsViewModel.result.observe(this, Observer {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                })

                clearET()
            }
            else {
                Toast.makeText(this, "Car was solded or deleted", Toast.LENGTH_LONG).show()
            }

        }
        deleteBtn.setOnClickListener {
            if (modelTV.text != "") {
                var car = detailsViewModel.carDetails.value
                detailsViewModel.deleteCar(car!!)

                detailsViewModel.result.observe(this, Observer {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                })

                clearET()
            }
            else {
                Toast.makeText(this, "Car was solded or deleted", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun initUI() {
        brandTV = findViewById(R.id.brandDetailsTV)
        modelTV = findViewById(R.id.modelDetailsTV)
        colorTV = findViewById(R.id.colorDetailsTV)
        capacityTV = findViewById(R.id.capacityDetailsTV)
        powerTV = findViewById(R.id.powerDetailsTV)
        yearTV = findViewById(R.id.yearDetailsTV)
        carTypeTV = findViewById(R.id.carTypeDetailsTV)
        engineTypeTV = findViewById(R.id.engineTypeDetailsTV)
        updateBtn = findViewById(R.id.updateButton)
        deleteBtn = findViewById(R.id.deleteButton)
        soldBtn = findViewById(R.id.soldButton)

    }
    private fun clearET() {
        brandTV.text = ""
        modelTV.text = ""
        colorTV.text = ""
        carTypeTV.text = ""
        engineTypeTV.text = ""
        powerTV.text = ""
        yearTV.text = ""
        capacityTV.text = ""
    }
}