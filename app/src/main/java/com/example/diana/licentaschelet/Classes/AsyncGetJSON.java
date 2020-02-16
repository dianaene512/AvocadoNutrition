package com.example.diana.licentaschelet.Classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class AsyncGetJSON extends AsyncTask<String, String, FridgeProduct> {

 private String jsonUrl;
 private String name;
 private String producer;
 private FridgeProduct fp;
 private ProgressDialog progressDialog;
 private Context context;

    public AsyncGetJSON(String jsonUrl,Context c) {
        this.jsonUrl = jsonUrl;
        this.context =c;
    }

    @Override
    protected void onPreExecute() {
        /*super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("LOADING");
        progressDialog.show();*/

    }

    @Override
    protected FridgeProduct doInBackground(String... arg0) {

        JSONHelper jParser = new JSONHelper();
        JSONObject json = jParser.getJSONFromUrl(jsonUrl);
       // Log.d("json",json.toString());

         name = "abc";
        producer ="abc";
        try {
            JSONObject jsonprod = json.getJSONObject("product");
            name = jsonprod.getString("product_name");
            producer = jsonprod.getString("brands");
            fp = new FridgeProduct();
            fp.setName(this.name);
            fp.setProducer(this.producer);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("obiect",name + " " + producer );


        return fp;
    }



}
