package com.example.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {

    EditText updatetaskinput, updatetaskdescription;
    Button updatebutton , deletebutton;

    String id, task, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        updatetaskinput = findViewById(R.id.updatetaskinput);
        updatetaskdescription = findViewById(R.id.updatetaskdescription);
        updatebutton = findViewById(R.id.updatebutton);
        deletebutton = findViewById(R.id.deletebutton);

        getAndSetIntentData();

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase myDB = new MyDatabase(UpdateActivity.this);

                // Get updated values from input fields
                String updatedTask = updatetaskinput.getText().toString().trim();
                String updatedDesc = updatetaskdescription.getText().toString().trim();

                // Update task in database
                boolean isUpdated = myDB.updateData(id, updatedTask, updatedDesc);
                if (isUpdated) {
                    Toast.makeText(UpdateActivity.this, "Task Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity after update
                } else {
                    Toast.makeText(UpdateActivity.this, "Failed to Update Task", Toast.LENGTH_SHORT).show();
                }
            }

        });
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("task") && getIntent().hasExtra("desc")) {
            id = getIntent().getStringExtra("id");
            task = getIntent().getStringExtra("task");
            desc = getIntent().getStringExtra("desc");

            updatetaskinput.setText(task);
            updatetaskdescription.setText(desc);
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + task + "?");
        builder.setMessage("Are you sure you want to delete "+ task + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabase myDB = new MyDatabase(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish(); // Close the activity after deletion
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });

        builder.create().show(); // This line was missing
    }


    }

