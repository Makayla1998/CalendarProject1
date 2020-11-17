package com.example.project2;

import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    //dbManager;
    private DatabaseManager dbManager;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        dbManager = new DatabaseManager(this);
        updateView();
        // dbManager and update view
    }

    // Build a View dynamically with all the calendar
    public void updateView( )
    {
        // ArrayList for calendars
        ArrayList<Calendar> calendars = dbManager.selectAll();

        if( calendars.size() > 0)
        {
            // create ScrollView and GridLayout
            ScrollView scrollView = new ScrollView( this );
            GridLayout grid = new GridLayout( this );
            grid.setRowCount( calendars.size( ) );
            grid.setColumnCount( 4 );

            // create arrays of components
            TextView[] ids = new TextView[calendars.size( )];
            EditText[][] namesAndPrices = new EditText[calendars.size( )][2];
            Button[] buttons = new Button[calendars.size( )];
            ButtonHandler bh = new ButtonHandler( );

            // retrieve width of screen
            Point size = new Point( );
            getWindowManager( ).getDefaultDisplay( ).getSize( size );
            int width = size.x;

            int i = 0;

            for (Calendar calendar : calendars)
            {
                // create the TextView for the calendar's id
                ids[i] = new TextView( this );
                ids[i].setGravity( Gravity.CENTER );
                ids[i].setText( "" + (i+1) );

                // create the two EditTexts for the calendar's name and price
                namesAndPrices[i][0] = new EditText( this );
                namesAndPrices[i][1] = new EditText( this );
                namesAndPrices[i][0].setText( calendar.getName( ) );
                namesAndPrices[i][1].setText( "" + calendar.getPrice( ) );
                namesAndPrices[i][1].setInputType( InputType.TYPE_CLASS_NUMBER );
                namesAndPrices[i][0].setId( 10 * calendar.getId( ) );
                namesAndPrices[i][1].setId( 10 * calendar.getId( ) + 1 );

                // create the button
                buttons[i] = new Button( this );
                buttons[i].setText( "Update" );
                buttons[i].setId( calendar.getId( ) );

                // set up event handling
                buttons[i].setOnClickListener( bh );

                final ImageButton b1 = new ImageButton( this );


                // add the elements to grid
                grid.addView( b1, width / 10 , ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( namesAndPrices[i][0], ( int ) ( width * .4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( namesAndPrices[i][1], ( int ) ( width * .25 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( buttons[i], ( int ) ( width * .25 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );

                i++;
            }

            // create a back button
            Button backButton = new Button(this);
            backButton.setText(R.string.button_back);
            TextView emptyText = new TextView(this);
            grid.addView(emptyText,( int ) ( width / 10 ), ViewGroup.LayoutParams.WRAP_CONTENT );
            grid.addView( backButton, ( int ) ( width * .15 ),ViewGroup.LayoutParams.WRAP_CONTENT );

            backButton.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v) {
                    UpdateActivity.this.finish();
                }
            });

            scrollView.addView( grid );
            setContentView( scrollView );
        }
    }

    private class ButtonHandler implements View.OnClickListener
    {
        public void onClick( View v )
        {
            // retrieve name and price of the calendar
            int calendarID = v.getId( );
            EditText nameET = ( EditText ) findViewById( 10 * calendarID );
            EditText priceET = ( EditText ) findViewById( 10 * calendarID + 1 );
            String name = nameET.getText().toString();
            String priceString = priceET.getText().toString();

            // update event in database and update screen
            try
            {
                double price = Double.parseDouble(priceString);
                dbManager.updateByID(calendarID, name, price);
                Toast.makeText(UpdateActivity.this, "Item has been updated", Toast.LENGTH_SHORT).show();

                updateView();
            } catch( NumberFormatException exc)
            {
                Toast.makeText(UpdateActivity.this, "Error found with input", Toast.LENGTH_SHORT).show();

            }
        }
    }
}