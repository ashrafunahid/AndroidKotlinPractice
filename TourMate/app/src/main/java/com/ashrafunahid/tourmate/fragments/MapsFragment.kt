package com.ashrafunahid.tourmate.fragments

import android.app.PendingIntent
import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ashrafunahid.tourmate.R
import com.ashrafunahid.tourmate.customdialogs.AddDestinationAlertDialog
import com.ashrafunahid.tourmate.receivers.GeofencingBroadcastReceiver
import com.ashrafunahid.tourmate.userlocation.isLocationPermissionGranted
import com.ashrafunahid.tourmate.viewmodels.LocationViewModel
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private lateinit var map: GoogleMap
    private val locationViewModel: LocationViewModel by activityViewModels()
    private lateinit var geoFencingClient: GeofencingClient
    private var geoFenceList = mutableListOf<Geofence>()
    private val PENDING_INTENT_REQUEST_CODE = 111
    private val pendingIntent: PendingIntent by lazy {
        val intent = Intent(requireActivity(), GeofencingBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(requireContext(), PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        map = googleMap
        showAlert("Set Location Alert", "Long press anywhere on the map to set a location alert.")

        if(isLocationPermissionGranted(requireActivity())) {
            map.isMyLocationEnabled = true
        }

        locationViewModel.location.observe(viewLifecycleOwner, { location ->
            /*
            Zoom Level -> (0 - 20f)
            1-5 -> Satellite View
            6-10 -> Continental View
            1-15 -> City View
            16-20 -> Street View
             */
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        location.latitude,
                        location.longitude
                    ), 15f
                )
            )

            map.setOnMapLongClickListener { latLng ->
                AddDestinationAlertDialog { string ->
                    createGeoFence(LatLng(location.latitude, location.longitude), string)
                }.show(childFragmentManager, "add_destination_dialog")
            }
        })
    }

    private fun createGeoFence(latLng: LatLng, label: String) {
        val geofence = Geofence.Builder()
            .setCircularRegion(latLng.latitude, latLng.longitude, 200f)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(10*60*60*1000)
            .setRequestId(label)
            .build()
        geoFenceList.add(geofence)

        val request = GeofencingRequest.Builder()
            .addGeofences(geoFenceList)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()
        geoFencingClient.addGeofences(request, pendingIntent)
            .addOnSuccessListener {
                Toast.makeText(requireActivity(), "Your place has been recorded for next alert", Toast.LENGTH_SHORT).show()
                addCircleOnMap(latLng)
            }
            .addOnFailureListener {  }
    }

    private fun addCircleOnMap(latLng: LatLng) {
        val options = CircleOptions()
            .center(latLng)
            .radius(200.0)
            .strokeWidth(1f)
            .strokeColor(R.color.teal_200)
        map.addCircle(options)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        geoFencingClient = LocationServices.getGeofencingClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun showAlert(title: String, body: String) {
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(body)
            .setPositiveButton("Ok", null)
        val dialog = builder.create()
        dialog.show()
    }
}