package com.atoz.akkaratanapat.findpharmacy.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.atoz.akkaratanapat.daogenerator.DaoSession;
import com.atoz.akkaratanapat.daogenerator.Pharmacy;
import com.atoz.akkaratanapat.daogenerator.PharmacyDao;
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
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnCardClickListener,OnMarkerClickListener
        ,DialogListener{

    public ArrayList<CardPharmacy> dataSet = new ArrayList<>();
    public int index = 0;
    private PharmacyDao pharmacyDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initDataset();
        setGreenDAO(HomeActivity.this);
        if (savedInstanceState == null) {
            changeFragment("map");
            changeFragment("card");
            //checkForPhoneStatePermission();
        }

    }

    private void setGreenDAO(Context context) {
        GreenDaoApplication application = (GreenDaoApplication) context.getApplicationContext();
        DaoSession daoSession = application.getDaoSession();
        pharmacyDao = daoSession.getPharmacyDao();
    }

    public void loadPharmacyAll(){
        List<Pharmacy> pharmacies = pharmacyDao.loadAll();
        for (Pharmacy pharmacy : pharmacies) {
            long id = pharmacy.getId();
            String name = pharmacy.getName();
            String address = pharmacy.getAddress();
            String tel = pharmacy.getNumber();
            double lat = pharmacy.getLat();
            double lng = pharmacy.getLng();
            String owner = pharmacy.getOwner();
        }
    }

    public void addPharmacyDao(String name, String address, Double lat, Double lng, String number, String owner) {
        Pharmacy pharmacy = new Pharmacy(null,name,address,lat,lng,number,owner);
        pharmacyDao.insert(pharmacy);
    }

    public void deletePharmaDao(long id){
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

    private void initDataset(){
        LatLng sydney =  new LatLng(-33.867834,151.207760);
        LatLng sydney2 =  new LatLng(-33.877749,151.186506);
        LatLng sydney3 =  new LatLng(-33.865626,151.193621);
        LatLng sydney4 =  new LatLng(-33.872987,151.198806);

        dataSet.add(new CardPharmacy(new MyPharmacy("Pharmacy#1","Somewhere",sydney,"081234567","Someone"),0.7));
        dataSet.add(new CardPharmacy(new MyPharmacy("Pharmacy#2","Somewhere2",sydney2,"081234567","Someone"),0.80));
        dataSet.add(new CardPharmacy(new MyPharmacy("Pharmacy#3","Somewhere3",sydney3,"081234567","Someone"),0.90));
        dataSet.add(new CardPharmacy(new MyPharmacy("Pharmacy#4","Somewhere4",sydney4,"081234567","Someone"),1));
    }


    @Override
    public void onSubmit(String name, MyPharmacy mypharmacy) {

    }

    @Override
    public void onCancel(String name) {

    }

    @Override
    public void onDismiss(String name) {

    }

    @Override
    public void onCardClick(int index) {
        if(this.index != index){
            Toast.makeText(getApplicationContext(),index+"",Toast.LENGTH_SHORT).show();
            FragmentManager fm = getSupportFragmentManager();
            MapFragment fragment = (MapFragment) fm.findFragmentByTag("map");
            fragment.zoom(dataSet.get(index).getPharmacy().getLocation(),index);
        } else{
            Toast.makeText(getApplicationContext(),index+" dialog show up",Toast.LENGTH_SHORT).show();
            InfoDialog.show(HomeActivity.this,true,dataSet.get(index).getPharmacy().getNamePharmacy(),
                    dataSet.get(index).getPharmacy().getTelNumber(),this);
        }

    }

    @Override
    public void onMarkerClick(String name) {

    }
}
