package com.example.diana.licentaschelet.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.Date;
import java.text.SimpleDateFormat;


public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    private static final String DATABASE_NAME = "Licenta2.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_FRIDGEPRODUCTS = "Table_FridgeProducts";
    private static final String TABLE_FAVORITEPRODUCTS = "Table_FavoriteProducts";
    private static final String TABLE_CONSUMEDPRODUCTS = "Table_ConsumedProducts";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_PRODUCER = "PRODUCER";
    private static final String COL_CALORIES = "CALORIES";
    private static final String COL_DESCRIPTION = "DESCRIPTION";
    private static final String COL_EXPDATE = "EXPIRYDATE";
    private static final String COL_PRICE = "PRICE";
    private static final String COL_CONSUMEDDAY = "CONSUMEDDAY";

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {

        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_FRIDGEPRODUCTS +" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                                     "NAME TEXT," +
                                                                    "PRODUCER TEXT," +
                                                                    "CALORIES REAL," +
                                                                    "DESCRIPTION TEXT," +
                                                                    "EXPIRYDATE TEXT," +
                                                                    "PRICE REAL)");

        sqLiteDatabase.execSQL("create table " + TABLE_FAVORITEPRODUCTS +" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "PRODUCER TEXT," +
                "CALORIES REAL," +
                "DESCRIPTION TEXT)" );

        sqLiteDatabase.execSQL("create table " + TABLE_CONSUMEDPRODUCTS +" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "PRODUCER TEXT," +
                "CONSUMEDDAY TEXT," +
                "CALORIES REAL)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_FRIDGEPRODUCTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVORITEPRODUCTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CONSUMEDPRODUCTS);
        onCreate(sqLiteDatabase);
    }

    public boolean insertFridgeProduct(String name,String producer,float calories, String description, String expiryDate, float price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_PRODUCER ,producer);
        contentValues.put(COL_CALORIES,calories);
        contentValues.put(COL_DESCRIPTION,description);
        contentValues.put(COL_EXPDATE ,expiryDate);
        contentValues.put(COL_PRICE ,price);



        long result = db.insert(TABLE_FRIDGEPRODUCTS,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;


    }

    public boolean insertFavoriteProduct(String name,String producer,float calories, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_PRODUCER ,producer);
        contentValues.put(COL_CALORIES,calories);
        contentValues.put(COL_DESCRIPTION,description);


        long result = db.insert(TABLE_FAVORITEPRODUCTS,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;


    }

    public boolean insertConsumedProduct(String name,String producer,float calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateString = sdf.format(date);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_PRODUCER ,producer);
        contentValues.put(COL_CALORIES,calories);
        contentValues.put(COL_CONSUMEDDAY,dateString);



        long result = db.insert(TABLE_CONSUMEDPRODUCTS,null ,contentValues);

        if(result == -1)
            return false;
        else
            return true;


    }

    public Cursor getAllFridgeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_FRIDGEPRODUCTS,null);
        return res;
    }

    public Cursor getAllFavoriteProductsData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_FAVORITEPRODUCTS,null);
        return res;
    }

    public Cursor getAllConsumedProductsData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_CONSUMEDPRODUCTS,null);
        return res;
    }

    public boolean updateFridgeData(String id,String name,String producer,float calories, String description, String expiryDate, float price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID,id);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_PRODUCER ,producer);
        contentValues.put(COL_CALORIES,calories);
        contentValues.put(COL_DESCRIPTION,description);
        contentValues.put(COL_EXPDATE ,expiryDate);
        contentValues.put(COL_PRICE ,price);
        db.update(TABLE_FRIDGEPRODUCTS, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public boolean updateFavoriteData(String id,String name,String producer,float calories, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID,id);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_PRODUCER ,producer);
        contentValues.put(COL_CALORIES,calories);
        contentValues.put(COL_DESCRIPTION,description);

        db.update(TABLE_FAVORITEPRODUCTS, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public boolean updateConsumedProductData(String id,String name,String producer,float calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID,id);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_PRODUCER ,producer);
        contentValues.put(COL_CALORIES,calories);

        db.update(TABLE_CONSUMEDPRODUCTS, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public void deleteFridgeData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIDGEPRODUCTS, "ID = ?",new String[] {id});
    }

    public Integer deleteFavoriteProductData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FAVORITEPRODUCTS, "ID = ?",new String[] {id});
    }

    public Integer deleteConsumedProductData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CONSUMEDPRODUCTS, "ID = ?",new String[] {id});
    }
}
