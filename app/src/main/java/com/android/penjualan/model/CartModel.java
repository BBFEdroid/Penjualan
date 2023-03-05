package com.android.penjualan.model;

import android.graphics.drawable.Drawable;

public class CartModel {
    int qty, cartPrice;
    String cartName, cartUnit;
    Drawable cartImg;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(int cartPrice) {
        this.cartPrice = cartPrice;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public String getCartUnit() {
        return cartUnit;
    }

    public void setCartUnit(String cartUnit) {
        this.cartUnit = cartUnit;
    }

    public Drawable getCartImg() {
        return cartImg;
    }

    public void setCartImg(Drawable cartImg) {
        this.cartImg = cartImg;
    }
}
