package com.xszn.ime.widget.hellocharts.listener;


import com.xszn.ime.widget.hellocharts.model.PointValue;
import com.xszn.ime.widget.hellocharts.model.SubcolumnValue;

public interface ComboLineColumnChartOnValueSelectListener extends OnValueDeselectListener {

    public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value);

    public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value);

}
