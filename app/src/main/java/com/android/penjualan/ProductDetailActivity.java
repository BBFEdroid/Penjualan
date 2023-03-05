package com.android.penjualan;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.penjualan.database.DatabaseHelper;
import com.android.penjualan.model.ProductModel;
import com.google.gson.Gson;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView productdetailImage;
    private TextView productdetailName, productdetailPrice, productdetailDimension, productdetailPriceUnit;
    private Button productdetailBtnBuy;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        findViewById(R.id.product_detail_close).setOnClickListener(v -> finish());

        productdetailImage = findViewById(R.id.product_detail_img);
        productdetailName = findViewById(R.id.product_detail_name);
        productdetailPrice = findViewById(R.id.product_detail_price);
        productdetailDimension = findViewById(R.id.product_detail_dimension);
        productdetailPriceUnit = findViewById(R.id.product_detail_price_unit);
        productdetailBtnBuy = findViewById(R.id.product_detail_btn_buy);
        productdetailBtnBuy.setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });

        Intent intent=getIntent();
        int product_id=intent.getIntExtra("product_id",0);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);
        // Insert data into Users table
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from products where id=?", new String[]{String.valueOf(product_id)});
        if (cursor.moveToFirst()){
            ProductModel productModel=new ProductModel();
            productModel.setProduct_id(cursor.getInt(0));
            productModel.setProduct_name(cursor.getString(1));
            productModel.setProduct_price(cursor.getInt(3));
            productModel.setProduct_dimension(cursor.getString(6));
            productModel.setProduct_unit(cursor.getString(7));
            productdetailName.setText(productModel.getProduct_name());
            productdetailPrice.setText(String.valueOf(productModel.getProduct_price()));
            productdetailDimension.setText(productModel.getProduct_dimension());
            productdetailPriceUnit.setText(productModel.getProduct_unit());
        }

    }
}