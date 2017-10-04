package com.atoz.akkaratanapat.findpharmacy.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;

import com.atoz.akkaratanapat.findpharmacy.Interface.DialogListener;
import com.atoz.akkaratanapat.findpharmacy.R;
import com.cengalabs.flatui.views.FlatTextView;

/**
 * Created by altear on 8/6/2017.
 */

public class InfoDialog {

    private static Dialog dialog;

    public static void show(final Context context
            , boolean cancelable,String name,String telNo, final DialogListener listener) {

        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.info_dialog);
        dialog.setCancelable(cancelable);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(listener != null)
                    listener.onDismiss("create");
            }
        });

        FlatTextView infoCallDialog = (FlatTextView) dialog.findViewById(R.id.infoCallDialog);
        infoCallDialog.setText("Name : "+ name + "\n Tel no : " + telNo);

        FlatTextView okDialog = (FlatTextView) dialog.findViewById(R.id.okCallDialog);
        okDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        FlatTextView cancelDialog = (FlatTextView) dialog.findViewById(R.id.cancelCallDialog);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
