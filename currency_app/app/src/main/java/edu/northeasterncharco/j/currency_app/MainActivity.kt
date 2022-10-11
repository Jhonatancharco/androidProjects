package edu.northeasterncharco.j.currency_app

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createSpinner() //creates the dropdown spinner to select currency
    }

    fun onCalculate(view: View){ //handles onCalculate click
        val formatter = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 2;//formats to two digits

        val input = editTextNumberDecimal.text.toString().toDouble()
        val currency = spinner.selectedItem.toString()//gets input
        formatter.currency = Currency.getInstance(currency)//formats output based on currency type
        val todayValues = RequestController(input, currency)//creates RequestController

        val newThread = Thread(todayValues) //starts a new thread to make a request to the API
        newThread.start()
        newThread.join()//Joins the thread
        //sets the output text to the correct amount and format
        editTextNumberDecimal2.setText(formatter.format(todayValues.calcCurrency()).toString())
    }

    private fun createSpinner(){ //creates the spinner dropdown
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.currency_array,//accesses the list of items
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)//selects the correct default resource for the spinner
            spinner.adapter = adapter
        }
    }
}