package com.example.project2;

import android.content.Context;

public class CalendarButton extends androidx.appcompat.widget.AppCompatButton
{
    private Calendar calendar;

    public CalendarButton(Context context, Calendar newCalendar)
    {
        super( context );
        calendar = newCalendar;
    }

    public double getPrice()
    {
        return calendar.getPrice();
    }
}
