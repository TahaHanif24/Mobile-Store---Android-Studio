package com.example.javaproject;

public class Mycart {
    private String productName;
    private int productPrice;
    private int totalPrice;
    private int totalQuantity;
    private String currentDate;
    private String currentTime;

    public Mycart() {

    }

    public Mycart(String productName, int productPrice, int totalPrice, int totalQuantity, String currentDate, String currentTime, String documentId) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.documentId = documentId;
    }

    private String documentId; // Add this field to hold the document ID

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}