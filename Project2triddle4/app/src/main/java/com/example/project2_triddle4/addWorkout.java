package com.example.project2_triddle4;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class addWorkout extends AppCompatActivity {

    EditText nameWork;
    EditText setsWork;
    EditText repsWork;
    EditText weightWork;
    EditText notesWork;
    DatabaseOpenHelper dbHelper;
    ArrayList<String> id, name, reps, sets, weight, notes;
    //DatabaseOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        nameWork = findViewById(R.id.nameVal);
        setsWork = findViewById(R.id.setVal);
        repsWork =  findViewById(R.id.repVal);
        weightWork = findViewById(R.id.weightVal);
        notesWork = findViewById(R.id.notesVal);
        dbHelper = new DatabaseOpenHelper(addWorkout.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        reps = new ArrayList<>();
        sets = new ArrayList<>();
        weight = new ArrayList<>();
        notes = new ArrayList<>();
        storeData();
    }

    public void onCancelClick(View view)
    {
        finish();
    }
    public void onSaveClick(View view)
    {
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(addWorkout.this);
        String repsUpdate, setsUpdate, weightUpdate;
        if(nameWork.getText().toString().trim().equals(""))
        {
            Toast.makeText(this, "Must name your exercise!", Toast.LENGTH_SHORT).show();
        }
        else {
            boolean shouldContinue = true;
            for(int i = 0; i < name.size(); i++)
            {
                if(name.get(i).equals(nameWork.getText().toString().trim()))
                {
                    Toast.makeText(this, "Cannot create duplicate exercise names!", Toast.LENGTH_SHORT).show();
                    shouldContinue = false;
                }
            }
            if(shouldContinue)
            {
                if(repsWork.getText().toString().trim().equals(""))
                {
                    repsUpdate = "0";
                }
                else
                {
                    repsUpdate = repsWork.getText().toString().trim();
                }
                if(setsWork.getText().toString().trim().equals(""))
                {
                    setsUpdate = "0";
                }
                else
                {
                    setsUpdate = setsWork.getText().toString().trim();
                }
                if(weightWork.getText().toString().trim().equals(""))
                {
                    weightUpdate = "0";
                }
                else
                {
                    weightUpdate = weightWork.getText().toString();
                }
                dbHelper.addWorkout(nameWork.getText().toString(),
                        Integer.parseInt(repsUpdate),
                        Integer.parseInt(setsUpdate),
                        Integer.parseInt(weightUpdate),
                        notesWork.getText().toString());
                finish();
            }

        }
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
}