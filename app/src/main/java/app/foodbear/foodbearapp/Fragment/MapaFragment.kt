package app.foodbear.foodbearapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.foodbear.foodbearapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapaFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mapa, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        crearMarcador()
    }

    private fun crearMarcador() {
        val coordenadasSoriana = LatLng(25.7715142,-100.3230092)
        val markerSoriana = MarkerOptions().position(coordenadasSoriana).title("Soriana")
        map.addMarker(markerSoriana)

        val coordenadasSmart = LatLng(25.7222817,-100.4777101)
        val markerSmart = MarkerOptions().position(coordenadasSmart).title("S-Mart")
        map.addMarker(markerSmart)

        val coordenadasBodega = LatLng(25.7111128,-100.3894763)
        val markerBodega = MarkerOptions().position(coordenadasBodega).title("Bodega Aurrera")
        map.addMarker(markerBodega)

        val coordenadasHeb = LatLng(25.6934462,-100.4203756)
        val markerHeb = MarkerOptions().position(coordenadasHeb).title("H-E-B")
        map.addMarker(markerHeb)

        val coordenadasOxxo = LatLng(25.7470407,-100.4049575)
        val markerOxxo = MarkerOptions().position(coordenadasOxxo).title("OXXO")
        map.addMarker(markerOxxo)

        val coordenadasDulceria = LatLng(25.7008883,-100.3281428)
        val markerDulceria = MarkerOptions().position(coordenadasDulceria).title("Dulceria Gutierrez")
        map.addMarker(markerDulceria)

        val coordenadasTienda = LatLng(25.6779805,-100.3516988)
        val markerTienda = MarkerOptions().position(coordenadasTienda).title("Tienda Abarrotes Los Hermanos")
        map.addMarker(markerTienda)

        val coordenadasSeven = LatLng(25.7600749,-100.3989691)
        val markerSeven = MarkerOptions().position(coordenadasSeven).title("7-Eleven")
        map.addMarker(markerSeven)

        val coordenadasFruteria = LatLng(25.6614705,-100.2161438)
        val markerFruteria = MarkerOptions().position(coordenadasFruteria).title("Fruteria Doña Rosa")
        map.addMarker(markerFruteria)

        val coordenadasInicio = LatLng(25.6871137,-100.3805645)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadasInicio, 12f),
            4000,
            null
        )
    }


}


