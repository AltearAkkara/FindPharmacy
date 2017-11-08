package com.atoz.akkaratanapat.findpharmacy.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.atoz.akkaratanapat.findpharmacy.Activity.HomeActivity;
import com.atoz.akkaratanapat.findpharmacy.Adapter.CardAdapter;
import com.atoz.akkaratanapat.findpharmacy.Dialog.CreateDialog;
import com.atoz.akkaratanapat.findpharmacy.Interface.DialogListener;
import com.atoz.akkaratanapat.findpharmacy.Dialog.SearchDialog;
import com.atoz.akkaratanapat.findpharmacy.Model.MyPharmacy;
import com.atoz.akkaratanapat.findpharmacy.R;
import com.getbase.floatingactionbutton.FloatingActionButton;


/**
 * Created by Altear on 8/18/16.
 */
public class HorizontalPagerFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton floatingActionButtonSearch,floatingActionButtonCreate;
    private HomeActivity homeActivity;

    public static HorizontalPagerFragment newInstance() {
        return new HorizontalPagerFragment();
    }

    public HorizontalPagerFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_horizontal, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeActivity = (HomeActivity) getActivity();
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setComponent(view);

    }

    private void setComponent(View view){
        floatingActionButtonSearch = (FloatingActionButton)view.findViewById(R.id.actionSearch);
        floatingActionButtonCreate = (FloatingActionButton)view.findViewById(R.id.actionAdd);
        floatingActionButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDialog.show(getContext(),true,homeActivity);
            }
        });
        floatingActionButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog.show(getContext(),true,homeActivity);
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CardAdapter(homeActivity.dataSet,homeActivity,getContext());
        recyclerView.setAdapter(adapter);

    }

    public void updatCard(){
        adapter.notifyDataSetChanged();
    }
}
