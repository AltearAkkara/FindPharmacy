package com.atoz.akkaratanapat.findpharmacy.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atoz.akkaratanapat.findpharmacy.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    public static BlankFragment newInstance(){
        return new BlankFragment();
    }

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}
