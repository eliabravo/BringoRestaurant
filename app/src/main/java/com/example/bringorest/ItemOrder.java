package com.example.bringorest;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ItemOrder {
    private String mCustomerInfo;
    private String mOrderInfo;
    private String mCustomerName;
    private String mCustomerAddress;
    private String mCustomerZip;
    private String mCustomerPhone;
    private String mOrderNotes;
    private String mOrderTime;
    OrdersFragment.OrderStatus mOrderStatus;

    public ItemOrder (OrdersFragment.OrderStatus status, String customerInfo, String orderInfo, String orderTime, String customerName, String customerAddress, String customerZip, String customerPhone, String orderNotes){

        mOrderStatus = status;
        mCustomerInfo = customerInfo;
        mOrderInfo = orderInfo;
        mOrderTime = orderTime;
        mCustomerPhone = customerPhone;
        mCustomerAddress = customerAddress;
        mCustomerName = customerName;
        mCustomerZip = customerZip;
        mOrderNotes = orderNotes;
    }

    //setter methods

    public void setmCustomerAddress(String mCustomerAddress) {
        this.mCustomerAddress = mCustomerAddress;
    }

    public String getmCustomerInfo() {
        return mCustomerInfo;
    }

    public void setmCustomerInfo(String mCustomerInfo) {
        this.mCustomerInfo = mCustomerInfo;
    }

    public String getmOrderInfo() {
        return mOrderInfo;
    }

    public void setmOrderInfo(String mOrderInfo) {
        this.mOrderInfo = mOrderInfo;
    }

    public String getmCustomerName() {
        return mCustomerName;
    }

    public void setmCustomerName(String mCustomerName) {
        this.mCustomerName = mCustomerName;
    }

    public String getmCustomerAddress() {
        return mCustomerAddress;
    }

    public String getmCustomerZip() {
        return mCustomerZip;
    }

    public void setmCustomerZip(String mCustomerZip) {
        this.mCustomerZip = mCustomerZip;
    }

    public String getmCustomerPhone() {
        return mCustomerPhone;
    }

    public void setmCustomerPhone(String mCustomerPhone) {
        this.mCustomerPhone = mCustomerPhone;
    }

    public String getmOrderNotes() {
        return mOrderNotes;
    }

    public void setmOrderNotes(String mOrderNotes) {
        this.mOrderNotes = mOrderNotes;
    }

    public String getmOrderTime() {
        return mOrderTime;
    }

    public void setmOrderTime(String mOrderTime) {
        this.mOrderTime = mOrderTime;
    }

    public OrdersFragment.OrderStatus getmOrderStatus() {
        return mOrderStatus;
    }

    public void setmOrderStatus(OrdersFragment.OrderStatus mOrderStatus) {
        this.mOrderStatus = mOrderStatus;
    }


}

