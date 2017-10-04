package com.atoz.akkaratanapat.findpharmacy.Interface;

import com.atoz.akkaratanapat.findpharmacy.Model.MyPharmacy;

/**
 * Created by aa on 9/30/2017.
 */

public interface DialogListener {
    void onSubmit(String name, MyPharmacy pharmacy);
    void onCancel(String name);
    void onDismiss(String name);

}

