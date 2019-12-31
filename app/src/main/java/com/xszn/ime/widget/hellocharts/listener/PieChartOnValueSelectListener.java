package com.xszn.ime.widget.hellocharts.listener;


import com.xszn.ime.widget.hellocharts.model.SliceValue;

public interface PieChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int arcIndex, SliceValue value);

}
