package com.example.diana.licentaschelet.Activities.ProductRelated;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diana.licentaschelet.Classes.ConsumedProduct;
import com.example.diana.licentaschelet.Classes.DBHelper;
import com.example.diana.licentaschelet.Classes.FridgeProduct;
import com.example.diana.licentaschelet.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProductActivity extends Activity {

    private EditText txtProductName;
    private EditText txtProducer;
    private EditText txtCalories;
    private EditText txtExpiryDate;
    private EditText txtDescription;
    private EditText txtPrice;
    private Button btnSave;
    private DBHelper dbh;
    private FridgeProduct fp;
    private String operatie;
    private ConsumedProduct cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        dbh = DBHelper.getInstance(getApplicationContext());
        txtProductName = findViewById(R.id.txtProductName);
        txtProducer = findViewById(R.id.txtProducer);
        txtCalories = findViewById(R.id.txtCalories);
        txtExpiryDate = findViewById(R.id.txtDate);
        txtDescription = findViewById(R.id.txtDescription);
        txtPrice = findViewById(R.id.txtPrice);
        btnSave = findViewById(R.id.btnSave);

        fp=new FridgeProduct();
        Bundle bundle = getIntent().getExtras();
        operatie = bundle.getString("operatie");
        if(operatie.equals("edit") || operatie.equals("addAsFav") || operatie.equals("scanFromFridge") || operatie.equals("addFromFav")){

            fp = (FridgeProduct)bundle.getSerializable("obiect");
            txtProductName.setText(fp.getName());
            txtProducer.setText(fp.getProducer());
            txtCalories.setText(String.valueOf(fp.getCalories()));
            txtExpiryDate.setText(fp.getExpiryDate());
            txtDescription.setText(fp.getDescription());
            txtPrice.setText(String.valueOf(fp.getPrice()));
        }

        if(operatie.equals("scanFromToday")){
            ConsumedProduct cp = (ConsumedProduct)bundle.getSerializable("obiect");
            txtProductName.setText(cp.getName());
            txtProducer.setText(cp.getProducer());
            txtCalories.setText(String.valueOf(cp.getKcal()));
            txtExpiryDate.setText(cp.getDateConsumed());

        }

        if(operatie.equals("addTodayFrom")){
            cp=(ConsumedProduct)bundle.getSerializable("obiect");
            txtProductName.setText(cp.getName());
            txtProducer.setText(cp.getProducer());
            txtCalories.setText(String.valueOf(fp.getCalories()));


        }

        if(operatie.equals("editToday")){
             cp = (ConsumedProduct)bundle.getSerializable("obiect");
            txtProductName.setText(cp.getName());
            txtProducer.setText(cp.getProducer());
            txtCalories.setText(String.valueOf(cp.getKcal()));
            txtExpiryDate.setText(cp.getDateConsumed());

        }

        if(operatie.equals("addFav") || operatie.equals("addAsFav")){
            txtExpiryDate.setText("");
            txtExpiryDate.setEnabled(false);
            txtPrice.setText("");
            txtPrice.setEnabled(false);
        }



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputValid()){


                    String name = "";
                    String producer = "";
                    float calories = 0;
                    String expiryDate = "";
                    String description = "";
                    float price = 0;

                    if(!txtProductName.getText().toString().trim().isEmpty()){
                        name = txtProductName.getText().toString().trim();
                    }

                    if(!txtProducer.getText().toString().trim().isEmpty()){
                        producer = txtProducer.getText().toString().trim();
                    }

                    if(!txtCalories.getText().toString().trim().isEmpty()){
                        calories = Float.parseFloat(txtCalories.getText().toString().trim());
                    }

                    if(!txtExpiryDate.getText().toString().trim().isEmpty()){
                        expiryDate = txtExpiryDate.getText().toString().trim();
                    }

                    if(!txtDescription.getText().toString().trim().isEmpty()){
                        description = txtDescription.getText().toString().trim();

                    }

                    if(!txtPrice.getText().toString().trim().isEmpty()){
                        price = Float.parseFloat(txtPrice.getText().toString().trim());
                    }





                    //Product p = new FridgeProduct(name,producer,calories,description,expiryDate,price);

                    if(operatie.equals("edit")){
                        dbh.updateFridgeData(fp.getId(),name,producer,calories,description,expiryDate,price);
                    }

                    if(operatie.equals("add") || operatie.equals("scan") || operatie.equals("addFromFav")){
                        boolean isInserted = dbh.insertFridgeProduct(name,producer,calories,description,expiryDate,price);
                        if(isInserted != true){
                            Toast.makeText(getApplicationContext(),R.string.tryAgain,Toast.LENGTH_LONG).show();
                        }

                    }

                    if(operatie.equals("addFav") || operatie.equals("addAsFav")){
                        dbh.insertFavoriteProduct(name,producer,calories,description);
                    }

                    if(operatie.equals("addToday") || operatie.equals("scanToday") ){

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date today = new Date();
                        String todayString = sdf.format(today);

                        dbh.insertConsumedProduct(name,producer,calories);
                    }

                    if(operatie.equals("editToday")){
                        dbh.updateConsumedProductData(cp.getId(),name,producer,calories);

                    }




                    finish();

                }
            }
        });


    }


    public boolean inputValid(){
        boolean ok = true;

        String name = txtProductName.getText().toString().trim();
        String producer = txtProducer.getText().toString().trim();
       // float calories = Float.parseFloat(txtCalories.getText().toString().trim());
        String expiryDate = txtExpiryDate.getText().toString().trim();
        String description = txtDescription.getText().toString().trim();
       // float price = Float.parseFloat(txtPrice.getText().toString().trim());

        if(!txtCalories.getText().toString().trim().isEmpty() && !String.valueOf(txtCalories.getText().toString().trim()).matches("[0-9.]*")){
            ok=false;
            txtCalories.setError(getString(R.string.failkcal));
        }

        if(!operatie.equals("addFav") && !String.valueOf(txtPrice.getText().toString().trim()).isEmpty() && !String.valueOf(txtPrice.getText().toString().trim()).matches("[0-9.]*")){
            ok=false;
            txtPrice.setError(getString(R.string.failprice));
        }

        if(name.isEmpty() ){
            ok=false;
            txtProductName.setError(getString(R.string.failempty));

        }

        if(!operatie.equals("addFav") && !operatie.equals("addAsFav")&& expiryDate.isEmpty()){
            ok=false;
            txtExpiryDate.setError(getString(R.string.failempty));
        }

        if(!operatie.equals("addFav") && !operatie.equals("addAsFav") && (expiryDate.length()!=10 ||
                expiryDate.charAt(2) != '/' ||
                expiryDate.charAt(5) != '/' ||
               !Character.isDigit(expiryDate.charAt(0)) ||
               !Character.isDigit(expiryDate.charAt(1)) ||
               !Character.isDigit(expiryDate.charAt(3)) ||
               !Character.isDigit(expiryDate.charAt(4)) ||
               !Character.isDigit(expiryDate.charAt(6)) ||
               !Character.isDigit(expiryDate.charAt(7)) ||
               !Character.isDigit(expiryDate.charAt(8)) ||
               !Character.isDigit(expiryDate.charAt(9)))){



            ok=false;
            txtExpiryDate.setError(getString(R.string.faildate));
        }



        return ok;
    }



}
