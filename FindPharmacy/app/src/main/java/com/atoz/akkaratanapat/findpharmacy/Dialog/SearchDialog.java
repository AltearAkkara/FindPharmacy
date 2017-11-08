package com.atoz.akkaratanapat.findpharmacy.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;

import com.atoz.akkaratanapat.findpharmacy.Interface.DialogListener;
import com.atoz.akkaratanapat.findpharmacy.Model.MyPharmacy;
import com.atoz.akkaratanapat.findpharmacy.R;
import com.cengalabs.flatui.views.FlatEditText;
import com.cengalabs.flatui.views.FlatTextView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by altear on 8/6/2017.
 */

public class SearchDialog {

    private static Dialog dialog;

    public static void show(Context context
            , boolean cancelable, final DialogListener listener) {

        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.search_dialog);
        dialog.setCancelable(cancelable);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(listener != null)
                    listener.onDismiss("search");
            }
        });

        FlatTextView okDialog = (FlatTextView) dialog.findViewById(R.id.okDialog);
        FlatTextView cancelDialog = (FlatTextView) dialog.findViewById(R.id.cancelDialog);

        final FlatEditText name = (FlatEditText)dialog.findViewById(R.id.pharmacyEditText);
        final FlatEditText address = (FlatEditText)dialog.findViewById(R.id.pharmacyAddressEditText);
        address.setVisibility(View.GONE);
        final FlatEditText province = (FlatEditText)dialog.findViewById(R.id.addressEditTextProvince);
        final FlatEditText district = (FlatEditText)dialog.findViewById(R.id.addressEditTextDistrict);
        final FlatEditText lat = (FlatEditText)dialog.findViewById(R.id.latEditText);
        lat.setVisibility(View.GONE);
        final FlatEditText lng = (FlatEditText)dialog.findViewById(R.id.lngEditText);
        lng.setVisibility(View.GONE);
        final FlatEditText tel = (FlatEditText)dialog.findViewById(R.id.telEditText);
        final FlatEditText owner = (FlatEditText)dialog.findViewById(R.id.ownerEditText);

//        dialog_title.getAttributes().setTheme(FlatUI.SNOW, context.getResources());
//        dialog_message.getAttributes().setTheme(FlatUI.DARK, context.getResources());

        okDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String nameInput = "",addressInput = "",telInput = "",ownerInput = "";

                listener.onSubmit("Search",new MyPharmacy(name.getText().toString(),"",
                        province.getText().toString(),district.getText().toString(),new LatLng(0,0),
                        tel.getText().toString(),owner.getText().toString()));
                dialog.dismiss();
            }
        });

        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
