package edu.northeasterncharco.j.currency_app

import java.net.*
import org.json.JSONObject

class RequestController(private var dollars: Double, private var currency: String): Runnable {
    private val url = URL("https://v6.exchangerate-api.com/v6/eb2154150871bf2fad53ddc2/latest/USD")//API string
    private var rate = 0.0 //store for rate data

    override fun run() {
        try { //try catch block that makes the request to the API
            val response = JSONObject(url.readText())//GET request
            val rates = response.getJSONObject("conversion_rates")
            rate = rates.get(currency) as Double //saves the rate to the class
        } catch (ex: Exception){ex.printStackTrace();}
    }

    fun calcCurrency(): Double{
        return (dollars * rate) //calculation helper
    }

}