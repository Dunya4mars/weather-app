package com.barmej.weatherapp.data

import com.google.gson.annotations.SerializedName

data class Coord (

    @SerializedName("lon" ) var lon : Int? = null,
    @SerializedName("lat" ) var lat : Int? = null

)