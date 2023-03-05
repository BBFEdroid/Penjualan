package com.android.penjualan;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.penjualan.database.DatabaseHelper;
import com.android.penjualan.model.ProductModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductListActivity extends AppCompatActivity {
    private Button productlistCartBtn;
    private RecyclerView productlistItem;
    private ArrayList<ProductModel> productModels;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_product_list);

        productlistCartBtn = findViewById(R.id.productlist_cart_btn);
        productlistItem = findViewById(R.id.productlist_list);

        productlistCartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });


        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);
        // Insert data into Users table
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from products", null);
        productModels = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ProductModel productModel = new ProductModel();
                productModel.setProduct_id(cursor.getInt(0));
                productModel.setProduct_currency(cursor.getString(4));
                productModel.setProduct_unit(cursor.getString(7));
                productModel.setProduct_name(cursor.getString(1));
                productModel.setProduct_price(cursor.getInt(3));
                productModel.setProduct_discount(cursor.getInt(5));
                productModel.setProduct_code(cursor.getString(2));
                productModels.add(productModel);
            } while (cursor.moveToNext());
        }
        db.close();
        Log.d(TAG, "onCreate: " + new Gson().toJson(productModels));
        productAdapter = new ProductAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductListActivity.this, LinearLayoutManager.VERTICAL, false);
        productlistItem.setLayoutManager(linearLayoutManager);
        productlistItem.setAdapter(productAdapter);
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
        @NonNull
        @Override
        public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_product_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
            ProductModel productModel = productModels.get(position);
//            holder.productImg.setImageDrawable(productModel.getProductImg());
            holder.productName.setText(productModel.getProduct_name());
            holder.productLatestPrice.setText(String.valueOf(productModel.getProduct_discount()));
            holder.productPrice.setText(String.valueOf(productModel.getProduct_price()));
            holder.productBtnBuy.setOnClickListener(v -> {

            });
            holder.productImg.setOnClickListener(v -> {
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra("product_id", productModel.getProduct_id());
                startActivity(intent);
            });
            holder.productBtnBuy.setOnClickListener(v -> {
                int qty = 1;
                SQLiteDatabase dbRead = databaseHelper.getReadableDatabase();
                Cursor cursorRead = dbRead.rawQuery("select * from transaction_details where product_code=?", new String[]{productModel.getProduct_code()});
                if (cursorRead.moveToFirst()) {
                    if (cursorRead.getInt(0) > 0) {
                        qty += cursorRead.getInt(5);
                        SQLiteDatabase db = databaseHelper.getReadableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("quantity", qty);
                        values.put("sub_total", productModel.getProduct_price() * qty);
                        db.update("transaction_details", values, "id=?", new String[]{String.valueOf(cursorRead.getInt(0))});
                        db.close();
                    }
                } else {
                    SQLiteDatabase db = databaseHelper.getReadableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("document_code", "TRX");
                    values.put("document_number", random());
                    values.put("product_code", productModel.getProduct_code());
                    values.put("price", productModel.getProduct_price());
                    values.put("quantity", qty);
                    values.put("unit", productModel.getProduct_unit());
                    values.put("sub_total", productModel.getProduct_price() * qty);
                    values.put("currency", productModel.getProduct_currency());
                    db.insert("transaction_details", null, values);
                    db.close();
                }
                cursorRead.close();

            });

            holder.productLatestPrice.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount() {
            return productModels.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CircleImageView productImg;
            TextView productName, productPrice, productLatestPrice, productBtnBuy;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                productImg = itemView.findViewById(R.id.item_product_list_img);
                productName = itemView.findViewById(R.id.item_product_list_name);
                productLatestPrice = itemView.findViewById(R.id.item_product_list_lastest_price);
                productPrice = itemView.findViewById(R.id.item_product_list_price);
                productBtnBuy = itemView.findViewById(R.id.item_product_list_btn_buy);
            }
        }
    }

    private static int random() {
        Random r = new Random();
        int i = r.nextInt(80 - 65) + 65;
        return i;
    }
}