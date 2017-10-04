package com.atoz.akkaratanapat.findpharmacy.Model;

/**
 * Created by aa on 9/23/2017.
 */

public class CardPharmacy {

    private MyPharmacy pharmacy;

    public CardPharmacy(MyPharmacy pharmacy, double distance) {
        this.pharmacy = pharmacy;
        this.distance = distance;
    }

    public MyPharmacy getPharmacy() {

        return pharmacy;
    }

    public void setPharmacy(MyPharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    private double distance;
}
