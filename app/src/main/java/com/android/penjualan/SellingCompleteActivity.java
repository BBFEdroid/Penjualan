package com.android.penjualan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SellingCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_complete);

        findViewById(R.id.selling_complete_btn_continue_selling).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);

        });
    }
}