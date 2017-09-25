package com.atoz.akkaratanapat.findpharmacy.Model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by aa on 9/23/2017.
 */

public class Pharmacy {

//    ร้าน วรวัตน์เภสัช
//    ที่อยู่	25 ราษฎร์สนาม เมืองอุตรดิตถ์
//    จังหวัด อุตรดิตถ์
//    โทรศัพท์	0 5544 1335
//    เภสัชกร	ภก.วรวัฒน์ ภู่ประเสริฐ

    private String namePharmacy;
    private String address;
    private LatLng location;
    private String telNumber;
    private String ownerName;

    public Pharmacy(String namePharmacy, String address, LatLng location, String telNumber, String ownerName) {
        this.namePharmacy = namePharmacy;
        this.address = address;
        this.location = location;
        this.telNumber = telNumber;
        this.ownerName = ownerName;
    }

    public String getNamePharmacy() {
        return namePharmacy;
    }

    public void setNamePharmacy(String namePharmacy) {
        this.namePharmacy = namePharmacy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
