package com.example.user.projectcoordinatesdb2;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements LocationListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap googleMap;
    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> arrayPoints;
    private Marker myMarker;
    private static int LOAD_IMAGE_RESULTS = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        arrayPoints = new ArrayList<LatLng>();

        MapsInitializer.initialize(getApplicationContext());
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap = supportMapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        //googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);

    }


    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        //googleMap.addMarker(new MarkerOptions().position(latLng));
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));



    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {
        final MarkerOptions markerOptions = new MarkerOptions();
        double latitude=latLng.latitude;
        double longitude=latLng.longitude;

        // Setting latitude and longitude of the marker position
        markerOptions.position(latLng);

        // Setting titile of the infowindow of the marker
        markerOptions.title("현재위치");

        // Setting the content of the infowindow of the marker
        markerOptions.snippet("위도:" + latitude + "," + "경도:" + longitude);

        // Instantiating the class PolylineOptions to plot polyline in the map
        PolylineOptions polylineOptions = new PolylineOptions();
        // Setting the color of the polyline
        polylineOptions.color(Color.RED);
        // Setting the width of the polyline
        polylineOptions.width(3);
        // Adding the taped point to the ArrayList
        arrayPoints.add(latLng);
        // Setting points of polyline
        polylineOptions.addAll(arrayPoints);
        // Adding the polyline to the map
        googleMap.addPolyline(polylineOptions);

        // Adding the marker to the map
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        //registerForContextMenu();
       /* googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                return false;
            }
        });
        */

    }


    @Override
    public void onMapLongClick(LatLng arg0) {
        googleMap.clear();
        arrayPoints.clear();

    }




}
