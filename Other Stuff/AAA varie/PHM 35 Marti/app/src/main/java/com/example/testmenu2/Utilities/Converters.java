package com.example.testmenu2.Utilities;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date LongToDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long DateToLong(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String DateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static  Date StringToDate(String string){
        Date date= null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}