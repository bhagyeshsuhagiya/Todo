package com.example.todo;

import static com.example.todo.R.id.delete_all;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;
    MyDatabase myDB;
    ArrayList<String> task_id, task, task_description;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enables edge-to-edge display
        setContentView(R.layout.activity_main);

        // Handle window insets for immersive UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.rcv);
        addButton = findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Intent intent = new Intent(MainActivity.this, AddActivity.class);
                                                startActivity(intent);
                                         }
                                     });


        myDB = new MyDatabase(MainActivity.this);
        task_id = new ArrayList<>();
        task = new ArrayList<>();
        task_description = new ArrayList<>();

        storeDataInArrays(); // Load data from database

        customAdapter = new CustomAdapter(MainActivity.this, this, task_id, task, task_description);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 ) {
           recreate();
        }
    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task.add(cursor.getString(1));
                task_description.add(cursor.getString(2));
            }
        }
        cursor.close(); // Close the cursor to free up resources
    }

}