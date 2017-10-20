package com.atoz.akkaratanapat.findpharmacy.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.atoz.akkaratanapat.daogenerator.DaoSession;
import com.atoz.akkaratanapat.daogenerator.Pharmacy;
import com.atoz.akkaratanapat.daogenerator.PharmacyDao;
import com.atoz.akkaratanapat.findpharmacy.APIHandle;
import com.atoz.akkaratanapat.findpharmacy.Dialog.InfoDialog;
import com.atoz.akkaratanapat.findpharmacy.Fragment.HorizontalPagerFragment;
import com.atoz.akkaratanapat.findpharmacy.Fragment.MapFragment;
import com.atoz.akkaratanapat.findpharmacy.GreenDaoApplication;
import com.atoz.akkaratanapat.findpharmacy.Interface.DialogListener;
import com.atoz.akkaratanapat.findpharmacy.Interface.OnCardClickListener;
import com.atoz.akkaratanapat.findpharmacy.Interface.OnMarkerClickListener;
import com.atoz.akkaratanapat.findpharmacy.Model.CardPharmacy;
import com.atoz.akkaratanapat.findpharmacy.Model.MyPharmacy;
import com.atoz.akkaratanapat.findpharmacy.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class HomeActivity extends AppCompatActivity implements OnCardClickListener, OnMarkerClickListener
        , DialogListener, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, APIHandle.ApiHandlerListener {

    public double myLat = 0, myLng = 0;
    public ArrayList<CardPharmacy> dataSet = new ArrayList<>();
    public int index = 0;
    private PharmacyDao pharmacyDao;
    private static final int REQUEST_PHONE_CALL = 1;
    private GoogleApiClient googleApiClient;
    private APIHandle apiHandle;
    Location currentLocation = new Location("current");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setActionbar();
        setGreenDAO(HomeActivity.this);
        //LatLng sydney =  new LatLng(-33.867834,151.207760);
        //addPharmacyDao(new MyPharmacy("Pharmacy#1","Somewhere",sydney,"081234567","Someone"));
        setGoogleApiClient();
        initPhone();
        //getLocationFromAddress(getApplicationContext(),"50/1 หมู่ที่ 10, ตำบลเหมืองแดง อำเภอแม่สาย จังหวัดเชียงราย, 73130");
        //loadPharmacyAll();
        apiHandle = new APIHandle(this);
        apiHandle.setApiHandlerListener(this);

        loadPharmacyAll();
        if (savedInstanceState == null) {
//            changeFragment("map");
//            changeFragment("card");
            //checkForPhoneStatePermission();
        }

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            } else if (address.size() == 0) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    private void setActionbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("\t\t" + "ค้นหาร้านยาคุณภาพ");
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    }

    private void initPhone() {
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void setGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    private void setGreenDAO(Context context) {
        GreenDaoApplication application = (GreenDaoApplication) context.getApplicationContext();
        DaoSession daoSession = application.getDaoSession();
        pharmacyDao = daoSession.getPharmacyDao();
    }

    public void loadPharmacyAll() {
        QueryBuilder<Pharmacy> userQueryBuilder = pharmacyDao.queryBuilder();
        userQueryBuilder.where(PharmacyDao.Properties.Lat.between(myLat - 0.025,myLat + 0.025)
                ,PharmacyDao.Properties.Lng.between(myLng - 0.075,myLng + 0.075));
//        userQueryBuilder.where(PharmacyDao.Properties.Lat.between(-90, 90)
//                , PharmacyDao.Properties.Lng.between(-180, 180));
        List<Pharmacy> pharmacies = userQueryBuilder.list();
        initDataset(pharmacies);
    }

    public void loadPharmacy(MyPharmacy myPharmacy) {
        //find dataSet
        //find db if id is not match add to dataset
        QueryBuilder<Pharmacy> userQueryBuilder = pharmacyDao.queryBuilder();
        userQueryBuilder.where(PharmacyDao.Properties.Name.eq(myPharmacy.getNamePharmacy())).
                or(PharmacyDao.Properties.Address.eq(myPharmacy.getAddress()),
                        userQueryBuilder.or(PharmacyDao.Properties.Number.eq(myPharmacy.getTelNumber()),
                                PharmacyDao.Properties.Owner.eq(myPharmacy.getOwnerName())));
        List<Pharmacy> pharmacies = userQueryBuilder.list();
        updateDataset(pharmacies);
    }

    public void addPharmacyDao(MyPharmacy myPharmacy) {
        Pharmacy pharmacy = new Pharmacy(null, myPharmacy.getNamePharmacy(),
                myPharmacy.getAddress(), myPharmacy.getLocation().latitude,
                myPharmacy.getLocation().longitude, myPharmacy.getTelNumber(), myPharmacy.getOwnerName());
        pharmacyDao.insert(pharmacy);
    }

    public void deletePharmaDao(long id) {
        pharmacyDao.deleteByKey(id);

    }

    public void changeFragment(String fragmentName) {
        if (fragmentName.equals("map")) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out)
                    .replace(R.id.container, MapFragment.newInstance(), fragmentName)
                    .addToBackStack(null)
                    .commit();
        }
        if (fragmentName.equals("card")) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out)
                    .replace(R.id.containerCard, HorizontalPagerFragment.newInstance(), fragmentName)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void initDataset(List<Pharmacy> pharmacies) {

        currentLocation.setLatitude(myLat);
        currentLocation.setLongitude(myLng);

        changeFragment("map");
        changeFragment("card");

        for (Pharmacy pharmacy : pharmacies) {
            long id = pharmacy.getId();
            String name = pharmacy.getName();
            String address = pharmacy.getAddress();
            String tel = pharmacy.getNumber();
            double lat = pharmacy.getLat();
            double lng = pharmacy.getLng();
            String owner = pharmacy.getOwner();
            LatLng location = new LatLng(lat, lng);
            Location phamaLocation = new Location("");
            phamaLocation.setLatitude(lat);
            phamaLocation.setLongitude(lng);

            dataSet.add(new CardPharmacy(id, new MyPharmacy(name, address, location,
                    tel, owner), currentLocation.distanceTo(phamaLocation) / 1000, 1));
        }

        apiHandle.requestNearbyPlace(myLat, myLng, 9500);


//        LatLng sydney = new LatLng(-33.867834, 151.207760);
//        LatLng sydney2 = new LatLng(-33.877749, 151.186506);
//        LatLng sydney3 = new LatLng(-33.865626, 151.193621);
//        LatLng sydney4 = new LatLng(-33.872987, 151.198806);

        //Toast.makeText(getApplicationContext(), "Found" + pharmacies.size() + "places", Toast.LENGTH_SHORT).show();
//        dataSet.add(new CardPharmacy(new MyPharmacy("Pharmacy#1","Somewhere",sydney,"081234567","Someone"),0.7,0));
//        dataSet.add(new CardPharmacy(new MyPharmacy("Pharmacy#2","Somewhere2",sydney2,"081234567","Someone"),0.80,0));
//        dataSet.add(new CardPharmacy(new MyPharmacy("Pharmacy#3","Somewhere3",sydney3,"081234567","Someone"),0.90,1));
//        dataSet.add(new CardPharmacy(new MyPharmacy("Pharmacy#4","Somewhere4",sydney4,"081234567","Someone"),1,1));


    }

    private void updateDataset(List<Pharmacy> pharmacies) {
        Location currentLocation = new Location("current");
        currentLocation.setLatitude(myLat);
        currentLocation.setLongitude(myLng);

        for (Pharmacy pharmacy : pharmacies) {
            long id = pharmacy.getId();
            String name = pharmacy.getName();
            String address = pharmacy.getAddress();
            String tel = pharmacy.getNumber();
            double lat = pharmacy.getLat();
            double lng = pharmacy.getLng();
            String owner = pharmacy.getOwner();
            LatLng location = new LatLng(lat, lng);
            Location phamaLocation = new Location("");
            phamaLocation.setLatitude(lat);
            phamaLocation.setLongitude(lng);

            FragmentManager fm = getSupportFragmentManager();
            MapFragment mapFragment = (MapFragment) fm.findFragmentByTag("map");
            HorizontalPagerFragment horizontalPagerFragment = (HorizontalPagerFragment) fm.findFragmentByTag("card");

            if (findIdInDataSet(id)) {
                dataSet.add(new CardPharmacy(id, new MyPharmacy(name, address, location,
                        tel, owner), currentLocation.distanceTo(phamaLocation), 1));

                if (mapFragment != null) {
                    mapFragment.pin(dataSet.size() - 1);
                }

                if (horizontalPagerFragment != null) {
                    horizontalPagerFragment.updatCard();
                }

            }
        }
        if (pharmacies.size() == 0) {
            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Found " + pharmacies.size() + "places", Toast.LENGTH_SHORT).show();
            //update
        }

    }

    public boolean findIdInDataSet(long id) {
        boolean result = true;
        for (int index = 0; index < dataSet.size(); index++) {
            if (dataSet.get(index).getId() == id) {
                result = false;
                Toast.makeText(getApplicationContext(),
                        "That pharmacy where you want to find already have in data set.", Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }

    @Override
    public void onSubmit(String name, MyPharmacy mypharmacy) {
        if (name.equals("Call")) {
            phoneCall(mypharmacy.getTelNumber());
        } else if (name.equals("Create")) {
            addPharmacyDao(mypharmacy);
        } else if (name.equals("Search")) {
            loadPharmacy(mypharmacy);
        }
    }

    @Override
    public void onCancel(String name) {

    }

    @Override
    public void onDismiss(String name) {

    }

    @Override
    public void onCardClick(int index) {
        if (this.index != index) {
            Toast.makeText(getApplicationContext(), index + "", Toast.LENGTH_SHORT).show();
            FragmentManager fm = getSupportFragmentManager();
            MapFragment mapFragment = (MapFragment) fm.findFragmentByTag("map");
            if (mapFragment != null) {
                mapFragment.zoom(dataSet.get(index).getPharmacy().getLocation(), index);
            }
            this.index = index;
        } else {
            Toast.makeText(getApplicationContext(), index + " dialog show up", Toast.LENGTH_SHORT).show();
            InfoDialog.show(HomeActivity.this, true, dataSet.get(index), this);
        }

    }

    @Override
    public void onMarkerClick(String name) {

    }

    public void phoneCall(String telNo) {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telNo));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                startActivity(intent);
            }
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (myLat == 0 | myLng == 0) {
            myLat = location.getLatitude();
            myLng = location.getLongitude();
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadPharmacyAll();
                }
            });
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
            return;
        }
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        if (locationAvailability.isLocationAvailable()) {
            // Call Location Services
            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(15000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            // Do something when Location Provider not available
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onSuccess(String name, final int index, JSONObject json) throws JSONException {
        JSONObject response = json;
        String status = response.getString("status");
        if (status.equals("OK")) {
            if (name.equals(APIHandle.APIName.Nearby.toString())) {
                JSONArray result = response.getJSONArray("results");
                for (int count = 0; count < result.length(); count++) {
                    JSONObject location = result.getJSONObject(count).getJSONObject("geometry").getJSONObject("location");
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");
                    String pharmacyName = result.getJSONObject(count).getString("name");
                    String placeID = result.getJSONObject(count).getString("place_id");
                    String address = result.getJSONObject(count).getString("vicinity");
                    Location phamaLocation = new Location("");
                    phamaLocation.setLatitude(lat);
                    phamaLocation.setLongitude(lng);
                    dataSet.add(new CardPharmacy(-1, new MyPharmacy(pharmacyName, address, new LatLng(lat, lng),
                            "", "Someone"), currentLocation.distanceTo(phamaLocation), 0));

                    apiHandle.requestPlaceDetail(count, placeID);
                }

            } else if (name.equals(APIHandle.APIName.Detail.toString())) {
                JSONObject result = response.getJSONObject("result");
                String tel = result.getString("international_phone_number");
                String[] tempNumber = tel.split(" ");
                dataSet.get(index).getPharmacy().setTelNumber("0" + tempNumber[1] + tempNumber[2] + tempNumber[3]);
                FragmentManager fm = getSupportFragmentManager();
                final HorizontalPagerFragment horizontalPagerFragment = (HorizontalPagerFragment) fm.findFragmentByTag("card");

                final MapFragment mapFragment = (MapFragment) fm.findFragmentByTag("map");

                if (mapFragment != null) {
                    HomeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mapFragment.pin(index);
                        }
                    });
                }

                if (horizontalPagerFragment != null) {
                    HomeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            horizontalPagerFragment.updatCard();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onBodyError(ResponseBody responseBodyError) {

    }

    @Override
    public void onBodyErrorIsNull() {

    }

    @Override
    public void onFailure(String name, String url, String param, Exception e) {

    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onFinishLoading() {

    }


    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }
}
