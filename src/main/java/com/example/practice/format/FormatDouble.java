package com.example.practice.format;

import java.text.DecimalFormat;

public class FormatDouble {
    public static final DecimalFormat df = new DecimalFormat("#.######");

    public static String format(double value) {
        return df.format(value);
    }
}
