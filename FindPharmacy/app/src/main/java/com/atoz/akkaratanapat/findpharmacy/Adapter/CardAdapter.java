package com.atoz.akkaratanapat.findpharmacy.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
    private Context context;
    //private SparseBooleanArray selectedItems;

    public CardAdapter(ArrayList<CardPharmacy> dataSet, OnCardClickListener onCardClickListener, Context context) {
        this.dataSet = dataSet;
        this.onCardClickListener = onCardClickListener;
        this.context = context;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //holder.header.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        holder.textName.setTag(position);
        if(dataSet.get(position).getType() == 1){
            holder.textName.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryPin));
            holder.textAddress.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryPin));
            holder.textDistance.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryPin));
        }
        holder.textName.setText(dataSet.get(position).getPharmacy().getNamePharmacy());
        holder.textAddress.setText(dataSet.get(position).getPharmacy().getAddress());
        holder.textDistance.setText(dataSet.get(position).getDistance() + " KM.");

        holder.myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataSet.get(position).getType() == 0){
                    holder.header.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryPin));
                    holder.textName.setTextColor(ContextCompat.getColor(context, R.color.white));
                }else{
                    holder.header.setBackgroundColor(ContextCompat.getColor(context, R.color.colorSecondaryPin));
                    holder.textName.setTextColor(ContextCompat.getColor(context, R.color.white));
                }


                onCardClickListener.onCardClick(position);
            }
        });
        // holder.itemView.setSelected(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout header;
        CardView myCardView;
        TextView textName, textAddress, textDistance;

        ViewHolder(final View itemView) {
            super(itemView);
            myCardView = (CardView) itemView.findViewById(R.id.myCard);
            header = (RelativeLayout) itemView.findViewById(R.id.header);
            textName = (TextView) itemView.findViewById(R.id.name);
            textAddress = (TextView) itemView.findViewById(R.id.address);
            textDistance = (TextView) itemView.findViewById(R.id.distance);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    header.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryPin));
//                    textName.setTextColor(ContextCompat.getColor(context, R.color.white));
//                    onCardClickListener.onCardClick((int)textName.getTag());
//                }
//            });
        }

    }
}
