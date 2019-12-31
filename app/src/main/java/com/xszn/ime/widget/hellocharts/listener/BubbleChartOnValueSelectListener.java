package com.xszn.ime.widget.hellocharts.listener;


import com.xszn.ime.widget.hellocharts.model.BubbleValue;

public interface BubbleChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int bubbleIndex, BubbleValue value);

}
