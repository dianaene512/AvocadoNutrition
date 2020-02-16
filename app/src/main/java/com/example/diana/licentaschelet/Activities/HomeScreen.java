package com.example.diana.licentaschelet.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import com.example.diana.licentaschelet.Activities.AccountRelated.Settings;
import com.example.diana.licentaschelet.Activities.ProductRelated.FavoriteProducts;
import com.example.diana.licentaschelet.Activities.ProductRelated.FoodToday;
import com.example.diana.licentaschelet.Activities.ProductRelated.MyFridge;
import com.example.diana.licentaschelet.Classes.DBHelper;
import com.example.diana.licentaschelet.Classes.TranslationUtils;
import com.example.diana.licentaschelet.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeScreen extends Activity {

  private GridLayout gl;
  private ProgressDialog progressDialog;
  private ArrayList<String> ingredientsListENG;
  private DBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        gl = findViewById(R.id.gvSectiuni);
        dbh = DBHelper.getInstance(getApplicationContext());
        ingredientsListENG = null;
        for(int i =0; i<gl.getChildCount();i++){

            CardView cont = (CardView) gl.getChildAt(i);
            final int finalI = i;
            cont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent();
                    switch(finalI){
                            case 0: {


                                intent = new Intent(getApplicationContext(),MyFridge.class);
                                break;
                            }

                            case 1: {
                                intent = new Intent(getApplicationContext(),FoodToday.class);
                                break;
                            }

                            case 2: {
                                 intent = new Intent(getApplicationContext(),FavoriteProducts.class);
                                break;
                            }

                            case 3: {


                                progressDialog = new ProgressDialog(HomeScreen.this);
                                progressDialog.setMessage(getString(R.string.translating));
                                progressDialog.show();
                                ingredientsListENG = getTranslatedIngredientsList();
                                progressDialog.dismiss();
                                 intent = new Intent(getApplicationContext(),Recipes.class);
                                 intent.putExtra("lista",ingredientsListENG);
                                break;
                            }

                            case 4: {
                                 intent = new Intent(getApplicationContext(),Statistics.class);
                                break;
                            }

                            case 5: {
                                 intent = new Intent(getApplicationContext(),Settings.class);
                                break;
                            }
                    }

                    startActivity(intent);
                }
            });
        }





    }

    public ArrayList<String> getIngredientsList(){

        ArrayList<String> lista=new ArrayList<>();
        Cursor c = dbh.getAllFridgeData();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    String nume = c.getString(c.getColumnIndex("NAME"));
                    lista.add(nume);

                } while (c.moveToNext());



            }


        }

        return lista;
    }

    public ArrayList<String> getTranslatedIngredientsList(){
        ArrayList<String> listaRO = getIngredientsList();
        ArrayList<JsonElement> listaJSON = new ArrayList<>();
        ArrayList<String> listaTradusa =new ArrayList<>();
        JsonElement jsel = null;
        for(String ingredient : listaRO){
            TranslationUtils tu = new TranslationUtils(ingredient);
            try {
                  jsel = tu.execute().get();

                  listaJSON.add(jsel);

                  JsonArray jsonArrayReturnat = jsel.getAsJsonArray();

                  JsonObject jsobj = (JsonObject) jsonArrayReturnat.get(0);

                  JsonArray jsArrayTrans = jsobj.getAsJsonArray("translations");
                  JsonObject jsobjTrans = (JsonObject) jsArrayTrans.get(0);
                  String ingredientTradus = jsobjTrans.get("text").getAsString();

                  listaTradusa.add(ingredientTradus);




            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return listaTradusa;


    }

}
