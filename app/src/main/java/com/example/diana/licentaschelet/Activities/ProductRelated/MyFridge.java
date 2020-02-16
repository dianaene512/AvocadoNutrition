package com.example.diana.licentaschelet.Activities.ProductRelated;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diana.licentaschelet.Classes.AsyncGetJSON;
import com.example.diana.licentaschelet.Classes.DBHelper;
import com.example.diana.licentaschelet.Classes.FridgeProduct;
import com.example.diana.licentaschelet.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MyFridge extends Activity {

    private DBHelper dbh;
  private Button btnAddToFridge;
  private Button btnScan;
  private ListView lvFridge;
  private ArrayList<FridgeProduct> listaProduseFrigider;
 private   static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String CHANNEL_ID = "1";
    private ImageView mImageView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fridge);





        lvFridge = findViewById(R.id.lvFridge);
        registerForContextMenu(lvFridge);
        dbh=DBHelper.getInstance(getApplicationContext());
        btnAddToFridge = findViewById(R.id.btnAddProductToFridge);
        btnScan = findViewById(R.id.btnScan);
        mImageView = findViewById(R.id.mImageView);

        populateFromDb();

        btnAddToFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                intent.putExtra("operatie","add");
                startActivity(intent);
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncGetJSON agj = new AsyncGetJSON("https://world.openfoodfacts.org/api/v0/product/5941056500098.json",MyFridge.this);



                FridgeProduct fp = new FridgeProduct();
                try { progressDialog = new ProgressDialog(MyFridge.this);
                    progressDialog.setMessage(getString(R.string.searching));
                    progressDialog.show();
                    fp = agj.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                   intent.putExtra("operatie","scanFromFridge");
                   intent.putExtra("obiect",fp);
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


       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Date exp = new Date();

        if(!listaProduseFrigider.isEmpty()){
            for(FridgeProduct p : listaProduseFrigider){
                try {
                    if(!p.getExpiryDate().isEmpty()){
                        exp = sdf.parse(p.getExpiryDate());
                        if(today.compareTo(exp)>0){
                            sendExpiryDateNotification();
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }






    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ChannelName";
            String description = "ChannelDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageView.setImageBitmap(imageBitmap);
//        }
//    }


    public void sendExpiryDateNotification(){

        Intent intent = new Intent(this, MyFridge.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.avocadoicon)
                .setContentTitle("Expired products")
                .setContentText("You have expired products in your fridge. Please take action")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }


    public void populateFromDb() {
        listaProduseFrigider = new ArrayList<>();


        SQLiteDatabase newDB = dbh.getWritableDatabase();
        Cursor c = newDB.rawQuery("SELECT * FROM Table_FridgeProducts", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(c.getColumnIndex("ID"));
                    String nume = c.getString(c.getColumnIndex("NAME"));
                    String producator = c.getString(c.getColumnIndex("PRODUCER"));
                    float calorii = c.getFloat(c.getColumnIndex("CALORIES"));
                    String description = c.getString(c.getColumnIndex("DESCRIPTION"));
                    float pret = c.getFloat(c.getColumnIndex("PRICE"));
                    String data = c.getString(c.getColumnIndex("EXPIRYDATE"));
                    listaProduseFrigider.add(new FridgeProduct(id,nume,producator,calorii,description,data,pret));
                    //listaProduse.add(nume + " " + producator + "\n" + "Exp: " + data);
                } while (c.moveToNext());

                ArrayAdapter<FridgeProduct> adapter = new ArrayAdapter<FridgeProduct>(this, android.R.layout.simple_list_item_1, listaProduseFrigider);
                lvFridge.setAdapter(adapter);


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
        FridgeProduct p=listaProduseFrigider.get((int)info.id);
        switch (item.getItemId()) {
            case R.id.seeMore:
                editFridgeItem(p);
                return true;
            case R.id.delete:
                deleteFridgeItem(p.getId());
                return true;

            case R.id.addAsFav:
                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                intent.putExtra("operatie","addAsFav");
                intent.putExtra("obiect",p);
                startActivity(intent);

            default: return false;
        }
    }

    public void deleteFridgeItem(String id){

        dbh.deleteFridgeData(id);
        Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show();
        populateFromDb();
    }

    public void editFridgeItem( FridgeProduct p){
        Intent intent = new Intent(this,AddProductActivity.class);
        intent.putExtra("operatie","edit");
        intent.putExtra("obiect",p);
        startActivity(intent);
    }
}