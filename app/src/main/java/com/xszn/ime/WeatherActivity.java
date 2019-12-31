package com.xszn.ime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xszn.ime.utils.CommonUtils;
import com.xszn.ime.widget.hellocharts.gesture.ContainerScrollType;
import com.xszn.ime.widget.hellocharts.model.Axis;
import com.xszn.ime.widget.hellocharts.model.AxisValue;
import com.xszn.ime.widget.hellocharts.model.Line;
import com.xszn.ime.widget.hellocharts.model.LineChartData;
import com.xszn.ime.widget.hellocharts.model.PointValue;
import com.xszn.ime.widget.hellocharts.model.ValueShape;
import com.xszn.ime.widget.hellocharts.model.Viewport;
import com.xszn.ime.widget.hellocharts.view.LineChartView;
import com.xszn.ime.widget.weatherview.AirLevel;
import com.xszn.ime.widget.weatherview.WeatherItemView;
import com.xszn.ime.widget.weatherview.WeatherModel;
import com.xszn.ime.widget.weatherview.WeatherView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.xszn.ime
 * @class describe
 * @time 2019-12-30 15:24
 * @change
 * @chang time
 * @class describe
 */
public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.tv_temp)
    TextView mTvTemp;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.tv_weather)
    TextView mTvWeather;
    @BindView(R.id.img_weather)
    ImageView mImgWeather;
    @BindView(R.id.tv_weather_detail)
    TextView mTvWeatherDetail;
    @BindView(R.id.tv_next_day)
    TextView mTvNextDay;
    @BindView(R.id.tv_next_temp_range)
    TextView mTvNextTempRange;
    @BindView(R.id.tv_next_weather)
    TextView mTvNextWeather;
    @BindView(R.id.tv_next_weather_icon)
    ImageView mTvNextWeatherIcon;
    @BindView(R.id.rl_tomorrow_weather)
    RelativeLayout mRlTomorrowWeather;
    @BindView(R.id.tv_last_day)
    TextView mTvLastDay;
    @BindView(R.id.tv_last_temp_range)
    TextView mTvLastTempRange;
    @BindView(R.id.tv_last_weather)
    TextView mTvLastWeather;
    @BindView(R.id.tv_last_weather_icon)
    ImageView mTvLastWeatherIcon;
    @BindView(R.id.rl_last_weather)
    RelativeLayout mRlLastWeather;
    @BindView(R.id.lineView)
    LineChartView mLineView;
    @BindView(R.id.weather_view)
    WeatherView mWeatherView;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;

    public static void launch(Context context) {
        Intent intent = new Intent(context, WeatherActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        initViwe();
    }

    private void initViwe() {
        drawLine();
        //填充天气数据
        mWeatherView.setList(generateData());

        //画折线
        //weatherView.setLineType(ZzWeatherView.LINE_TYPE_DISCOUNT);
        //画曲线(已修复不圆滑问题)
        mWeatherView.setLineType(WeatherView.LINE_TYPE_CURVE);

        //设置线宽
        mWeatherView.setLineWidth(2f);

        //设置一屏幕显示几列(最少3列)
        try {
            mWeatherView.setColumnNumber(6);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置白天和晚上线条的颜色
        mWeatherView.setDayAndNightLineColor(Color.parseColor("#E4AE47"), Color.parseColor("#58ABFF"));

        //点击某一列
        mWeatherView.setOnWeatherItemClickListener(new WeatherView.OnWeatherItemClickListener() {
            @Override
            public void onItemClick(WeatherItemView itemView, int position, WeatherModel weatherModel) {
                //Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void drawLine() {
        CommonUtils.solveScrollConflict(mLineView, mScrollView);
        String[] lineLabels = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00",
                "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
                "18:00", "19:00", "20:00", "21:00", "22:00", "23:00",};
        int maxNumberOfLines = 1;
        int numberOfPoints = lineLabels.length;
        ValueShape shape = ValueShape.CIRCLE;
        float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 10f;
            }
        }


        List<Line> lines = new ArrayList<Line>();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < maxNumberOfLines; ++i) {
            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Line line = new Line(values);
            line.setColor(getResources().getColor(R.color.white));

            line.setShape(shape);
            line.setPointRadius(3);
            line.setStrokeWidth(1);
            line.setCubic(false);
            line.setFilled(false);
            line.setHasLabels(true);
            line.setHasLabelsOnlyForSelected(false);
            line.setHasLines(true);
            line.setHasPoints(true);
            line.setCubic(true);
            //line.setPointColor(R.color.transparent);
            line.setHasGradientToTransparent(true);
//            if (pointsHaveDifferentColor){
//                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
//            }
            lines.add(line);

        }

        LineChartData data = new LineChartData(lines);
        for (int i = 0; i < numberOfPoints; i++) {
            axisValues.add(new AxisValue(i).setLabel(lineLabels[i]));
        }
        Axis axisX = new Axis(axisValues).setMaxLabelChars(5);
        axisX.setTextColor(getResources().getColor(R.color.white))
                .setTextSize(10).setHasSeparationLineColor(Color.parseColor("#28ffffff"))
                .setHasTiltedLabels(true).setHasSeparationLine(true);
        data.setAxisXBottom(axisX);
        Axis axisY = new Axis().setHasSeparationLine(false).setMaxLabelChars(3)
                .setTextColor(getResources().getColor(R.color.transparent))
                .setTextSize(11).setHasLines(true).setLineColor(Color.parseColor("#28ffffff"))
                .setHasTiltedLabels(false);
        data.setAxisYLeft(axisY);
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        mLineView.setLineChartData(data);

        mLineView.setZoomEnabled(false);
        mLineView.setScrollEnabled(true);
        mLineView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        mLineView.setValueSelectionEnabled(true);//点击折线点可以显示label
        // Reset viewport height range to (0,100)
        mLineView.setViewportCalculationEnabled(false);

        //让布局能够水平滑动要设置setCurrentViewport比setMaximumViewport小
        final Viewport v = new Viewport(mLineView.getMaximumViewport());
        float min, max;
        min = max = randomNumbersTab[0][0];
        for (int i = 0; i < numberOfPoints; i++) {
            if (randomNumbersTab[0][i] > max)   // 判断最大值
                max = randomNumbersTab[0][i];
            if (randomNumbersTab[0][i] < min)   // 判断最小值
                min = randomNumbersTab[0][i];
        }
        v.bottom = (int) min - 20;
        v.top = (int) max + 20;
        v.left = 0;
        v.right = numberOfPoints - 1 + 0.5f;
        mLineView.setMaximumViewport(v);
        v.left = 0;
        v.right = Math.min(7.5f, numberOfPoints - 1 + 0.5f);
        mLineView.setCurrentViewport(v);
    }

    private List<WeatherModel> generateData() {
        List<WeatherModel> list = new ArrayList<WeatherModel>();
        WeatherModel model = new WeatherModel();
        model.setDate("12/07");
        model.setWeek("昨天");
        model.setDayWeather("大雪");
        model.setDayTemp(10);
        model.setNightTemp(5);
        model.setNightWeather("晴");
        model.setWindOrientation("西南风");
        model.setWindLevel("3级");
        model.setAirLevel(AirLevel.EXCELLENT);
        list.add(model);

        WeatherModel model1 = new WeatherModel();
        model1.setDate("12/08");
        model1.setWeek("今天");
        model1.setDayWeather("晴");
        model1.setDayTemp(8);
        model1.setNightTemp(5);
        model1.setNightWeather("晴");
        model1.setWindOrientation("西南风");
        model1.setWindLevel("3级");
        model1.setAirLevel(AirLevel.HIGH);
        list.add(model1);

        WeatherModel model2 = new WeatherModel();
        model2.setDate("12/09");
        model2.setWeek("明天");
        model2.setDayWeather("晴");
        model2.setDayTemp(9);
        model2.setNightTemp(8);
        model2.setNightWeather("晴");
        model2.setWindOrientation("东南风");
        model2.setWindLevel("3级");
        model2.setAirLevel(AirLevel.POISONOUS);
        list.add(model2);

        WeatherModel model3 = new WeatherModel();
        model3.setDate("12/10");
        model3.setWeek("周六");
        model3.setDayWeather("晴");
        model3.setDayTemp(12);
        model3.setNightTemp(9);
//        model3.setDayPic(R.drawable.w0);
//        model3.setNightPic(R.drawable.w1);
        model3.setNightWeather("晴");
        model3.setWindOrientation("东北风");
        model3.setWindLevel("3级");
        model3.setAirLevel(AirLevel.GOOD);
        list.add(model3);

        WeatherModel model4 = new WeatherModel();
        model4.setDate("12/11");
        model4.setWeek("周日");
        model4.setDayWeather("多云");
        model4.setDayTemp(13);
        model4.setNightTemp(7);
        model4.setNightWeather("多云");
        model4.setWindOrientation("东北风");
        model4.setWindLevel("3级");
        model4.setAirLevel(AirLevel.LIGHT);
        list.add(model4);

        WeatherModel model5 = new WeatherModel();
        model5.setDate("12/12");
        model5.setWeek("周一");
        model5.setDayWeather("多云");
        model5.setDayTemp(17);
        model5.setNightTemp(8);
        model5.setNightWeather("多云");
        model5.setWindOrientation("西南风");
        model5.setWindLevel("3级");
        model5.setAirLevel(AirLevel.LIGHT);
        list.add(model5);

        WeatherModel model6 = new WeatherModel();
        model6.setDate("12/13");
        model6.setWeek("周二");
        model6.setDayWeather("晴");
        model6.setDayTemp(13);
        model6.setNightTemp(6);
        model6.setNightWeather("晴");
        model6.setWindOrientation("西南风");
        model6.setWindLevel("3级");
        model6.setAirLevel(AirLevel.POISONOUS);
        list.add(model6);

        WeatherModel model7 = new WeatherModel();
        model7.setDate("12/14");
        model7.setWeek("周三");
        model7.setDayWeather("晴");
        model7.setDayTemp(19);
        model7.setNightTemp(10);
        model7.setNightWeather("晴");
        model7.setWindOrientation("西南风");
        model7.setWindLevel("3级");
        model7.setAirLevel(AirLevel.POISONOUS);
        list.add(model7);

        WeatherModel model8 = new WeatherModel();
        model8.setDate("12/15");
        model8.setWeek("周四");
        model8.setDayWeather("晴");
        model8.setDayTemp(22);
        model8.setNightTemp(4);
        model8.setNightWeather("晴");
        model8.setWindOrientation("西南风");
        model8.setWindLevel("3级");
        model8.setAirLevel(AirLevel.POISONOUS);
        list.add(model8);

        return list;
    }
}
