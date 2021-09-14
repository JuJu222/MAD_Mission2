package com.example.mission2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mission2.model.User;
import com.google.android.material.textfield.TextInputLayout;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        ImageButton userButtonEdit = findViewById(R.id.userButtonEdit);
        ImageButton userButtonDelete = findViewById(R.id.userButtonDelete);
        TextView userTextViewFullName = findViewById(R.id.userTextViewFullName);
        TextView userTextViewAge = findViewById(R.id.userTextViewAge);
        TextView userTextViewAddress = findViewById(R.id.userTextViewAddress);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        User user = intent.getParcelableExtra("user");
        String fullName = user.getFullName();
        String age = user.getAge();
        String address = user.getAddress();

        userTextViewFullName.setText(fullName);
        userTextViewAge.setText(age);
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
                                setResult(2, intent);
                                finish();
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
                        Intent data = result.getData();
                        assert data != null;
                        int index = data.getIntExtra("position", -1);
                        User user = data.getParcelableExtra("user");
                    }
                }
            });
}