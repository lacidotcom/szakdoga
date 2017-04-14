package com.example.laci.listazas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private EditText editText, eT_barcode, eT_piece,eT_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        eT_barcode = (EditText) findViewById(R.id.editText_vonalkod);
        eT_piece = (EditText) findViewById(R.id.editText_darab);
        eT_price = (EditText) findViewById(R.id.editText_darabar);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String newEntry = editText.getText().toString();
                int entry_vonalK = 0;
                if("".equals(eT_barcode.getText().toString())){
                    entry_vonalK = 0;
                }else{
                    entry_vonalK = Integer.parseInt(eT_barcode.getText().toString());
                }
                int entry_darab = 0;
                if("".equals(eT_piece.getText().toString())){
                    entry_darab = 0;
                }else{
                    entry_darab = Integer.parseInt(eT_piece.getText().toString());
                }
                int entry_darab_ar = 0;
                if("".equals(eT_price.getText().toString())){
                    entry_darab_ar = 0;
                }else{
                    entry_darab_ar = Integer.parseInt(eT_price.getText().toString());
                }
                if(editText.length() != 0 && entry_vonalK > 0 && entry_darab > 0 && entry_darab_ar > 0){
                    AddData(newEntry,entry_vonalK,entry_darab,entry_darab_ar);
                    editText.setText("");
                    eT_barcode.setText("");
                    eT_piece.setText("");
                    eT_price.setText("");

                }else{
                    Toast.makeText(MainActivity.this,"You must put something in all field",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }


    public void AddData(String newEntry, int entry_vonalK, int entry_darab,int entry_darab_ar){
        boolean insertData = mDatabaseHelper.addData(newEntry,entry_vonalK,entry_darab,entry_darab_ar);
        if(insertData){
            Toast.makeText(MainActivity.this,"data successfully inserted",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this,"something went wrong",Toast.LENGTH_LONG).show();
        }
    }


}
