package com.example.receiptx;

public class ReceiptSummary {
    private Integer rID;
    private String rName;
    private String rStore;
    private String rDate;
    private String rCategory;
    private String rAddress;
    private Double rTotal;
    private String rImagePath;

    //creating the object
    public ReceiptSummary(Integer rID, String rName, String rStore, String rDate, String rCategory, String rAddress, Double rTotal, String rImagePath) {
        this.rID = rID;
        this.rName = rName;
        this.rStore = rStore;
        this.rDate = rDate;
        this.rCategory = rCategory;
        this.rAddress = rAddress;
        this.rTotal = rTotal;
        this.rImagePath = rImagePath;
    }

    public String getrImagePath() {
        return rImagePath;
    }

    public void setrImagePath(String rImagePath) {
        this.rImagePath = rImagePath;
    }

    public String getrCategory() {
        return rCategory;
    }

    public void setrCategory(String rCategory) {
        this.rCategory = rCategory;
    }

    public String getrAddress() {
        return rAddress;
    }

    public void setrAddress(String rAddress) {
        this.rAddress = rAddress;
    }

    //getter
    public Integer getrID() {
        return rID;
    }

    //setter
    public void setrID(Integer rID) {
        this.rID = rID;
    }

    //getter
    public String getrName() {
        return rName;
    }
    //setter
    public void setrName(String rName) {
        this.rName = rName;
    }

    //getter
    public String getrStore() {
        return rStore;
    }
    //setter
    public void setrStore(String rStore) {
        this.rStore = rStore;
    }

    //getter
    public String getrDate() {
        return rDate;
    }
    //setter
    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    //getter
    public Double getrTotal() {
        return rTotal;
    }
    //setter
    public void setrTotal(Double rTotal) {
        this.rTotal = rTotal;
    }
}
