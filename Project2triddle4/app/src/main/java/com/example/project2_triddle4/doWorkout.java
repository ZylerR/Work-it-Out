package com.example.project2_triddle4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class doWorkout extends AppCompatActivity {
    EditText elem;
    ListView listView;
    ArrayAdapter<String> myAdapter;
    DatabaseOpenHelper dbHelper;
    ArrayList<String> id, name, reps, sets, weight, notes;

    //ArrayList<String> bruh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_workout);
        listView = (ListView) findViewById(R.id.doWorkoutView);


        dbHelper = new DatabaseOpenHelper(doWorkout.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        reps = new ArrayList<>();
        sets = new ArrayList<>();
        weight = new ArrayList<>();
        notes = new ArrayList<>();
        storeData();
        System.out.println("Name val: " + name.get(0));
        // Param1 - context
        // Param2 - layout for the row

        myAdapter = new ArrayAdapter<String>(this, R.layout.line);

        listView.setAdapter(myAdapter);
        for(int i = 0; i < name.size(); i++)
        {
            myAdapter.add(name.get(i));
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Reps: " + reps.get(position) + " | Sets: " + sets.get(position) + " | Weight: " + weight.get(position) + " | Notes: " + notes.get(position), Toast.LENGTH_LONG).show();//((TextView) view).getText()
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                    alertView("Single Item Deletion",position);

                                                    return true;
                                                }
                                            }
        );

    }
    private void alertView(String message ,final int position ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(doWorkout.this);
        dialog.setTitle( message )
                .setIcon(R.drawable.ic_launcher_background)
                .setMessage("Are you sure you want to do this?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }})
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        myAdapter.remove(myAdapter.getItem(position));
                        name.remove(position);
                        reps.remove(position);
                        sets.remove(position);
                        weight.remove(position);
                        notes.remove(position);
                    }
                }).show();
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