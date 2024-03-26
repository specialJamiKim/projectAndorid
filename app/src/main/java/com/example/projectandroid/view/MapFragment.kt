package com.example.projectandroid.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.projectandroid.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class MapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        const val TAG = "MapFragment"
    }

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        mapView = rootView.findViewById(R.id.mapFragment)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@MapFragment)

        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val tag : String?
        val busanjin = LatLng(35.1630, 129.0628)

        currentMarker = googleMap.addMarker(
            MarkerOptions()
                .position(busanjin)
                .title("부산진구")
                .snippet("학원이 위치함")
                .apply {
                    // 마커에 가게 이름 연결
                    tag = "가게 이름"
                }
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(busanjin, 10f))

        // 정보 창 어댑터 설정
        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                // 기본 정보 창을 사용하지 않고 사용자 지정 정보 창을 사용하도록 반환
                return null
            }

            override fun getInfoContents(marker: Marker): View? {
                // 마커에 연결된 가게 이름을 가져옴
                val storeName = marker.tag as? String ?: return null

                // 정보 창을 표시할 뷰 생성
                val view = layoutInflater.inflate(R.layout.info_window_layout, null)
                // 뷰의 텍스트뷰에 가게 이름 설정
                view.findViewById<TextView>(R.id.storeNameTextView).text = storeName

                return view
            }
        })

        // 마커 클릭 시 정보 창 표시
        googleMap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }
}
