package com.example.bringorest;

public class ItemMenu {
    private int mImageRes;
    private String mName;
    private String mDesc;
    private String mQty;
    private String mImageStr;
    private String mPrice;

    public ItemMenu(int imageRes, String name, String desc, String qty, String imageStr, String price){
        mImageRes = imageRes;
        mName = name;
        mDesc = desc;
        mQty = qty;
        mImageStr = imageStr;
        mPrice = price;
    }

    public void changeImageString (String text) {
        mImageStr = text;
    }

    public void changeName(String text) {
        mName = text;
    }

    public void changeDesc(String text) {
        mDesc = text;
    }

    public void changeQty(String text) {
        mQty = text;
    }

    public void changePrice(String text) {mPrice = text;}

    public int getImageRes (){
        return mImageRes;
    }

    public String getName () {
        return mName;
    }

    public String getDesc () {
        return mDesc;
    }

    public String getQty () {
        return mQty;
    }

    public String getPrice() {return mPrice; }

    public String getImageString(){
        return mImageStr;
    }
}

