package com.atoz.akkaratanapat.findpharmacy.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.atoz.akkaratanapat.findpharmacy.Interface.DialogListener;
import com.atoz.akkaratanapat.findpharmacy.Model.MyPharmacy;
import com.atoz.akkaratanapat.findpharmacy.R;
import com.cengalabs.flatui.views.FlatEditText;
import com.cengalabs.flatui.views.FlatTextView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by altear on 8/6/2017.
 */

public class CreateDialog {

    private static Dialog dialog;

    public static void show(final Context context
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
                    listener.onDismiss("create");
            }
        });

        FlatTextView messageDialogTitle = (FlatTextView) dialog.findViewById(R.id.messageDialogTitle);
        FlatTextView messageDialogMessage = (FlatTextView)dialog.findViewById(R.id.messageDialogMessage);
        FlatTextView okDialog = (FlatTextView) dialog.findViewById(R.id.okDialog);
        FlatTextView cancelDialog = (FlatTextView) dialog.findViewById(R.id.cancelDialog);

        final FlatEditText name = (FlatEditText)dialog.findViewById(R.id.pharmacyEditText);
        final FlatEditText address = (FlatEditText)dialog.findViewById(R.id.addressEditText);
        final FlatEditText lat = (FlatEditText)dialog.findViewById(R.id.latEditText);
        final FlatEditText lng = (FlatEditText)dialog.findViewById(R.id.lngEditText);
        final FlatEditText tel = (FlatEditText)dialog.findViewById(R.id.telEditText);
        final FlatEditText owner = (FlatEditText)dialog.findViewById(R.id.ownerEditText);

        messageDialogTitle.setText("Create");
        messageDialogMessage.setText("Please fill all parameter.");
//        dialog_title.getAttributes().setTheme(FlatUI.SNOW, context.getResources());
//        dialog_message.getAttributes().setTheme(FlatUI.DARK, context.getResources());

        okDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().length() == 0 | address.getText().toString().length() == 0|
                        lat.getText().toString().length() == 0|lng.getText().toString().length() == 0|
                        tel.getText().toString().length() == 0|owner.getText().toString().length() == 0){
                    Toast.makeText(context,"Please fill all parameter",Toast.LENGTH_SHORT).show();
                }
                else{
                    listener.onSubmit("Create",new MyPharmacy(name.getText().toString(),
                            address.getText().toString(),
                            new LatLng(Double.parseDouble(lat.getText().toString()),
                                    Double.parseDouble(lng.getText().toString())),
                            tel.getText().toString(),owner.getText().toString()));
                    dialog.dismiss();
                }
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
