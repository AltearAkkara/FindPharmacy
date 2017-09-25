package com.atoz.akkaratanapat.findpharmacy.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.atoz.akkaratanapat.findpharmacy.Fragment.HorizontalPagerFragment;
import com.atoz.akkaratanapat.findpharmacy.Fragment.MapFragment;
import com.atoz.akkaratanapat.findpharmacy.Interface.OnCardClickListener;
import com.atoz.akkaratanapat.findpharmacy.Interface.OnDismissDialogListener;
import com.atoz.akkaratanapat.findpharmacy.Interface.OnMarkerClickListener;
import com.atoz.akkaratanapat.findpharmacy.Model.CardPharmacy;
import com.atoz.akkaratanapat.findpharmacy.Model.Pharmacy;
import com.atoz.akkaratanapat.findpharmacy.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements OnCardClickListener,OnMarkerClickListener,OnDismissDialogListener{

    public ArrayList<CardPharmacy> dataSet = new ArrayList<>();
    public int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initDataset();
        if (savedInstanceState == null) {
            changeFragment("map");
            changeFragment("card");
            //checkForPhoneStatePermission();
        }
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

        dataSet.add(new CardPharmacy(new Pharmacy("Pharmacy#1","Somewhere",sydney,"081234567","Someone"),0.7));
        dataSet.add(new CardPharmacy(new Pharmacy("Pharmacy#2","Somewhere2",sydney2,"081234567","Someone"),0.80));
        dataSet.add(new CardPharmacy(new Pharmacy("Pharmacy#3","Somewhere3",sydney3,"081234567","Someone"),0.90));
        dataSet.add(new CardPharmacy(new Pharmacy("Pharmacy#4","Somewhere4",sydney4,"081234567","Someone"),1));
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
        }

    }

    @Override
    public void onMarkerClick(String name) {

    }
}
