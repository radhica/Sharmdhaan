package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/5/15.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class AtomPayment implements Serializable {
    private static final long serialVersionUID = -5435670920302756945L;

    private String name = "";
    private double quantity = 0;
    private double value;

    public AtomPayment(String name, double quantity, double value) {
        this.setName(name);
        this.setQuantity(quantity);
        this.setValue(value);
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public double increment(){
        return ++quantity;
    }

    public double decrement(){
        return  --quantity;
    }

}