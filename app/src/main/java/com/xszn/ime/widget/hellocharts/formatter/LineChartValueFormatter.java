package com.xszn.ime.widget.hellocharts.formatter;


import com.xszn.ime.widget.hellocharts.model.PointValue;

public interface LineChartValueFormatter {

    public int formatChartValue(char[] formattedValue, PointValue value);
}
