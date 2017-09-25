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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
        activity.index = index;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                activity.dataSet.get(0).getPharmacy().getLocation(), 15));

        for (int count = 0; count < activity.dataSet.size(); count++) {


            Circle circle = mMap.addCircle(new CircleOptions().center(activity.dataSet.get(count)
                    .getPharmacy().getLocation()).radius(20)
                    .fillColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryPin)));
            circle.setStrokeColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryPin));
            ;
            Circle circle2 = mMap.addCircle(new CircleOptions().center(activity.dataSet.get(count)
                    .getPharmacy().getLocation()).radius(100)
                    .fillColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryRad)));
            circle2.setStrokeColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryRad));
            pin.add(circle);
            rad.add(circle2);

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


}
