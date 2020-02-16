package com.example.diana.licentaschelet.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.diana.licentaschelet.Classes.RecipeHelper;
import com.example.diana.licentaschelet.Classes.RecipesListviewAdapter;
import com.example.diana.licentaschelet.Classes.Reteta;
import com.example.diana.licentaschelet.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Recipes extends Activity {

    private ListView lvRecipes;
    private ArrayList<String> ingredientsList;
    private String ingredientsString;
    private JsonElement jsel;
    private ArrayList<Reteta> listaRetete;
    private RecipesListviewAdapter adapter;
    private TextView tvRecipeNotFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        ingredientsList=new ArrayList<>();
        ingredientsString="";
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ingredientsList = bundle.getStringArrayList("lista");
        lvRecipes = findViewById(R.id.lvRecipes);
        listaRetete = new ArrayList<>();
        tvRecipeNotFound = findViewById(R.id.tvRecipeNotFound);

        for(String s : ingredientsList){
            ingredientsString = ingredientsString + ","+ s;
        }

        Log.d("ingredientsString",ingredientsString);
        ingredientsString.replaceAll("\\s","");

        RecipeHelper rh = new RecipeHelper(ingredientsString);
        try {
             jsel = rh.execute().get();
           // Log.d("retete",jsel.toString());
            if(jsel!=null){
                populateRecipesList(jsel);

            }
            else{
                tvRecipeNotFound.setText(R.string.noRecepiesFound);
            }


           // Log.d("NRRETETE",String.valueOf(this.listaRetete.size()));
        } catch (InterruptedException | ExecutionException e) {
           // e.printStackTrace();
            Log.d("retete","nu s au gasit");
        }

        adapter=new RecipesListviewAdapter(this,listaRetete);
        lvRecipes.setAdapter(adapter);

    }


    public void populateRecipesList(JsonElement jsonElement){

        // din json elementul(obj?) cu toate retetele
        // -> arraylist cu fiecare reteta ca jsonelement/array
        // ->din jsonelement/array/obj cu o reteta in obiect reteta
        // sa adaug in lista de retete

        JsonObject jsoReturnat = null;
        try {
            jsoReturnat = jsel.getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();

        }
        JsonArray jsArrayRetete = jsoReturnat.getAsJsonArray("results");

        String jsArrayReteteString = jsArrayRetete.toString();

       // Log.d("retete json",jsArrayReteteString);

        for (JsonElement o : jsArrayRetete){
            JsonObject oo = o.getAsJsonObject();
            String title="";
            String recipeLink="";
            ArrayList<String> ingredients = new ArrayList<>();
            String imageLink="";

            title = oo.get("title").getAsString().trim();
            recipeLink = oo.get("href").getAsString().trim();
            imageLink = oo.get("thumbnail").getAsString().trim();
            String ingredienteString = oo.get("ingredients").getAsString();
            String[] ingredienteStringArray = ingredienteString.split(",");
            for(String s : ingredienteStringArray){
                ingredients.add(s.trim());
            }
            Reteta r = new Reteta(title,recipeLink,ingredients,imageLink);

            Log.d("RETETA",r.toString());

            this.listaRetete.add(r);

        }


    }

}
