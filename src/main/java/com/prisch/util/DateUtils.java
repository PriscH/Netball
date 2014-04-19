package com.prisch.util;

import android.text.format.DateFormat;

import java.util.Date;

public class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm";

    public static String formatDate(Date date) {
        CharSequence formattedDate = DateFormat.format(DATE_FORMAT, date);
        return formattedDate.toString();
    }

}
