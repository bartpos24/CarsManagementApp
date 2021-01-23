package com.example.carsmanagementapp.ui.cars.dateails.update

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.Model.Enum.CarType
import com.example.carsmanagementapp.Model.Enum.EngineType
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.repositories.DatabaseRepository
import com.example.carsmanagementapp.ui.cars.dateails.DetailsViewModel
import com.example.carsmanagementapp.utils.DecimalDigitsInputFilter
import com.example.carsmanagementapp.utils.MaxLengthInputFilter
import com.skydoves.colorpickerview.ColorPickerView
import com.skydoves.colorpickerview.flag.BubbleFlag
import com.skydoves.colorpickerview.flag.FlagMode
import com.skydoves.colorpickerview.listeners.ColorListener
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar
import java.lang.NumberFormatException
import java.util.*

class UpdateActivity : AppCompatActivity() {

    private lateinit var updateViewModelFactory: UpdateViewModelFactory
    private lateinit var updateViewModel: UpdateViewModel
    private lateinit var updateBtn: Button
    private lateinit var brandET: EditText
    private lateinit var modelET: EditText
    private lateinit var yearET: EditText
    private lateinit var capacityET: EditText
    private lateinit var powerET: EditText
    private lateinit var colorBT: Button
    private lateinit var engTypeSpinner: Spinner
    private lateinit var carTypeSpinner: Spinner
    private var colorstr:String = ""
    var typeOfCar = CarType.NONE
    var typeOfEngine = EngineType.NONE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        var car = intent.getSerializableExtra("carToUpdate") as Car

        updateBtn = findViewById(R.id.updateBtnUp)
        val repository = DatabaseRepository()
        updateViewModelFactory = UpdateViewModelFactory(repository)
        updateViewModel = ViewModelProviders.of(this, updateViewModelFactory).get(UpdateViewModel::class.java)

        initUI()

        displayCar(car)

        engTypeSpinner.setSelection(getIndex(engTypeSpinner, car.engType.toString()))
        carTypeSpinner.setSelection(getIndex(carTypeSpinner, car.carType.toString()))

        engTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                engSelectedView(position)
            }
        }
        carTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                carSelectedView(position)
            }
        }

        colorBT.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.colorpicker_header)

            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.color_picker, null)
            val btAccept =  view.findViewById<TextView>(R.id.BtAccept)
            val btDecline =  view.findViewById<TextView>(R.id.BtDecline)
            var tempColor:String = ""
            val colorPicker = view.findViewById<ColorPickerView>(R.id.colorPickerView)
            colorPicker.selectCenter()
            val bubbleFlag = BubbleFlag(this)
            bubbleFlag.flagMode = FlagMode.FADE
            colorPicker.setFlagView(bubbleFlag)

            val brightnessSlideBar = view.findViewById<BrightnessSlideBar>(R.id.brightnessSlideBar)


            colorPicker.attachBrightnessSlider(brightnessSlideBar)


            builder.setView(view)
            val alert = builder.create()


            colorPicker.setColorListener(ColorListener { color, fromUser ->


                tempColor = String.format("#%06X", 0xFFFFFF and color)

            })

            btAccept.setOnClickListener {
                colorstr = tempColor
                colorBT.setBackgroundColor(Color.parseColor(tempColor))
                colorBT.text = ""
                alert.cancel();
            }

            btDecline.setOnClickListener {
                alert.cancel();
            }
            alert.show()
        }

        updateBtn.setOnClickListener {
            var validation = validationCar()
            if (validation) {
                val car = Car(car.id, brandET.text.toString(), modelET.text.toString(), typeOfCar, capacityET.text.toString().toDouble(), powerET.text.toString().toInt(), yearET.text.toString().toInt(), typeOfEngine, colorstr)
                updateViewModel.updateCar(car)
            }
            else {
                //Potencjalna obsługa błędu

            }
            /*var cap = capSelectedView()
            var pow = powerSelectedView()
            var year = yearSelectedView()
            if (cap != 0.0 && pow != 0 && year != 0 && typeOfEngine != EngineType.NONE && typeOfCar != CarType.NONE) {
                if (brandET.text.toString() == "") {
                    brandET.error = resources.getString(R.string.brandError)
                }
                else if (modelET.text.toString() == "") {
                    modelET.error = resources.getString(R.string.modelError)
                }
                else if (colorET.text.toString() == "") {
                    colorET.error = resources.getString(R.string.colorError)
                }
                else {
                    val car = Car(car.id, brandET.text.toString(), modelET.text.toString(), typeOfCar, cap, pow, year, typeOfEngine, colorET.text.toString())
                    updateViewModel.updateCar(car)

                }

            }
            */
        }

        updateViewModel.messageLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun validationCar() : Boolean{
        var validation: Boolean = true
        if (brandET.text.toString() == "") {
            brandET.error = resources.getString(R.string.brandError)
            validation = false
        }
        if (modelET.text.toString() == "") {
            modelET.error = resources.getString(R.string.modelError)
            validation = false
        }
        colorBT.error = null
        if (colorstr == "") {
            colorBT.error = resources.getString(R.string.colorError)
            validation = false
        }
        var cap = capSelectedView()

        if (cap <= 0.0) {
            capacityET.error = resources.getString(R.string.capacityError)
            validation = false
        }
        else if(cap > 10.0)
        {
            capacityET.error = resources.getString(R.string.capacityError2)
            validation = false
        }
        var pow = powerSelectedView()
        if (pow == 0 || pow < 0) {
            powerET.error = resources.getString(R.string.powerError)
            validation = false
        }
        else if(pow > 1000)
        {
            powerET.error = resources.getString(R.string.powerError2)
            validation = false
        }
        var year = yearSelectedView()
        if (year <1945){
            yearET.error = resources.getString(R.string.yearError1)
            validation = false
        }
        else if(year > Calendar.getInstance().get(Calendar.YEAR)){
            yearET.error = resources.getString(R.string.yearError2)
            validation = false
        }


        if (typeOfEngine == EngineType.NONE) {
            //Toast.makeText(mContext, R.string.engineError, Toast.LENGTH_LONG).show()
            validation = false
        }
        if (typeOfCar == CarType.NONE) {
            //Toast.makeText(mContext, R.string.carError, Toast.LENGTH_LONG).show()
            validation = false
        }

        return validation

    }

    private fun capSelectedView(): Double {
        var capacity: Double = 0.0
        try {
            capacity = capacityET.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            capacityET.error = resources.getString(R.string.doubleError)
        } catch (e: Throwable) {
            capacityET.error = resources.getString(R.string.error)
        }

        return capacity
    }
    private fun powerSelectedView(): Int {
        var power: Int = 0
        try {
            power = powerET.text.toString().toInt()
        }
        catch (e: NumberFormatException) {
            powerET.error = resources.getString(R.string.intError)
        }
        catch (e: Throwable) {
            powerET.error = resources.getString(R.string.error)
        }

        return power
    }
    private fun yearSelectedView(): Int {
        var year: Int = 0
        try {
            year = yearET.text.toString().toInt()
        }
        catch (e: NumberFormatException) {
            yearET.error = resources.getString(R.string.yearError)
        }
        catch (e: Throwable) {
            yearET.error = resources.getString(R.string.error)
        }

        return year
    }

   /* private fun capSelectedView(): Double {
        var capacity: Double = 0.0
        try {
            capacity = capacityET.text.toString().toDouble()
        }
        catch (e: NumberFormatException) {
            Toast.makeText(this, R.string.doubleError, Toast.LENGTH_LONG).show()
        }
        catch (e: Throwable) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
        }

        return capacity

    }
    private fun powerSelectedView(): Int {
        var power: Int = 0
        try {
            power = powerET.text.toString().toInt()
        }
        catch (e: NumberFormatException) {
            Toast.makeText(this, R.string.intError, Toast.LENGTH_LONG).show()
        }
        catch (e: Throwable) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
        }

        return power
    }
    private fun yearSelectedView(): Int {
        var year: Int = 0
        try {
            year = yearET.text.toString().toInt()
        }
        catch (e: NumberFormatException) {
            Toast.makeText(this, R.string.yearError, Toast.LENGTH_LONG).show()
        }
        catch (e: Throwable) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
        }

        return year
    }

    */
    private fun getIndex(spinner: Spinner, myString: String): Int {
        var index: Int = 0
        var h = spinner.count - 1
        for (i in 0..h) {
            if (myString == spinner.adapter.getItem(i)) {
                index = i
                break
            }
        }
        return index
    }
    private fun engSelectedView(position: Int): EngineType {
        if (position == 0)
            typeOfEngine = EngineType.DIESEL
        else if (position == 1)
            typeOfEngine = EngineType.BENZINE
        else if (position == 2)
            typeOfEngine = EngineType.LPG
        else if (position == 3)
            typeOfEngine = EngineType.ELECTRIC
        else if (position == 4)
            typeOfEngine = EngineType.HYBRID
        else
            Toast.makeText(this, R.string.engineError, Toast.LENGTH_LONG).show()

        return typeOfEngine
    }
    private fun carSelectedView(position: Int) {
        if (position == 0)
            typeOfCar = CarType.HETCHBACK
        else if (position == 1)
            typeOfCar = CarType.SEDAN
        else if (position == 2)
            typeOfCar = CarType.COMBI
        else if (position == 3)
            typeOfCar = CarType.SUV
        else if (position == 4)
            typeOfCar = CarType.CABRIOLET
        else if (position == 5)
            typeOfCar = CarType.VAN
        else if (position == 6)
            typeOfCar = CarType.PICKUP
        else if (position == 7)
            typeOfCar = CarType.COUPE
        else
            Toast.makeText(this, R.string.carError, Toast.LENGTH_LONG).show()
    }
    private fun initUI() {
        brandET = findViewById(R.id.brandEditTextUp)
        modelET = findViewById(R.id.modelEditTextUp)
        yearET = findViewById(R.id.yearEditTextUp)
        capacityET = findViewById(R.id.capEditTextUp)
        powerET = findViewById(R.id.powerEditTextUp)
        colorBT = findViewById(R.id.colorButtonUp)
        engTypeSpinner = findViewById(R.id.engTypeSpinnerUp)
        carTypeSpinner = findViewById(R.id.carTypeSpinnerUp)

        capacityET.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(1))
        powerET.filters = arrayOf<InputFilter>(MaxLengthInputFilter(4))

        updateBtn = findViewById(R.id.updateBtnUp)

        var engAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.engType, android.R.layout.simple_spinner_item)
        engAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        engTypeSpinner.adapter = engAdapter

        var carAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.carType, android.R.layout.simple_spinner_item)
        carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        carTypeSpinner.adapter = carAdapter
    }
    private fun displayCar(car: Car) {
        brandET.text = Editable.Factory.getInstance().newEditable(car.brand)
        modelET.text = Editable.Factory.getInstance().newEditable(car.model)
        yearET.text = Editable.Factory.getInstance().newEditable(car.year.toString())
        capacityET.text = Editable.Factory.getInstance().newEditable(car.engCap.toString())
        powerET.text = Editable.Factory.getInstance().newEditable(car.power.toString())
        colorBT.setBackgroundColor(Color.parseColor(car.color))
    }
}