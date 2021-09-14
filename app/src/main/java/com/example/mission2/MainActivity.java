package com.example.mission2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mission2.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnCardListener {
    ArrayList<User> dataUser;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataUser = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(dataUser, MainActivity.this, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recyclerViewAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivityForResult() depreciated
                Intent intent = new Intent(MainActivity.this, AddEditUserActivity.class);
                addEditUserActivityLauncher.launch(intent);
            }
        });
    }

    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("user", dataUser.get(position));
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
                        System.out.println(user.getFullName());
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            });

    ActivityResultLauncher<Intent> userActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 1) {
                        Intent data = result.getData();
                        assert data != null;
                        int index = data.getIntExtra("position", -1);
                        User user = data.getParcelableExtra("user");
                        dataUser.get(index).setFullName(user.getFullName());
                        dataUser.get(index).setAge(user.getAge());
                        dataUser.get(index).setAddress(user.getAddress());
                        recyclerViewAdapter.notifyDataSetChanged();
                    } else if (result.getResultCode() == 2) {
                        Intent data = result.getData();
                        assert data != null;
                        int index = data.getIntExtra("position", -1);
                        dataUser.remove(index);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            });
}