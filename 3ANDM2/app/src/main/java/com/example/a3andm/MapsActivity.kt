package com.example.a3andm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.clear()
        val bus = intent.getStringArrayListExtra("bus")
        val lat = intent.getStringArrayListExtra("lat")
        val lon = intent.getStringArrayListExtra("lon")
        val zoomLevel = 15.0f // 21 MAX
        val barca = LatLng(41.3851, 2.1734)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barca, zoomLevel))

        for (i in 0..2429) {
            val custom = lat?.get(i)?.let { lon?.get(i)?.let { it1 -> LatLng(
                    it.toDouble(),
                    it1.toDouble()
            ) } }
            mMap.addMarker(custom?.let { MarkerOptions().position(it).title(bus?.get(i)) })
        }
    }
}