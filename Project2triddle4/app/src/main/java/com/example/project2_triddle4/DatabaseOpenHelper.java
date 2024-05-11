package com.example.project2_triddle4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    final static String TABLE_NAME = "workouts";
    final static String NAMES = "name";
    final static String _ID = "_id";
    final static String REPS = "reps";
    final static String SETS = "sets";
    final static String WEIGHT = "weight";
    final static String NOTES = "notes";
    final private static String NAME = "workout.db";
    final public String[] columns = {_ID, NAMES, REPS, SETS, WEIGHT, NOTES};
    final private static Integer VERSION = 2;
    final private Context context;
    private SQLiteDatabase db;
    public DatabaseOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
        System.out.println("RUNS@@J@JIJ@??");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CMD = "CREATE TABLE " + TABLE_NAME + " (" + _ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAMES + " TEXT NOT NULL, " + REPS + " INTEGER NOT NULL, "
                + SETS + " INTEGER NOT NULL, " + WEIGHT + " INTEGER NOT NULL, "
                + NOTES + " TEXT NOT NULL)";
        db.execSQL(CREATE_CMD);
        System.out.println("RUNS??");
        ContentValues values = new ContentValues();
        values.put(NAMES,"Running");
        values.put(REPS,1);
        values.put(SETS,1);
        values.put(WEIGHT,150);
        values.put(NOTES, "Please work bro :)");
        db.insert(TABLE_NAME,null,values);
        values.clear();
        values.put(NAMES,"Jumping Jacks");
        values.put(REPS,20);
        values.put(SETS,2);
        values.put(WEIGHT,0);
        values.put(NOTES, "Please");
        db.insert(TABLE_NAME,null,values);
        values.clear();
        values.put(NAMES,"Pushups");
        values.put(REPS,15);
        values.put(SETS,2);
        values.put(WEIGHT,150);
        values.put(NOTES, "Maybe?");
        db.insert(TABLE_NAME,null,values);
        values.clear();
        values.put(NAMES,"Situps");
        values.put(REPS,20);
        values.put(SETS,1);
        values.put(WEIGHT,0);
        values.put(NOTES, "Please!!");
        db.insert(TABLE_NAME,null,values);
        values.clear();
        values.put(NAMES,"Russian Twists");
        values.put(REPS,100);
        values.put(SETS,1);
        values.put(WEIGHT,0);
        values.put(NOTES, "Please work bro :)))))))");
        db.insert(TABLE_NAME,null,values);
        values.clear();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addWorkout(String name, int reps, int sets, int weight, String notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAMES, name);
        cv.put(REPS, reps);
        cv.put(SETS, sets);
        cv.put(WEIGHT, weight);
        cv.put(NOTES, notes);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1)
        {
            Toast.makeText(context, "FAILED!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "SUCCEEDED!!!", Toast.LENGTH_SHORT).show();
        }
        cv.clear();


    }

    public Cursor readAll()
    {
        Cursor newCursor = null;
        newCursor = this.getWritableDatabase().query("workouts", new String[] {"_id", "name", "reps", "sets", "weight", "notes"}, null, null, null, null, null);
        return newCursor;
    }

    public void update(String oldName, String newName, String newReps, String newSets, String newWeight, String newNotes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAMES, newName);
        values.put(REPS, newReps);
        values.put(SETS, newSets);
        values.put(WEIGHT, newWeight);
        values.put(NOTES, newNotes);
        int status = db.update(TABLE_NAME, values, NAMES + "=?", new String[] { oldName });
        if (status == 0) Toast.makeText(context.getApplicationContext(), "No updates needed",Toast.LENGTH_SHORT).show();
        values.clear();
        db.close();
    }
    public void delete(long toDelete) {
        System.out.println("STRING TO DELETE: " + String.valueOf(toDelete));
        long deleteItem = this.getWritableDatabase().delete(TABLE_NAME, "_id=?", new String[]{String.valueOf(toDelete)});
    }
}
