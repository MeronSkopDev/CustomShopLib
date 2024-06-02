package com.example.customshop.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class PurchaseDetails implements Serializable {
    public final String title;
    public final double price;
    public final String description;

    public PurchaseDetails(String title, double price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
    }
}
