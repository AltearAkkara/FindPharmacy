package com.atoz.akkaratanapat.findpharmacy.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;

import com.atoz.akkaratanapat.findpharmacy.Interface.DialogListener;
import com.atoz.akkaratanapat.findpharmacy.Model.CardPharmacy;
import com.atoz.akkaratanapat.findpharmacy.Model.MyPharmacy;
import com.atoz.akkaratanapat.findpharmacy.R;
import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatTextView;

/**
 * Created by altear on 8/6/2017.
 */

public class InfoDialog {

    private static Dialog dialog;

    public static void show(final Context context
            , boolean cancelable, CardPharmacy cardPharmacy, final DialogListener listener) {

        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.info_dialog);
        dialog.setCancelable(cancelable);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(listener != null)
                    listener.onDismiss("info");
            }
        });

        int type = cardPharmacy.getType();

        final MyPharmacy myPharmacy = cardPharmacy.getPharmacy();
        FlatTextView infoCallDialog = (FlatTextView) dialog.findViewById(R.id.infoCallDialog);
        FlatTextView messageCallDialog = (FlatTextView) dialog.findViewById(R.id.messageCallDialog);
        FlatTextView okDialog = (FlatTextView) dialog.findViewById(R.id.okCallDialog);
        FlatTextView cancelDialog = (FlatTextView) dialog.findViewById(R.id.cancelCallDialog);


        if(type == 1){
            dialog.findViewById(R.id.titleBackground).setBackgroundColor(ContextCompat.getColor(context, R.color.colorSecondaryPin));
            infoCallDialog.getAttributes().setTheme(FlatUI.SEA,context.getResources());
            messageCallDialog.getAttributes().setTheme(FlatUI.SEA,context.getResources());
            okDialog.getAttributes().setTheme(FlatUI.SEA,context.getResources());
            cancelDialog.getAttributes().setTheme(FlatUI.SEA,context.getResources());
        }

        infoCallDialog.setText("Name : "+ myPharmacy.getNamePharmacy() + "\n Tel no : "
                + myPharmacy.getTelNumber());

        okDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubmit("Call",myPharmacy);
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
