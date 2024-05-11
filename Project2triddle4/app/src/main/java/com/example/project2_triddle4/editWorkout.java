package com.example.project2_triddle4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class editWorkout extends AppCompatActivity {
    ListView mlist;
    SimpleCursorAdapter myAdapter;

    DatabaseOpenHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        mlist = (ListView) findViewById(R.id.workoutView);
        dbHelper = new DatabaseOpenHelper(this);
        Cursor c = dbHelper.readAll();
        myAdapter = new SimpleCursorAdapter(this, R.layout.datalist, c, dbHelper.columns, new int[]{R.id._id, R.id.name}, 1);//R.id._id,
        mlist.setAdapter(myAdapter);

        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if(!((TextView) view).getText().toString().contains("Done: "))
                //{
                //String doneStr = "Done: ";
                //String realStr = doneStr.concat(((TextView) view).getText().toString());
                //myAdapter.getString
                //System.out.println("String in Question: " + mlist.getItemAtPosition(position).toString());
                //System.out.println("LONG ID: " + id);
                //System.out.println("String in Question: " + myAdapter.getCursor().getString(1));
                Intent intent = new Intent(view.getContext(), editExistingWorkout.class);
                intent.putExtra("workoutClicked", myAdapter.getCursor().getString(1));
                startActivity(intent);
                /*if(!myAdapter.getCursor().getString(1).contains("Done: "))
                {
                    System.out.println("String in Question: " + myAdapter.getCursor().getString(1));
                    String addStr = "Done: ";
                    dbHelper.update(myAdapter.getCursor().getString(1), addStr.concat(myAdapter.getCursor().getString(1)));
                    Cursor updatedC = dbHelper.readAll();
                    myAdapter.swapCursor(updatedC);
                    mlist.setAdapter(myAdapter);
                }
                else
                {
                    System.out.println("String in Question: " + myAdapter.getCursor().getString(1));
                    dbHelper.update(myAdapter.getCursor().getString(1), myAdapter.getCursor().getString(1).substring(6));
                    Cursor updatedC = dbHelper.readAll();
                    myAdapter.swapCursor(updatedC);
                    mlist.setAdapter(myAdapter);
                }*/
            }
        });
        mlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                 alertView("Single Item Deletion",position, id);

                                                 return true;
                                             }
                                         }
        );
    }

    public void onAdd(View view)
    {
        Intent intent = new Intent(this, addWorkout.class);
        startActivity(intent);
    }
    private void alertView(String message ,final int position, long id ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(editWorkout.this);
        dialog.setTitle( message )
                .setIcon(R.drawable.ic_launcher_background)
                .setMessage("Are you sure you want to delete this?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }})
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        //myAdapter.remove(myAdapter.getItem(position));
                        dbHelper.delete(id);
                        Cursor updatedC = dbHelper.readAll();
                        myAdapter.swapCursor(updatedC);
                        mlist.setAdapter(myAdapter);
                    }
                }).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        Cursor updatedC = dbHelper.readAll();
        myAdapter.swapCursor(updatedC);
        mlist.setAdapter(myAdapter);
    }
}