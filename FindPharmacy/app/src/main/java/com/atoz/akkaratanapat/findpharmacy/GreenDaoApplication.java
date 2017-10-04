package com.atoz.akkaratanapat.findpharmacy;

/**
 * Created by altear on 8/16/2017.
 */

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.atoz.akkaratanapat.daogenerator.DaoMaster;
import com.atoz.akkaratanapat.daogenerator.DaoSession;


//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;

public class GreenDaoApplication extends Application {

    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        setupDatabase();
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper =
                new DaoMaster.DevOpenHelper(this, "Pharmacy.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}