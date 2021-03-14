package com.example.a3andm

import retrofit2.Call
import retrofit2.http.GET

interface Interface {
        @GET("bus/stations.json")
        fun BusList(): Call<Data>
}