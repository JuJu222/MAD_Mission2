package com.example.mission2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.mission2.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnCardListener {
    ArrayList<User> dataUser;
    RecyclerViewAdapter recyclerViewAdapter;
    TextView mainTextView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataUser = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(dataUser, MainActivity.this, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        mainTextView = findViewById(R.id.mainTextView);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recyclerViewAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              startActivityForResult() depreciated
                Intent intent = new Intent(MainActivity.this, AddEditUserActivity.class);
                intent.putExtra("type", "add");
                addEditUserActivityLauncher.launch(intent);
            }
        });
    }

    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("dataUser", dataUser);
        userActivityLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> addEditUserActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        User user = data.getParcelableExtra("user");
                        dataUser.add(user);

                        if (dataUser.isEmpty()) {
                            mainTextView.setVisibility(View.VISIBLE);
                        } else {
                            mainTextView.setVisibility(View.GONE);
                        }

                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            });

    ActivityResultLauncher<Intent> userActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        ArrayList<User> temp = data.getParcelableArrayListExtra("dataUser");
//                        dataUser = temp (tidak dapat digunakan karena bug notifyDataSetChanged())
//                        Bila di print, isi arraylist dataUser sudah berubah tetapi tampilan tidak mau berubah
                        dataUser.clear();
                        dataUser.addAll(temp);

                        if (dataUser.isEmpty()) {
                            mainTextView.setVisibility(View.VISIBLE);
                        } else {
                            mainTextView.setVisibility(View.GONE);
                        }

                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            });
}