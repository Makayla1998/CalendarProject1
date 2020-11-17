package com.example.project2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "CandyDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CANDY = "candy";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PRICE = "price";

    public DatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        String sqlCreate = "create table " + TABLE_CANDY + "( " + ID;
        sqlCreate += " interger primary key autoincrement, " + NAME;
        sqlCreate += " text, " + PRICE + " real )" ;

        db.execSQL( sqlCreate );
    }

    public void insertCalendar(Calendar calendar)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_CANDY + " values (null, '" + calendar.getName() + "', "
                + calendar.getPrice() + " )";
        db.execSQL(sqlInsert);
        db.close();
    }

    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
    {
        // Drop old table if it exists
        db.execSQL( "drop table if exists " + TABLE_CANDY );
        // Re-create tables
        onCreate( db );
    }

    public ArrayList<Calendar> selectAll()
    {
        String sqlQuery = "select * from " + TABLE_CANDY;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<Calendar> calendars = new ArrayList<Calendar>();

        while(cursor.moveToNext())
        {
            Calendar currentCalendar = new Calendar(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getDouble(2));
            calendars.add(currentCalendar);
        }
        db.close();

        return calendars;
    }

    public void deleteById(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "delete from " + TABLE_CANDY + " where " + ID + " = " + id;

        db.execSQL(sqlDelete);
        db.close();
    }

    public void updateByID(int id, String name, double price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlUpdate = "update " + TABLE_CANDY + " set " + NAME + " = '" + name + "' , " + PRICE + " = '" + price + "' where " + ID + " = " + id;

        db.execSQL(sqlUpdate);
        db.close();
    }
}