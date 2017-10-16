package com.atoz.akkaratanapat.findpharmacy.Model;

/**
 * Created by aa on 9/23/2017.
 */

public class CardPharmacy {

    private long id;
    private MyPharmacy pharmacy;
    private double distance;
    private int type;

    public CardPharmacy(long id,MyPharmacy pharmacy, double distance,int type) {
        this.id = id;
        this.pharmacy = pharmacy;
        this.distance = distance;
        this.type = type;
    }

    public MyPharmacy getPharmacy() {

        return pharmacy;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return this.id;
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

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return this.type;
    }
}
