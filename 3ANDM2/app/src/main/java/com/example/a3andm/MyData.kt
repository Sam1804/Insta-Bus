package com.example.a3andm

import com.google.gson.annotations.SerializedName

data class MyData(@SerializedName ("id") val id: String, @SerializedName ("street_name") val street_name:String, @SerializedName ("city") val city:String, @SerializedName ("utm_x") val utm_x:String, @SerializedName ("utm_y") val utm_y:String, @SerializedName ("lat") val lat:String, @SerializedName ("lon") val lon:String, @SerializedName ("furniture") val furniture:String, @SerializedName ("buses") val buses:String)
data class Data ( @SerializedName ("data") val data:tmbs)
data class tmbs (@SerializedName ("tmbs") val tmbs:List<MyData>)
