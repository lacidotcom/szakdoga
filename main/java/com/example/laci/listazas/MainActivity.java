package com.example.laci.listazas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData, btn_barcode_scan;
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
        btn_barcode_scan = (Button) findViewById(R.id.btn_barcode_scan);

        btn_barcode_scan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String newEntry = editText.getText().toString();
                String entry_vonalK = eT_barcode.getText().toString();
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
                if(editText.length() != 0 && entry_vonalK.length() != 0 && entry_darab > 0 && entry_darab_ar > 0){
                    AddData(newEntry,entry_vonalK,entry_darab,entry_darab_ar);
                    editText.setText("");
                    eT_barcode.setText("");
                    eT_piece.setText("");
                    eT_price.setText("");

                }else{
                    Toast.makeText(MainActivity.this,"Muszáj minden mezőt kitölteni",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
                mDatabaseHelper.allprice();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        if(scanResult != null){
            String re = scanResult.getContents();
            eT_barcode.setText(re);
        }
    }

    public void AddData(String newEntry, String entry_vonalK, int entry_darab,int entry_darab_ar){
        boolean insertData = mDatabaseHelper.addData(newEntry,entry_vonalK,entry_darab,entry_darab_ar);
        if(insertData){
            Toast.makeText(MainActivity.this,"Sikeresen hozzaadva",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this,"Hiba",Toast.LENGTH_LONG).show();
        }
    }


}
