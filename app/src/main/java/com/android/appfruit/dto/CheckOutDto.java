package com.android.appfruit.dto;

public class CheckOutDto {
    private String shipName;
    private String shipAddress;
    private String shipPhone;
    private String shipNote;

    public CheckOutDto() {
    }

    public CheckOutDto(String shipName, String shipAddress, String shipPhone, String shipNote) {
        this.shipName = shipName;
        this.shipAddress = shipAddress;
        this.shipPhone = shipPhone;
        this.shipNote = shipNote;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipPhone() {
        return shipPhone;
    }

    public void setShipPhone(String shipPhone) {
        this.shipPhone = shipPhone;
    }

    public String getShipNote() {
        return shipNote;
    }

    public void setShipNote(String shipNote) {
        this.shipNote = shipNote;
    }
}
