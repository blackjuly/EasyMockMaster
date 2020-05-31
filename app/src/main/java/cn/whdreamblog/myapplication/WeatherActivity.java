package cn.whdreamblog.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/5/16 20:50
 * desc : The class is used for show easy mock demo
 */
public class WeatherActivity extends AppCompatActivity implements WeatherContract.View {
    private WeatherContract.Presenter presenter;
    private TextView tv;
    private LinearLayout indicator;
    private RadioGroup rgCities;
    private Button btnGetWeather;
    private RadioButton rbShanghai;
    private RadioButton rbBeijing;
    private RadioButton rbChangZhou;
    private Button btnGetSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new WeatherPresenter(this);
        initView();
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            findViewById(R.id.indicator).setVisibility(View.VISIBLE);
            return;
        }
        findViewById(R.id.indicator).setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showInfo(String weatherInfo) {
        tv.setText(weatherInfo);
    }

    private void initView() {
        indicator = findViewById(R.id.indicator);
        rbShanghai = findViewById(R.id.rb_shanghai);
        rbBeijing = findViewById(R.id.rb_beijing);
        rbChangZhou = findViewById(R.id.rb_changzhou);
        rgCities = findViewById(R.id.rg_cities);
        rgCities.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String tag = (String) findViewById(checkedId).getTag();
                presenter.setCurrentCity(tag);
            }
        });
        btnGetWeather = findViewById(R.id.btn_get_weather);
        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start();
            }
        });
        tv = findViewById(R.id.tv);
        btnGetSuggestion = findViewById(R.id.btn_get_Suggestion);
        btnGetSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getSuggestion();
            }
        });
    }
}
