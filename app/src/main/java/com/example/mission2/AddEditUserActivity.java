package com.example.mission2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.example.mission2.model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

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
        ProgressDialog progressDialog = new ProgressDialog(AddEditUserActivity.this);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        if (type.equals("add")) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.dialog_progress);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String fullName = textInputLayoutFullName.getEditText().getText().toString();
                            String age = textInputLayoutAge.getEditText().getText().toString();
                            String address = textInputLayoutAddress.getEditText().getText().toString();
                            User user = new User(fullName, age, address);

                            Intent intent = new Intent();
                            intent.putExtra("user", user);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                            progressDialog.dismiss();
                        }
                    }, 2000);
                }
            });
        } else {
            toolbar.setTitle("Edit User");
            button.setText("Update Data");

            int position = intent.getIntExtra("position", -1);
            ArrayList<User> dataUser = intent.getParcelableArrayListExtra("dataUser");

            textInputLayoutFullName.getEditText().setText(dataUser.get(position).getFullName());
            textInputLayoutAge.getEditText().setText(dataUser.get(position).getAge());
            textInputLayoutAddress.getEditText().setText(dataUser.get(position).getAddress());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.dialog_progress);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String fullName = textInputLayoutFullName.getEditText().getText().toString();
                            String age = textInputLayoutAge.getEditText().getText().toString();
                            String address = textInputLayoutAddress.getEditText().getText().toString();
                            User user = new User(fullName, age, address);

                            dataUser.set(position, user);
                            Intent intent = new Intent();
                            intent.putExtra("position", position);
                            intent.putParcelableArrayListExtra("dataUser", dataUser);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                            progressDialog.dismiss();
                        }
                    }, 2000);
                }
            });
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}