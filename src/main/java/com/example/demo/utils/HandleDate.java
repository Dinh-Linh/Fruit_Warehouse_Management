package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HandleDate {
    public String FormatDate(Date date){
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
    }
}
