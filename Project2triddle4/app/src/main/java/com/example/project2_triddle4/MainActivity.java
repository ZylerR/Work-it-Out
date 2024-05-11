package com.example.project2_triddle4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    SimpleCursorAdapter myAdapter;

    DatabaseOpenHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void doAWorkoutClick(View view)
    {
        Intent intent = new Intent(this, doWorkout.class);
        startActivity(intent);
    }
    public void editWorkoutClick(View view)
    {
        Intent intent = new Intent(this, editWorkout.class);
        startActivity(intent);
    }
}