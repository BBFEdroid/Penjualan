package com.android.penjualan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "penjualan_db";
    private static final int DATABASE_VERSION = 1;

    //    table names
    private static final String TABLE_LOGINS = "logins";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_TRANSACTION_HEADER = "transaction_headers";
    private static final String TABLE_TRANSACTION_DETAIL = "transaction_details";

    // Common column names
    private static final String KEY_ID = "id";

    // Logins table column names
    private static final String KEY_USERNAME = "user";
    private static final String KEY_PASSWORD = "password";

    // Products table column names
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_PRODUCT_CODE = "product_code";
    private static final String KEY_PRICE = "price";
    private static final String KEY_CURRENCY = "currency";
    private static final String KEY_DISCOUNT = "discount";
    private static final String KEY_DIMENSION = "dimension";
    private static final String KEY_UNIT = "unit";

    // Transaction Header table column names
    private static final String KEY_DOCUMENT_CODE = "document_code";
    private static final String KEY_DOCUMENT_NUMBER = "document_number";
    private static final String KEY_DOCUMENT_USER = "user";
    private static final String KEY_DOCUMENT_TOTAL = "total";
    private static final String KEY_DOCUMENT_DATE = "date";

    // Transaction Detail table column names
    private static final String KEY_DET_CODE = "document_code";
    private static final String KEY_DET_NUMBER = "document_number";
    private static final String KEY_DET_PRODUCT = "product_code";
    private static final String KEY_DET_PRICE = "price";
    private static final String KEY_DET_QUANTITY = "quantity";
    private static final String KEY_DET_UNIT = "unit";
    private static final String KEY_DET_SUB_TOTAL = "sub_total";
    private static final String KEY_DET_CURRENCY = "currency";

    // Create tables
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_LOGINS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_USERNAME + " VARCHAR(50),"
            + KEY_PASSWORD + " VARCHAR(255)" + ")";

    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PRODUCT_NAME + " VARCHAR(18),"
            + KEY_PRODUCT_CODE + " VARCHAR(30),"
            + KEY_PRICE + " NUMERIC(6),"
            + KEY_CURRENCY + " VARCHAR(5),"
            + KEY_DISCOUNT + " NUMERIC(6),"
            + KEY_DIMENSION + " VARCHAR(50),"
            + KEY_UNIT + " VARCHAR(5)" + ")";

    private static final String CREATE_TABLE_TRANSACTION_HEADER = "CREATE TABLE " + TABLE_TRANSACTION_HEADER + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DOCUMENT_CODE + " VARCHAR(3),"
            + KEY_DOCUMENT_NUMBER + " VARCHAR(10),"
            + KEY_DOCUMENT_USER + " VARCHAR(50),"
            + KEY_DOCUMENT_TOTAL + " NUMERIC(10),"
            + KEY_DOCUMENT_DATE + " DATE(10)" + ")";

    private static final String CREATE_TABLE_TRANSACTION_DETAIL = "CREATE TABLE " + TABLE_TRANSACTION_DETAIL + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DET_CODE + " VARCHAR(3),"
            + KEY_DET_NUMBER + " VARCHAR(10),"
            + KEY_DET_PRODUCT + " VARCHAR(18),"
            + KEY_DET_PRICE + " NUMERIC(6),"
            + KEY_DET_QUANTITY + " INTEGER(6),"
            + KEY_DET_UNIT + " VARCHAR(5),"
            + KEY_DET_SUB_TOTAL + " NUMERIC(10),"
            + KEY_DET_CURRENCY + " VARCHAR(5)" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_TRANSACTION_HEADER);
        db.execSQL(CREATE_TABLE_TRANSACTION_DETAIL);

//        WRITE TO TABLE
//        user
        ContentValues values = new ContentValues();
        values.put("user", "admin");
        values.put("password", "password123");
        db.insert("logins", null, values);

//        data product
        values = new ContentValues();
        values.put("product_name", "So Klin Pewangi");
        values.put("product_code", "SKUSKILNP");
        values.put("price", 15000);
        values.put("currency", "IDR");
        values.put("discount", 10);
        values.put("dimension", "13cmx10cm");
        values.put("unit", "PCS");
        db.insert("products", null, values);

        values = new ContentValues();
        values.put("product_name", "So Klin");
        values.put("product_code", "SKUSKILN");
        values.put("price", 15000);
        values.put("currency", "IDR");
        values.put("discount", 10);
        values.put("dimension", "10cmx10cm");
        values.put("unit", "PCS");
        db.insert("products", null, values);

        values = new ContentValues();
        values.put("product_name", "Giv Sabun Badan");
        values.put("product_code", "SKUGISABA");
        values.put("price", 5000);
        values.put("currency", "IDR");
        values.put("discount", 10);
        values.put("dimension", "9cmx5cm");
        values.put("unit", "PCS");
        db.insert("products", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION_HEADER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION_DETAIL);
        onCreate(db);
    }

}

