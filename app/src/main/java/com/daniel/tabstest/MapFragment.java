package com.daniel.tabstest;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MapFragment extends SupportMapFragment implements
        OnMapReadyCallback {

    private final LatLng HAMBURG = new LatLng(53.558, 9.927);
    private final LatLng KIEL = new LatLng(53.551, 9.993);

    private static final String ARG_SECTION_NUMBER = "section_number";

    private GoogleMap mMap;
    private Marker marker;

    String user_photo_url = "http://lh3.googleusercontent.com/-9NsxYGINJ5w/AAAAAAAAAAI/AAAAAAAAAL0/KcvvUchQhJE/s120-c/photo.jpg";
    LatLng latLng = new LatLng(37.7576792,-122.5078121);

    public MapFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("MyMap", "onResume");
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {

            Log.d("MyMap", "setUpMapIfNeeded");

            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MyMap", "onMapReady");
        mMap = googleMap;
        setUpMap();
    }

    private void setUpMap() {

        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setMapToolbarEnabled(false);


        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setMapToolbarEnabled(false);



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);


        Picasso.with(getActivity())
                .load(user_photo_url)
                .resize(250,250)
                .centerCrop()
                .transform(new BubbleTransformation(20))
                .into(mTarget);


                        //https://lh3.googleusercontent.com/-9NsxYGINJ5w/AAAAAAAAAAI/AAAAAAAAAL0/KcvvUchQhJE/s120-c/photo.jpg

        /*
        Marker hamburg = mMap.addMarker(new MarkerOptions().position(HAMBURG)
                .title("Hamburg"));
        Marker kiel = mMap.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Kiel")
                .snippet("Kiel is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_launcher)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        */
    }

    Target mTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Marker driver_marker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                            .title("test")
                            .snippet("test address")
            );
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Log.d("picasso", "onBitmapFailed");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

}