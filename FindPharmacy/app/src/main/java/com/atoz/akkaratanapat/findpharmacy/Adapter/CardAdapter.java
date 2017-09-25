package com.atoz.akkaratanapat.findpharmacy.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atoz.akkaratanapat.findpharmacy.Interface.OnCardClickListener;
import com.atoz.akkaratanapat.findpharmacy.Model.CardPharmacy;
import com.atoz.akkaratanapat.findpharmacy.R;

import java.util.ArrayList;

/**
 * Created by aa on 9/23/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ArrayList<CardPharmacy> dataSet;
    private OnCardClickListener onCardClickListener;

    public CardAdapter(ArrayList<CardPharmacy> dataSet, OnCardClickListener onCardClickListener) {
        this.dataSet = dataSet;
        this.onCardClickListener = onCardClickListener;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textName.setTag(position);
        holder.textName.setText(dataSet.get(position).getPharmacy().getNamePharmacy());
        holder.textAddress.setText(dataSet.get(position).getPharmacy().getAddress());
        holder.textDistance.setText(dataSet.get(position).getDistance() + " KM.");

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName,textAddress,textDistance;

        ViewHolder(final View itemView) {
            super(itemView);

            textName = (TextView)itemView.findViewById(R.id.name);
            textAddress = (TextView)itemView.findViewById(R.id.address);
            textDistance = (TextView) itemView.findViewById(R.id.distance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCardClickListener.onCardClick((int)textName.getTag());
                }
            });
        }
    }
}
