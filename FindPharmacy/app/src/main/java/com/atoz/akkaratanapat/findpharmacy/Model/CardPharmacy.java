package com.atoz.akkaratanapat.findpharmacy.Model;

/**
 * Created by aa on 9/23/2017.
 */

public class CardPharmacy {

    private Pharmacy pharmacy;

    public CardPharmacy(Pharmacy pharmacy, double distance) {
        this.pharmacy = pharmacy;
        this.distance = distance;
    }

    public Pharmacy getPharmacy() {

        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
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
