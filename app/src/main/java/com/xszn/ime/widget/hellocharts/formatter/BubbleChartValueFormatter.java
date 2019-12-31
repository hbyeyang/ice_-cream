package com.xszn.ime.widget.hellocharts.formatter;


import com.xszn.ime.widget.hellocharts.model.BubbleValue;

public interface BubbleChartValueFormatter {

    public int formatChartValue(char[] formattedValue, BubbleValue value);
}
