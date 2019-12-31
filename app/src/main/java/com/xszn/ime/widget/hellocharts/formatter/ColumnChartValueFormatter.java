package com.xszn.ime.widget.hellocharts.formatter;


import com.xszn.ime.widget.hellocharts.model.SubcolumnValue;

public interface ColumnChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SubcolumnValue value);

}
