package com.atoz.akkaratanapat.findpharmacy.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HomeActivity activity;
    public ArrayList<Circle> pin = new ArrayList<>(), rad = new ArrayList<>();
    public Polyline polyline;


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
        rad.get(activity.index).setRadius(1000);
        rad.get(index).setRadius(3000);
        direction(new LatLng(activity.myLat, activity.myLng), latLng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(activity.dataSet.get(index)
                .getPharmacy().getLocation(), 12));
    }

    public void direction(LatLng origin, LatLng destination) {
        GoogleDirection.withServerKey(activity.serverKey)
                .from(origin)
                .to(destination)
                .unit(Unit.METRIC)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        // Do something here
                        if (direction.isOK()) {
                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();

                            route(DirectionConverter.createPolyline(getContext(), directionPositionList, 5, Color.RED));

                            System.out.print("direction ok " + direction.getStatus());
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something here
                        System.out.print("direction fail" + t.toString());
                    }
                });
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
        } else {
            mMap.setMyLocationEnabled(true);
        }
        //mMap.setMyLocationEnabled(true);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(activity.myLat, activity.myLng), 13));
//        mMap.addCircle(new CircleOptions().center(new LatLng(13.766,100.605)).radius(10000)
//                .fillColor(ContextCompat.getColor(getContext(), R.color.colorSecondaryRad)));


//        for (int count = 0; count < activity.dataSet.size(); count++) {
//            pin(count);
//        }
//        if(rad.size()>0){
//            rad.get(activity.index).setRadius(750);
//        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                activity.onMarkerClick("");
                return false;
            }
        });

    }

    public void route(PolylineOptions polylineOptions) {
        if(polyline != null){
            polyline.remove();
        }
        polyline = mMap.addPolyline(polylineOptions);
    }

    public void pin(int count) {
        int colour, colourRad;
        if (activity.dataSet.get(count).getType() == 0) {
            colour = ContextCompat.getColor(getContext(), R.color.colorPrimaryPin);
            colourRad = ContextCompat.getColor(getContext(), R.color.colorPrimaryRad);
        } else {
            colour = ContextCompat.getColor(getContext(), R.color.colorSecondaryPin);
            colourRad = ContextCompat.getColor(getContext(), R.color.colorSecondaryRad);
        }
        Circle circle = mMap.addCircle(new CircleOptions().center(activity.dataSet.get(count)
                .getPharmacy().getLocation()).radius(150)
                .fillColor(colour));
        circle.setStrokeColor(colour);

        Circle circle2 = mMap.addCircle(new CircleOptions().center(activity.dataSet.get(count)
                .getPharmacy().getLocation()).radius(1000)
                .fillColor(colourRad));
        circle2.setStrokeColor(colourRad);

        while (pin.size() < activity.dataSet.size()) {
            rad.add(null);
            pin.add(null);
        }
        pin.set(count, circle);
        rad.set(count, circle2);
    }

}
