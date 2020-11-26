package com.example.waterrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    private TextView textView;
    private Button button2;

    private DBHelper _db = new DBHelper(this, "WATER_RECORD", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeElements();
        addDelta();
        viewTotal();
    }

    private void initializeElements(){
         button = (Button) findViewById(R.id.button);
         editText = (EditText) findViewById(R.id.editText);
         editText.setText("");
         textView = (TextView) findViewById(R.id.textView);
         button2 = (Button) findViewById(R.id.button2);
    }

    private void addDelta()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInDb(Double.parseDouble(editText.getText().toString()));
                double total = getTotal();
                textView.setText(Double.toString(total));
                editText.setText("");
            }
        });
    }

    private void viewTotal()
    {
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double total = getTotal();
                textView.setText(Double.toString(total));
            }
        });
    }

    private double getTotal()
    {
        double result = 0.0;
        String query = "SELECT waterCount FROM WATER_RECORD";
        Cursor cursor = _db.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.moveToNext())
            result += cursor.getDouble(cursor.getColumnIndex("waterCount"));
        return result;
    }

    protected void addInDb(double delta)
    {
        _db.getWritableDatabase().execSQL("INSERT INTO WATER_RECORD (ID, waterCount) VALUES (NULL, " + delta +")");
    }
}