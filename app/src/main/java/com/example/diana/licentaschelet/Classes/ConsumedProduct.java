package com.example.diana.licentaschelet.Classes;



public class ConsumedProduct extends Product{
    private float kcal;
    private String dateConsumed;

    public ConsumedProduct(String id,String name, String producer, float kcal, String dateConsumed) {
        super(id,name, producer);
        this.kcal = kcal;
        this.dateConsumed=dateConsumed;
    }

    public ConsumedProduct(){
        this.setName("");
        this.setProducer("");
        this.setKcal(0);
        this.setDateConsumed("");
    }

    public String getDateConsumed(){
        return dateConsumed;
    }

    public void setDateConsumed(String dateConsumed){
        this.dateConsumed=dateConsumed;
    }

    public float getKcal() {
        return kcal;
    }

    public void setKcal(float kcal) {
        this.kcal = kcal;
    }

    @Override
    public String toString() {
        return super.toString() + "ConsumedProduct{" +
                "kcal=" + kcal +
                '}';
    }
}
