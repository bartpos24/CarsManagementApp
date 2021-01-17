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
import androidx.lifecycle.ViewModelProviders
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.repositories.DatabaseRepository
import com.example.carsmanagementapp.ui.cars.CarsViewModel
import com.example.carsmanagementapp.ui.cars.dateails.update.UpdateActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsViewModel: DetailsViewModel
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
    private lateinit var detailsViewModelFactory: DetailsViewModelFactory
    private lateinit var car: Car



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initUI()

        val repository = DatabaseRepository()
        detailsViewModelFactory = DetailsViewModelFactory(repository)
        detailsViewModel = ViewModelProviders.of(this, detailsViewModelFactory).get(DetailsViewModel::class.java)
        var id = intent.getStringExtra("id")

        detailsViewModel.getCar(id!!)
        detailsViewModel.carLiveData.observe(this, Observer {
            car = it
            displayCar(car)
        })

        updateBtn.setOnClickListener {
            if (modelTV.text != "") {
                val intent = Intent(this, UpdateActivity::class.java)
                intent.putExtra("carToUpdate", car)
                startActivity(intent)
            }
            else
                Toast.makeText(this, R.string.updateError, Toast.LENGTH_LONG).show()

        }
        soldBtn.setOnClickListener {
            if (modelTV.text != "") {
                detailsViewModel.soldCar(car!!)
                clearET()
            }
            else {
                Toast.makeText(this, R.string.updateError, Toast.LENGTH_LONG).show()
            }

        }
        detailsViewModel.messageLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        deleteBtn.setOnClickListener {
            if (modelTV.text != "") {
                detailsViewModel.deleteCar(car!!)

                clearET()
            }
            else {
                Toast.makeText(this, R.string.updateError, Toast.LENGTH_LONG).show()
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
    private fun displayCar(car: Car) {
        brandTV.text = car.brand
        modelTV.text = car.model
        colorTV.text = car.color
        capacityTV.text = car.engCap.toString()
        powerTV.text = car.power.toString()
        yearTV.text = car.year.toString()
        carTypeTV.text = car.carType.toString()
        engineTypeTV.text = car.engType.toString()
    }
}