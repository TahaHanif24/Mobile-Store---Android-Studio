package com.example.javaproject;

import java.io.Serializable;

public class newProducts implements Serializable {
    String img_url;
    String  name,type;
    String description;
    int price;

    public newProducts(String img_url, String name, int price, String description) {
        this.img_url = img_url;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public newProducts() {

    }
}

