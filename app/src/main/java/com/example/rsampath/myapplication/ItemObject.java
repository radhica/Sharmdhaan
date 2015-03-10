package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/5/15.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class ItemObject implements Parcelable {
    private static final long serialVersionUID = -5435670920302756945L;

    private String name = "";
    private long quantity = 0;
    private double value;
    private int id;
    private boolean selected;

    public ItemObject() {

    }

    public ItemObject(Parcel dataParcel){
        id = dataParcel.readInt();
        name = dataParcel.readString();

    }

    public ItemObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ItemObject(boolean selected, String name) {
        this.selected = selected;
        this.name = name;
    }

    public ItemObject(String name, int id, boolean selected) {
        this.name = name;
        this.id = id;
        this.selected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemObject(String name, long quantity, double value) {
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


    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public double increment(){
        return ++quantity;
    }

    public double decrement(){
        return  --quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(id);
        parcel.writeValue(name);
    }

    public static final Parcelable.Creator<ItemObject> CREATOR
            = new Parcelable.Creator<ItemObject>() {
        public ItemObject createFromParcel(Parcel in) {
            return new ItemObject(in);
        }

        public ItemObject[] newArray(int size) {
            return new ItemObject[size];
        }
    };

}