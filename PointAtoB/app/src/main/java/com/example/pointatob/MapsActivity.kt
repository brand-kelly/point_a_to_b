package com.example.pointatob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.pointatob.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import kotlin.time.toDuration

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val TAG = "MapsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        val sydney = LatLng(-33.852, 151.211)
        googleMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
        )
        // Set lat and long values, place names
        val intent = intent
//        val latA = intent.getDoubleExtra("latA", 0.0)
//        val latB = intent.getDoubleExtra("latB", 0.0)
//        val longA = intent.getDoubleExtra("longA", 0.0)
//        val longB = intent.getDoubleExtra("longB", 0.0)
//        val starting = LatLng(latA, longA)
//        val destination = LatLng(latB, longB)
        val pointA = intent.getStringExtra("pointA")
        val pointB = intent.getStringExtra("pointB")

        val latA = intent.extras!!.getDouble("latA")
        val longA = intent.extras!!.getDouble("longA")
        val starting = LatLng(latA, longA)
        val latB = intent.extras!!.getDouble("latB")
        val longB = intent.extras!!.getDouble("longB")
        val destination = LatLng(latB, longB)
        Log.i(TAG, "latA: {$latA} is double. longA: {$longA}")


//        LatLngBounds.Builder b = new LatLngBounds.Builder();
//        for (Marker m : markers) {
//
//        }

        // Add both markers and move the camera
        mMap.addMarker(MarkerOptions().position(starting).title(pointA))
        mMap.addMarker(MarkerOptions().position(destination).title(pointB))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(starting))

    }

}
