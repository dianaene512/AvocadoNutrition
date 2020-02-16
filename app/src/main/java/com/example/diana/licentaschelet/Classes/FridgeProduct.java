package com.example.diana.licentaschelet.Classes;


public class FridgeProduct extends Product{


    private float calories;
    private  String description;
    private  String expiryDate;
    private float price;

    public FridgeProduct(){
        calories=0;
        description="";
        expiryDate="";
        price=0;
    }

    public FridgeProduct(String id, String name, String producer, float calories, String description, String expiryDate, float price) {
        super(id, name, producer);
        this.calories = calories;
        this.description = description;
        this.expiryDate = expiryDate;
        this.price=price;

    }

    public FridgeProduct(String id, String name, String producer, float calories, String description) {
        super(id, name, producer);
        this.calories = calories;
        this.description = description;


    }

    @Override
    public String toString() {
        if(this.expiryDate!=null){
            return super.toString() + "Exp.: " + expiryDate ;
        }
        else return super.toString();
    }



    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


}
