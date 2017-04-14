package com.example.laci.listazas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Laci on 2017. 03. 29..
 */

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabasHelper;

    private ListView mListView;
    private SimpleCursorAdapter dataAdapter;
    private Button btn_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabasHelper = new DatabaseHelper(this);
        btn_add = (Button) findViewById(R.id.btn_hozzaad);

        //plvw();
        //populateListView();
        ListAllItems();
    }

    private void ListAllItems(){

        TextView Osszeg = (TextView)findViewById(R.id.tv_all_price);
        int Osszeg_from_db = mDatabasHelper.getOsszar();
        Osszeg.setText("Összeg:            " + Osszeg_from_db + " Forint");
        mDatabasHelper.allprice();

        Cursor cursor = mDatabasHelper.getAllRows();
        String[] columns = mDatabasHelper.SOME_KEYS;
        int[] to = new int[]{
                R.id.tv_nev,
                R.id.tv_barcode,
                R.id.tv_piece,
                R.id.tv_ar
        };


        dataAdapter = new SimpleCursorAdapter(this,R.layout.custom_row,cursor,columns,to,0);
        mListView.setAdapter(dataAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                //toastMessage(name);
                Cursor data = mDatabasHelper.getItemID(name);
                int itemID = -1;
                while(data.moveToFirst()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);

                    startActivity(editScreenIntent);
                }else{
                    toastMessage("no id associated with name");
                }
            }
        });
    }

    public void onClick(View view){
        Intent intent = new Intent(ListDataActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
