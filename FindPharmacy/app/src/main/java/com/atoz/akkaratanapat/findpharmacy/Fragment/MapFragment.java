package com.atoz.akkaratanapat.findpharmacy.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.atoz.akkaratanapat.findpharmacy.Activity.HomeActivity;
import com.atoz.akkaratanapat.findpharmacy.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HomeActivity activity;
    private ArrayList<Circle> pin = new ArrayList<>(), rad = new ArrayList<>();


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (HomeActivity) getActivity();
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment myMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_working);
        myMapFragment.getMapAsync(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    private void drawCircle(int color, LatLng latlng) {
        mMap.addCircle(new CircleOptions()
                .center(latlng)
                .radius(1000)
                .fillColor(color)).setStrokeColor(color);
    }

    public void zoom(LatLng latLng, int index) {
        rad.get(activity.index).setRadius(100);
        rad.get(index).setRadius(300);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //ActivityCompat.requestPermissions(getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
            //return;
        }
        else{
            mMap.setMyLocationEnabled(true);
        }
        //mMap.setMyLocationEnabled(true);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                activity.dataSet.get(0).getPharmacy().getLocation(), 15));

        for (int count = 0; count < activity.dataSet.size(); count++) {
            pin(count);
        }
        rad.get(activity.index).setRadius(300);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                activity.onMarkerClick("");
                return false;
            }
        });
    }

    public void pin(int count){
        int colour = 0,clourRad = 0;
        if(activity.dataSet.get(count).getType() == 0){
            colour = ContextCompat.getColor(getContext(), R.color.colorPrimaryPin);
            clourRad = ContextCompat.getColor(getContext(), R.color.colorPrimaryRad);
        }else {
            colour = ContextCompat.getColor(getContext(), R.color.colorSecondaryPin);
            clourRad = ContextCompat.getColor(getContext(), R.color.colorSecondaryRad);
        }
        Circle circle = mMap.addCircle(new CircleOptions().center(activity.dataSet.get(count)
                .getPharmacy().getLocation()).radius(20)
                .fillColor(colour));
        circle.setStrokeColor(colour);
        ;
        Circle circle2 = mMap.addCircle(new CircleOptions().center(activity.dataSet.get(count)
                .getPharmacy().getLocation()).radius(100)
                .fillColor(clourRad));
        circle2.setStrokeColor(clourRad);
        pin.add(circle);
        rad.add(circle2);
    }

}
