package com.example.diana.licentaschelet.Classes;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class TranslationUtils extends AsyncTask<String, Void, JsonElement> {

    static String subscriptionKey = "****";

    static String host = "https://api.cognitive.microsofttranslator.com";
    static String path = "/translate?api-version=3.0";
    static String params = "&to=en";

    private String text;

    public TranslationUtils(String text) {
        this.text=text;
    }

    @Override
    protected JsonElement doInBackground(String... strings) {


        JsonElement jsel = null;
        try {

            String response = this.Translate();
            jsel = this.prettify(response);

            // Log.d("translation","lalala" +jsel);
        } catch (Exception e) {
            Log.d("translation", e.toString());
        }

        return jsel;
    }

    public  class RequestBody {
        String Text;

        public RequestBody(String text) {
            this.Text = text;
        }
    }

    public  String Post (URL url, String content) throws Exception {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", content.length() + "");
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
        connection.setRequestProperty("X-ClientTraceId", java.util.UUID.randomUUID().toString());
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        byte[] encoded_content = content.getBytes("UTF-8");
        wr.write(encoded_content, 0, encoded_content.length);
        wr.flush();
        wr.close();

        StringBuilder response = new StringBuilder ();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return response.toString();
    }

    public  String Translate () throws Exception {
        URL url = new URL (host + path + params);

        List<RequestBody> objList = new ArrayList<RequestBody>();
        objList.add(new RequestBody(text));
        String content = new Gson().toJson(objList);

        return Post(url, content);
    }

    public  JsonElement prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        return json;
      //  Gson gson = new GsonBuilder().setPrettyPrinting().create();
      //  return gson.toJson(json);
    }

 /*   public JSONObject getJsonObject (){
        String response = null;
        try {
            response = this.Translate ();
            String pretty = this.prettify(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String pretty = this.prettify(response);

    }*/


}
