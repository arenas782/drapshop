package com.drapshop.ui.maps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.drapshop.R
import com.drapshop.data.Store
import com.drapshop.databinding.FragmentMapsBinding
import com.drapshop.ui.home.HomeFragment
import com.drapshop.utils.Utilities
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapsFragment : Fragment(), OnMapReadyCallback ,GoogleMap.OnMarkerClickListener{

    private lateinit var binding: FragmentMapsBinding
    private lateinit var mMap: GoogleMap
    companion object {
        var mapFragment : SupportMapFragment?=null

    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_maps, container, false
        )

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)


        binding.lifecycleOwner = this



        return binding.root
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        val florida = LatLng(25.815667477231152, -80.26888286818175)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(florida))
        mMap.setMinZoomPreference(10f)


        val jsonFileString = Utilities.getJsonDataFromAsset(requireContext(), "stores.json")
        val gson = Gson()
        val listPersonType = object : TypeToken<List<Store>>() {}.type
        var stores: List<Store> = gson.fromJson(jsonFileString, listPersonType)
        stores.forEach {
            val coords = LatLng(it.latitude,it.longitude)
            mMap.addMarker(MarkerOptions().position(coords).title(it.name))
        }
        mMap.setOnMarkerClickListener (this)

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        if (p0 != null) {
            Log.e("Clicked",p0.title.toString())
        }
        return false
    }
}