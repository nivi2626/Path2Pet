package huji.post_pc.path2pet.LostProcess

import android.Manifest
import android.location.Address
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.model.LatLng

import android.location.Geocoder
import androidx.appcompat.widget.SearchView
import java.lang.Exception
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.MapStyleOptions
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.R
import java.util.*


class Fragment_b_Map : Fragment() {
    private lateinit var mMap: GoogleMap
    private lateinit var myContext: Context
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation: LatLng
    private lateinit var lostPetActivityInstance: LostPetProcess

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_b_map, container, false)
        myContext = view.context
        lostPetActivityInstance = activity as LostPetProcess
        // initial latlng for Tel Aviv
        var latLng: LatLng = LatLng(32.098051, 34.791873)
        var latitude: String = "32.098051"
        var longitude: String = "34.791873"


        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        var searchView: SearchView = view.findViewById(R.id.idSearchView)


        // upload data from sp
        latitude =
            lostPetActivityInstance.sp.getString(AppPath2Pet.SP_LATITUDE, latitude).toString()
        longitude =
            lostPetActivityInstance.sp.getString(AppPath2Pet.SP_LONGITUDE, longitude).toString()
        latLng = LatLng(latitude.toDouble(), longitude.toDouble())

        // google map
        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            mMap = it

            val point = CameraUpdateFactory.newLatLng(latLng)
            val home = mMap.addMarker(MarkerOptions().position(latLng).title("Home"))
            home.tag = -1
            // moves camera to coordinates
            mMap.moveCamera(point)
            // animates camera to coordinates
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))

            it.setOnMapClickListener { coordinate ->
                it.clear()
                it.addMarker(MarkerOptions().position(coordinate))
                latLng = LatLng(coordinate.latitude, coordinate.longitude)
            }

        })

        // search view query
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                val searchInitLocation = searchView.query.toString()
                var addressList: List<Address>?
                if (searchInitLocation != "") {
                    val geocoder = Geocoder(view.context)
                    try {
                        addressList = geocoder.getFromLocationName(searchInitLocation, 1)
                        if (addressList.isEmpty()) {
                            throw Exception()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return false
                    }
                    val address: Address = addressList!![0]
                    latLng = LatLng(address.latitude, address.longitude)
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(latLng).title(searchInitLocation))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false

            }
        })

        // next listener
        nextButton.setOnClickListener {
            lostPetActivityInstance.progressBar.incrementProgressBy(1)
            with(lostPetActivityInstance.sp.edit()) {
                putString(AppPath2Pet.SP_LATITUDE, latLng.latitude.toString())
                putString(AppPath2Pet.SP_LONGITUDE, latLng.longitude.toString())
                apply()
            }
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            lostPetActivityInstance.onBackPressed()
        }

        return view
    }

    private fun nextButtonOnClick(view: View) {
        Navigation.findNavController(view).navigate(R.id.fragmentTypeSex)
    }

}