package com.example.laci.listazas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Laci on 2017. 03. 29..
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    ///////////////////////////////

    public static final String KEY_ROWID = "ID";
    public static final int COL_ROWID = 0;

    ///////////////////////////////

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "Nemtudom";
    private static final String COL1 = "_id";
    private static final String COL2 = "name";
    private static final String COL3 = "barcode";
    private static final String COL4 = "piece";
    private static final String COL5 = "price";
    //private static final String COL6 = "allprice";

    private static final String[] ALL_KEYS = {COL1,COL2,COL3,COL4,COL5};
    public static final String[] SOME_KEYS = {COL2,COL3,COL4,COL5};

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 +
                " TEXT, " + COL3 + " INTEGER, " + COL4 + " INTEGER, " + COL5 + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, int entry_vonalK, int entry_darab,int entry_darab_ar){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,item);
        contentValues.put(COL3,entry_vonalK);
        contentValues.put(COL4,entry_darab);
        contentValues.put(COL5,entry_darab_ar);


        //Log.d(TAG, "addData: adding "+ entry_darab + " piece " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1) {
            return false;
        }else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }



    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" + " AND " + COL2 + " = '"+oldName+"'";
        Log.d(TAG,"updateName: query: "+ query);
        Log.d(TAG, "updateName: setting name to "+ newName);
        db.execSQL(query);
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "+ COL1 + " = '" + id + "'" + " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG,"deleteName: query: "+ query);
        Log.d(TAG, "deleteName: deleting "+ name + "from database");
        db.execSQL(query);
     }

    public Cursor getAllRows(){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = null;
        Cursor c = db.query(TABLE_NAME,ALL_KEYS,null,null,null,null,null);
        if(c!= null){
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getRow(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, TABLE_NAME, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
}
