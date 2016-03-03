package com.daniel.tabstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class CheckMapActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    GoogleMap googleMap;
    List<MapLocation> restaurantList;
    List<MapLocation> barList;
    List<Marker> restaurantMarkers = new ArrayList<>();
    List<Marker> barMarkers = new ArrayList<>();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_map);

        //Eventually load from a database
        //Hard coded here for now
        restaurantList = new ArrayList<>();
        barList =  new ArrayList<>();

        MapLocation r1 = new MapLocation(37.78344,-122.42578, "Restaurant One" );
        MapLocation r2 = new MapLocation(37.7876965,-122.4106738, "Restaurant Two" );
        restaurantList.add(r1);
        restaurantList.add(r2);

        MapLocation b1 = new MapLocation(37.7866028,-122.4044511, "Bar One" );
        MapLocation b2 = new MapLocation(37.7864459,-122.4090323, "Bar Two" );
        barList.add(b1);
        barList.add(b2);

        CheckBox checkRestaurants = (CheckBox) findViewById(R.id.checkRestaurants);
        checkRestaurants.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    showRestaurants();
                } else {
                    hideRestaurants();
                }
            }
        });

        CheckBox checBars = (CheckBox) findViewById(R.id.checkBars);
        checBars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    showBars();
                } else {
                    hideBars();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;
        setUpMap();
    }

    public void setUpMap() {

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

    }

    public void showRestaurants() {

        restaurantMarkers.clear();
        for (MapLocation loc : restaurantList){
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(loc.lat, loc.lon))
                    .title(loc.title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(loc.lat, loc.lon)).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

            restaurantMarkers.add(marker);
        }
    }

    public void showBars() {

        barMarkers.clear();
        for (MapLocation loc : barList){
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(loc.lat, loc.lon))
                    .title(loc.title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(loc.lat, loc.lon)).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

            barMarkers.add(marker);
        }
    }

    public void hideRestaurants(){
        for (Marker marker : restaurantMarkers){
            marker.remove();
        }
    }

    public void hideBars(){
        for (Marker marker : barMarkers){
            marker.remove();
        }
    }

    public class MapLocation {
        public MapLocation(double lt, double ln, String t){
            lat = lt;
            lon = ln;
            title = t;
        }
        public double lat;
        public double lon;
        public String title;
    }

}