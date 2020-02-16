package com.example.diana.licentaschelet.Classes;


import java.io.Serializable;

public class Product implements Serializable{
    private String id;
    private  String name;
    private  String producer;


    public Product(){
        name="";
        producer="";
    }

    public Product(String id, String name, String producer) {
        this.id=id;
        this.name = name;
        this.producer = producer;

    }



    @Override
    public String toString() {
        return  name  + " " + producer + "\n";

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }



    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }



}
