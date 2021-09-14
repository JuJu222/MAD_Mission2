package com.example.mission2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.example.mission2.model.User;
import com.google.android.material.textfield.TextInputLayout;

public class AddEditUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        Button button = findViewById(R.id.button);
        TextInputLayout textInputLayoutFullName = findViewById(R.id.textInputLayoutFullName);
        TextInputLayout textInputLayoutAge = findViewById(R.id.textInputLayoutAge);
        TextInputLayout textInputLayoutAddress = findViewById(R.id.textInputLayoutAddress);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = textInputLayoutFullName.getEditText().getText().toString();
                String age = textInputLayoutAge.getEditText().getText().toString();
                String address = textInputLayoutAddress.getEditText().getText().toString();
                User user = new User(fullName, age, address);

                Intent intent = new Intent();
                intent.putExtra("user", user);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}