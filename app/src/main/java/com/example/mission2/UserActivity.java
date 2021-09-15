package com.example.mission2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mission2.model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    TextView userTextViewFullName;
    TextView userTextViewAge;
    TextView userTextViewAddress;
    ArrayList<User> dataUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        ImageButton userButtonEdit = findViewById(R.id.userButtonEdit);
        ImageButton userButtonDelete = findViewById(R.id.userButtonDelete);
        userTextViewFullName = findViewById(R.id.userTextViewFullName);
        userTextViewAge = findViewById(R.id.userTextViewAge);
        userTextViewAddress = findViewById(R.id.userTextViewAddress);
        ProgressDialog progressDialog = new ProgressDialog(UserActivity.this);

        Intent intent = getIntent();
        dataUser = intent.getParcelableArrayListExtra("dataUser");
        int position = intent.getIntExtra("position", -1);
        User user = dataUser.get(position);
        String fullName = user.getFullName();
        String age = user.getAge();
        String address = user.getAddress();

        userTextViewFullName.setText(fullName);
        userTextViewAge.setText(age  + " years old");
        userTextViewAddress.setText(address);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, AddEditUserActivity.class);
                intent.putExtra("type", "edit");
                intent.putExtra("position", position);
                intent.putExtra("dataUser", dataUser);
                addEditUserActivity.launch(intent);
            }
        });

        userButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Are you sure to delete " + fullName + " data?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.show();
                                progressDialog.setContentView(R.layout.dialog_progress);
                                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setResult(Activity.RESULT_OK, intent);
                                        dataUser.remove(position);
                                        Intent intent = new Intent();
                                        intent.putExtra("dataUser", dataUser);
                                        setResult(Activity.RESULT_OK, intent);
                                        finish();
                                        progressDialog.dismiss();
                                        Toast.makeText(UserActivity.this, "Delete successful!", Toast.LENGTH_SHORT).show();
                                    }
                                }, 2000);
                            }
                        });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    ActivityResultLauncher<Intent> addEditUserActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        assert intent != null;
                        dataUser = intent.getParcelableArrayListExtra("dataUser");
                        User user = dataUser.get(intent.getIntExtra("position", -1));
                        userTextViewFullName.setText(user.getFullName());
                        userTextViewAge.setText(user.getAge() + " years old");
                        userTextViewAddress.setText(user.getAddress());
                        setResult(Activity.RESULT_OK, intent);
                    }
                }
            });
}