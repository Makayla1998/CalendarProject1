package com.example.project2;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity
{

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbManager = new DatabaseManager(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void add(View v)
    {
        EditText priceET = findViewById(R.id.input_price);
        String priceString = priceET.getText().toString();
        double price = Double.parseDouble(priceString);

        EditText nameET = findViewById(R.id.input_name);
        String nameString = nameET.getText().toString();

        Calendar calendar = new Calendar(0, nameString, price);
        dbManager.insertCalendar(calendar);

        Toast.makeText(this, nameString, Toast.LENGTH_LONG).show();

        priceET.setText("");
        priceET.setText("");
    }


    public void goBack(View v)
    {
        this.finish();
    }
}