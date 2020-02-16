
package com.example.diana.licentaschelet.Activities.ProductRelated;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diana.licentaschelet.Classes.AsyncGetJSON;
import com.example.diana.licentaschelet.Classes.ConsumedProduct;
import com.example.diana.licentaschelet.Classes.DBHelper;
import com.example.diana.licentaschelet.Classes.FridgeProduct;
import com.example.diana.licentaschelet.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class FoodToday extends Activity {

    private DBHelper dbh;
    private Button btnAddToAteToday;
    private Button btnScan;
    private ListView lvAteToday;
    private ArrayList<ConsumedProduct> listaProduseConsumate;
  //  private   static final int REQUEST_IMAGE_CAPTURE = 1;
   // private static final String CHANNEL_ID = "1";
    private ImageView mImageView;
    private ProgressDialog progressDialog;
    private TextView tvCalories;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_today);





        lvAteToday = findViewById(R.id.lvAteToday);
       registerForContextMenu(lvAteToday);
        dbh=DBHelper.getInstance(getApplicationContext());
        btnAddToAteToday = findViewById(R.id.btnAddProductToAteToday);
        btnScan = findViewById(R.id.btnScanToday);
        mImageView = findViewById(R.id.mImageViewToday);
        tvCalories =findViewById(R.id.tvCalorii);
        sharedPreferences = getSharedPreferences("SharedPreferences",0);
         editor = sharedPreferences.edit();

        populateFromDb();

        btnAddToAteToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                intent.putExtra("operatie","addToday");
                Log.d("today","hello");
                startActivity(intent);
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncGetJSON agj = new AsyncGetJSON("https://world.openfoodfacts.org/api/v0/product/5941056500098.json",FoodToday.this);



                ConsumedProduct cp = new ConsumedProduct();
                try { progressDialog = new ProgressDialog(FoodToday.this);
                    progressDialog.setMessage(getString(R.string.searching));
                    progressDialog.show();
                    FridgeProduct fp = agj.execute().get();
                    cp.setName(fp.getName());
                    cp.setProducer(fp.getProducer());
                    cp.setKcal(fp.getCalories());

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date today = new Date();
                    String todayString = sdf.format(today);


                    cp.setDateConsumed(todayString);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                intent.putExtra("operatie","scanFromToday");
                intent.putExtra("obiect",cp);
                progressDialog.dismiss();
                startActivity(intent);

//                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(),"problem with camera",Toast.LENGTH_SHORT);
//                    }

            }
        });





//if(!listaProduseConsumate.isEmpty()){
  //          for(ConsumedProduct p : listaProduseConsumate){
    //            try {
      //              if(true){
//
  //                  }
//
  //              } catch (ParseException e) {
    //                e.printStackTrace();
      //          }
        //    }
        //}







    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageView.setImageBitmap(imageBitmap);
//        }
//    }




    public void populateFromDb() {
        listaProduseConsumate = new ArrayList<>();


        SQLiteDatabase newDB = dbh.getWritableDatabase();
        Cursor c = newDB.rawQuery("SELECT * FROM Table_ConsumedProducts", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(c.getColumnIndex("ID"));
                    String nume = c.getString(c.getColumnIndex("NAME"));
                    String producator = c.getString(c.getColumnIndex("PRODUCER"));
                    float calorii = c.getFloat(c.getColumnIndex("CALORIES"));

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date today = new Date();
                    String todayString = sdf.format(today);
                   // String data = c.getString(c.getColumnIndex("EXPIRYDATE"));
                    listaProduseConsumate.add(new ConsumedProduct(id,nume,producator,calorii,todayString));
                    //listaProduse.add(nume + " " + producator + "\n" + "Exp: " + data);
                } while (c.moveToNext());

                ArrayAdapter<ConsumedProduct> adapter = new ArrayAdapter<ConsumedProduct>(this, android.R.layout.simple_list_item_1, listaProduseConsumate);
                lvAteToday.setAdapter(adapter);
                float suma = 0;
                for(ConsumedProduct consp : listaProduseConsumate){
                     suma += consp.getKcal();

                }

                tvCalories.setText(String.valueOf(suma));
                editor.putFloat("kcalToday",suma);
                editor.apply();


            }


        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        populateFromDb();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        populateFromDb();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_fridge, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ConsumedProduct p=listaProduseConsumate.get((int)info.id);
        switch (item.getItemId()) {
            case R.id.seeMore:
                editConsumedItem(p);
                return true;
            case R.id.delete:
                deleteConsumedItem(p.getId());
                return true;

            case R.id.addAsFav:
                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                intent.putExtra("operatie","addAsFav");
                intent.putExtra("obiect",p);
                startActivity(intent);

            default: return false;
        }
    }

    public void deleteConsumedItem(String id){

        dbh.deleteConsumedProductData(id);
        Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show();
        populateFromDb();
    }

    public void editConsumedItem( ConsumedProduct p){
        Intent intent = new Intent(this,AddProductActivity.class);
        intent.putExtra("operatie","editToday");
        intent.putExtra("obiect",p);
        startActivity(intent);
    }
}

