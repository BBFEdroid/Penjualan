package com.android.penjualan;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.android.penjualan.database.DatabaseHelper;
import com.droidbond.loadingbutton.LoadingButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText loginUsername, loginPassword;
    private LoadingButton loginBtn;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        });

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Insert data into Users table
        SQLiteDatabase db = databaseHelper.getReadableDatabase();


    }

}