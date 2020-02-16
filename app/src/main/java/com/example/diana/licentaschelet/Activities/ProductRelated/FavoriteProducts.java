package com.example.diana.licentaschelet.Activities.ProductRelated;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.diana.licentaschelet.Classes.DBHelper;
import com.example.diana.licentaschelet.Classes.FridgeProduct;
import com.example.diana.licentaschelet.R;

import java.util.ArrayList;

public class FavoriteProducts extends Activity {

    private TextView txtFav;
    private ListView lvFavorites;
    private Button btnAddNewFavorite;
    private ArrayList<FridgeProduct> listaProduseFav;
    private DBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_products);

        txtFav = findViewById(R.id.txtFavs);
        Typeface typeface = ResourcesCompat.getFont(this,R.font.galada);
        txtFav.setTypeface(typeface);
        lvFavorites = findViewById(R.id.lvFavorites);
        registerForContextMenu(lvFavorites);
        btnAddNewFavorite = findViewById(R.id.btnAddFavorite);

        dbh = DBHelper.getInstance(getApplicationContext());

        populateFromDb();

        btnAddNewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                intent.putExtra("operatie","addFav");
                startActivity(intent);
            }
        });


    }

    public void populateFromDb() {
        listaProduseFav = new ArrayList<FridgeProduct>();

        Cursor c = dbh.getAllFavoriteProductsData();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(c.getColumnIndex("ID"));
                    String nume = c.getString(c.getColumnIndex("NAME"));
                    String producator = c.getString(c.getColumnIndex("PRODUCER"));
                    float calorii = c.getFloat(c.getColumnIndex("CALORIES"));
                    String description = c.getString(c.getColumnIndex("DESCRIPTION"));
                    listaProduseFav.add(new FridgeProduct(id,nume,producator,calorii,description));

                } while (c.moveToNext());

                ArrayAdapter<FridgeProduct> adapter = new ArrayAdapter<FridgeProduct>(this, android.R.layout.simple_list_item_1, listaProduseFav);
                lvFavorites.setAdapter(adapter);


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
        getMenuInflater().inflate(R.menu.contextmenu_favorites, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        FridgeProduct p=listaProduseFav.get((int)info.id);
        switch (item.getItemId()) {

            case R.id.delete:
                deleteFavItem(p.getId());
                return true;

            case R.id.addToFridge:
                Intent intent = new Intent(getApplicationContext(),AddProductActivity.class);
                intent.putExtra("operatie","addFromFav");
                intent.putExtra("obiect",p);
                startActivity(intent);
               // Toast.makeText(getApplicationContext(), R.string.productaddedtoyourfridge,Toast.LENGTH_LONG).show();
            default: return false;
        }
    }

    public void deleteFavItem(String id){

        dbh.deleteFavoriteProductData(id);
        populateFromDb();
    }

}
