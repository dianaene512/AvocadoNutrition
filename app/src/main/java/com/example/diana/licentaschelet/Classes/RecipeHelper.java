package com.example.diana.licentaschelet.Classes;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class RecipeHelper extends AsyncTask<String,String,JsonElement> {


 private  static String host = "http://www.recipepuppy.com/api/?i=";
 private   String ingredientsString;


    public RecipeHelper(String ingredientsString) {
        this.ingredientsString = ingredientsString.replaceAll("\\s","");
    }

    @Override
    protected JsonElement doInBackground(String... strings) {

        InputStream is = null;
        JsonElement jsel = null;
        String urlString = this.host + ingredientsString;
        Log.d("URLUL",urlString);
        URL url = null;
        JsonElement jso = null;
        JsonParser jsonParser = new JsonParser();



            try {
                url = new URL(urlString);


                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlString);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();


                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                jso = jsonParser.parse(sb.toString().trim());


                // JsonReader jsonReader = new JsonReader(reader);
                // jsonReader.setLenient(true);
                //StringBuilder sb = new StringBuilder();
                // String line = null;
      /*      while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            jso = jsonParser.parse(sb.toString());*/
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                jso=null;
                Log.e("jsh", "couldn t fetch data " + e.toString());
            }


        return jso;




    }



}
