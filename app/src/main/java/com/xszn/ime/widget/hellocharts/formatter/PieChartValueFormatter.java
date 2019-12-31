package com.xszn.ime.widget.hellocharts.formatter;


import com.xszn.ime.widget.hellocharts.model.SliceValue;

public interface PieChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SliceValue value);
}
