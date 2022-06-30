package com.machineries_pk.mrk.activities.NewCode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.databinding.ActivityCalculateResultBinding
import io.paperdb.Paper

class CalculateResultActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    lateinit var binding: ActivityCalculateResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val mapFragment: SupportMapFragment? = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)


    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        p0.getUiSettings()
        // Add a marker in Sydney and move the camera
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(0.0, 0.0)
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
        //        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18f))



        mMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE

        p0.uiSettings.setAllGesturesEnabled(true)
    }
}