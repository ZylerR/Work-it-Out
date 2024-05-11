package com.example.project2_triddle4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class editExistingWorkout extends AppCompatActivity {
    String clicked;
    int i;
    EditText nameText;
    EditText repsText;
    EditText setsText;
    EditText weightText;
    EditText notesText;
    DatabaseOpenHelper dbHelper;
    ArrayList<String> id, name, reps, sets, weight, notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_existing_workout);
        Intent intent = getIntent();
        clicked = intent.getStringExtra("workoutClicked");
        dbHelper = new DatabaseOpenHelper(editExistingWorkout.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        reps = new ArrayList<>();
        sets = new ArrayList<>();
        weight = new ArrayList<>();
        notes = new ArrayList<>();
        storeData();
        System.out.println("Rep val: " + reps.get(0));
        i = -1;
        boolean found = false;
        while(!found)
        {
            i += 1;
            if(name.get(i).equals(clicked))
            {
                //NAME FOUND!!!
                found = true;
            }
        }
        System.out.println("The position of the clicked thingy is: " + i);
        nameText = findViewById(R.id.nameVal);
        setsText = findViewById(R.id.setVal);
        repsText = findViewById(R.id.repVal);
        weightText = findViewById(R.id.weightVal);
        notesText = findViewById(R.id.notesVal);
        nameText.setHint(name.get(i));
        repsText.setHint(reps.get(i));
        setsText.setHint(sets.get(i));
        weightText.setHint(weight.get(i));
        notesText.setHint(notes.get(i));
    }

    void storeData()
    {
        Cursor cursor = dbHelper.readAll();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "Database is empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                reps.add(cursor.getString(2));
                sets.add(cursor.getString(3));
                weight.add(cursor.getString(4));
                notes.add(cursor.getString(5));
            }
        }
    }
    public void onCancelClick(View view)
    {
        finish();
    }
    public void onSaveClick(View view)
    {
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(editExistingWorkout.this);
        String nameUpdate, repsUpdate, setsUpdate, weightUpdate, notesUpdate;
        boolean shouldContinue = true;
        for(int i = 0; i < name.size(); i++)
        {
            if(!name.get(i).equals(clicked) && name.get(i).equals(nameText.getText().toString().trim()))
            {
                Toast.makeText(this, "Cannot create duplicate exercise names!", Toast.LENGTH_SHORT).show();
                shouldContinue = false;
            }
        }
        if(shouldContinue)
        {
            if(nameText.getText().toString().trim().equals(""))
            {
                nameUpdate = name.get(i);
            }
            else
            {
                nameUpdate = nameText.getText().toString().trim();
            }
            if(repsText.getText().toString().trim().equals(""))
            {
                repsUpdate = reps.get(i);
            }
            else
            {
                repsUpdate = repsText.getText().toString().trim();
            }
            if(setsText.getText().toString().trim().equals(""))
            {
                setsUpdate = sets.get(i);
            }
            else
            {
                setsUpdate = setsText.getText().toString().trim();
            }
            if(weightText.getText().toString().trim().equals(""))
            {
                weightUpdate = weight.get(i);
            }
            else
            {
                weightUpdate = weightText.getText().toString().trim();
            }
            if(notesText.getText().toString().trim().equals(""))
            {
                notesUpdate = notes.get(i);
            }
            else
            {
                notesUpdate = notesText.getText().toString().trim();
            }

            dbHelper.update(clicked, nameUpdate, repsUpdate, setsUpdate, weightUpdate, notesUpdate);
            finish();
        }
    }
}