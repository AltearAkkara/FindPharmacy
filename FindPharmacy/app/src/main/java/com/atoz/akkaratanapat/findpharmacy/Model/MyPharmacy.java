package com.atoz.akkaratanapat.findpharmacy.Model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by aa on 9/23/2017.
 */

public class MyPharmacy {

//    ร้าน วรวัตน์เภสัช
//    ที่อยู่	25 ราษฎร์สนาม เมืองอุตรดิตถ์
//    จังหวัด อุตรดิตถ์
//    โทรศัพท์	0 5544 1335
//    เภสัชกร	ภก.วรวัฒน์ ภู่ประเสริฐ

    private String namePharmacy;
    private String address;
    private String province;
    private String district;
    private LatLng location;
    private String telNumber;
    private String ownerName;

    public MyPharmacy(String namePharmacy, String address, String province, String district, LatLng location, String telNumber, String ownerName) {
        this.namePharmacy = namePharmacy;
        this.address = address;
        this.province = province;
        this.district = district;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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
