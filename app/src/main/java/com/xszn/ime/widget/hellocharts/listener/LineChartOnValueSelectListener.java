package com.xszn.ime.widget.hellocharts.listener;


import com.xszn.ime.widget.hellocharts.model.PointValue;

public interface LineChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int lineIndex, int pointIndex, PointValue value);

}
