package com.barmej.weatherapp.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.barmej.weatherapp.data.WeatherResponse
import com.barmej.weatherapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    //        way 2 okhttp3
    private val client = OkHttpClient()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFetch.setOnClickListener {

            makeRequestUsingOKHTTP(binding.inputName.text.toString())
            dayNowTime()


        }

    }

    private fun makeRequestUsingOKHTTP(name:String) {
      val url=HttpUrl.Builder()
        val request = Request.Builder()
            .url("         https://api.openweathermap.org/data/2.5/weather?q=$name&appid=c4c3e77cc72ef85edaa205bf5c1d1ca8\n")
            .build()

        client.newCall(request).enqueue(object : Callback {
            //if (result.cod == 200) {}
            override fun onFailure(call: Call, e: IOException) {
            //Toast.makeText(this,"fail : ${e.message}",Toast.LENGTH_SHORT).show()
               Log.i(TAG, "fail : ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                    response.body?.string()?.let { jsonString ->
                    val result = Gson().fromJson(jsonString, WeatherResponse::class.java)
                    Log.i(TAG, result.toString())

                       runOnUiThread {
                               // binding.testResult.text = response.body?.string()
                               binding.testCity.text = result.name.toString()
                               binding.windMeasurement.text = result.wind?.speed.toString()
                               binding.pressure.text = result.main?.pressure.toString()
                               binding.humidity.text = result.main?.humidity.toString()
                               binding.testTemperature.text = result.main?.temp.toString()
                             //  binding.weatherDescription.text = result.weather[2].description.toString()
                            result.weather.also { binding.weatherDescription.text = it[0].description.toString() }




                       }
                }
            }
        })        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dayNowTime() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ")
        val formatted = current.format(formatter)
        binding.date.text= formatted
//HH:mm:ss.SSS
    }
    companion object{
        const val  TAG = "Dunya_4Mar "
    }
    }
