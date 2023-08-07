package com.example.todolistapp.data;

import androidx.room.TypeConverter;

public class Converter {
    @TypeConverter
    public static Boolean fromInt(int value) {
        return value == 1 ? true : false;
    }

    @TypeConverter
    public static int fromBoolean(Boolean value) {
        return value ? 1 : 0;
    }

}
