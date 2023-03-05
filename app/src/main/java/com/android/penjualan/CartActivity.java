package com.android.penjualan;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.penjualan.database.DatabaseHelper;
import com.android.penjualan.model.TransactionDetailModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartList;
    private TextView cartTotal;
    private Button cartConfirmBtn;
    private CartAdapter cartAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<TransactionDetailModel> transactionDetailModels;
    private int tempId = 0;
    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        findViewById(R.id.cart_close).setOnClickListener(v -> finish());

        cartTotal = findViewById(R.id.cart_total);
        cartConfirmBtn = findViewById(R.id.cart_confirm_btn);
        cartList = findViewById(R.id.cart_list);

        cartConfirmBtn.setOnClickListener(v -> {
//            write to db
            databaseHelper = new DatabaseHelper(this);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(date);
            ContentValues values = new ContentValues();
            values.put("document_code", "TRX");
            values.put("document_number", random());
            values.put("user", "SMIT");
            values.put("total", total);
            values.put("date", formattedDate);
            db.insert("transaction_headers", null, values);
        });

        // Initialize database helper
        transactionDetailModels = new ArrayList<>();
        cartAdapter = new CartAdapter(transactionDetailModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cartList.setLayoutManager(linearLayoutManager);
        cartList.setAdapter(cartAdapter);
        loadList();
    }

    private class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
        private List<TransactionDetailModel> rvTransactionDetailModels;

        public CartAdapter(List<TransactionDetailModel> datas) {
            this.rvTransactionDetailModels = datas;
        }

        @NonNull
        @Override
        public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CartAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cart, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
            TransactionDetailModel transactionDetailModel = rvTransactionDetailModels.get(position);
//            holder.cartImg.setImageDrawable(cartModel.getCartImg());
            holder.cartName.setText(transactionDetailModel.getDet_product_name());
            holder.cartQty.setText(String.valueOf(transactionDetailModel.getDet_qty()));
            holder.cartQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() > 0) {
                        tempId = transactionDetailModel.getTrxdet_id();
                        SQLiteDatabase db = databaseHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("quantity", Integer.valueOf(s.toString()));
                        values.put("sub_total", transactionDetailModel.getDet_price() * Integer.parseInt(s.toString()));
                        db.update("transaction_details", values, "id=?", new String[]{String.valueOf(transactionDetailModel.getTrxdet_id())});
                        db.close();
                        loadList();
                        cartAdapter = new CartAdapter(transactionDetailModels);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
                        cartList.setLayoutManager(linearLayoutManager);
                        cartList.setAdapter(cartAdapter);
                    }
                    ;
                }
            });
            holder.cartPriceUnit.setText(transactionDetailModel.getDet_unit());
            holder.cartSubTotal.setText(String.valueOf(transactionDetailModel.getDet_subtotal()));
            if (tempId > 0 && tempId == transactionDetailModel.getTrxdet_id()) {
                holder.cartQty.requestFocus();
                tempId = 0;
            }

        }

        @Override
        public int getItemCount() {
            return rvTransactionDetailModels.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CircleImageView cartImg;
            TextView cartName, cartPriceUnit, cartSubTotal;
            TextInputEditText cartQty;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cartImg = itemView.findViewById(R.id.item_cart_img);
                cartQty = itemView.findViewById(R.id.item_cart_qty);
                cartName = itemView.findViewById(R.id.item_cart_name);
                cartPriceUnit = itemView.findViewById(R.id.item_cart_price_unit);
                cartSubTotal = itemView.findViewById(R.id.item_cart_subtotal);
            }
        }
    }

    private static int random() {
        Random r = new Random();
        int i = r.nextInt(80 - 65) + 65;
        return i;
    }

    private void loadList() {
        databaseHelper = new DatabaseHelper(this);
        List<TransactionDetailModel> list = new ArrayList<>();
        // Insert data into Users table
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select transaction_details.*, products.product_name from transaction_details left join products on products.product_code = transaction_details.product_code", null);
        if (cursor.moveToFirst()) {
            do {
                TransactionDetailModel transactionDetailModel = new TransactionDetailModel();
                transactionDetailModel.setTrxdet_id(Integer.parseInt(String.valueOf(cursor.getInt(0))));
                transactionDetailModel.setDet_document_code(String.valueOf(cursor.getInt(1)));
                transactionDetailModel.setDet_document_number(cursor.getString(2));
                transactionDetailModel.setDet_product_code(cursor.getString(3));
                transactionDetailModel.setDet_price(Integer.parseInt(cursor.getString(4)));
                transactionDetailModel.setDet_qty(Integer.parseInt(cursor.getString(5)));
                transactionDetailModel.setDet_unit(cursor.getString(6));
                transactionDetailModel.setDet_subtotal(Integer.parseInt(cursor.getString(7)));
                transactionDetailModel.setDet_currency(cursor.getString(8));
                transactionDetailModel.setDet_product_name(cursor.getString(9));
                list.add(transactionDetailModel);
                total += cursor.getInt(7);
            } while (cursor.moveToNext());
        }
        db.close();
        transactionDetailModels.clear();
        transactionDetailModels.addAll(list);
        cartTotal.setText(String.valueOf(total));

    }
}