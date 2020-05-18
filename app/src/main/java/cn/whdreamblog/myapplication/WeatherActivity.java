package cn.whdreamblog.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/5/16 20:50
 * desc : The class is used for show easy mock demo
 */
public class WeatherActivity extends AppCompatActivity implements WeatherContract.View {
    private WeatherContract.Presenter presenter;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        new WeatherPresenter(this);
        presenter.start();
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active){
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
    public void showWeatherInfo(String weatherInfo) {
        tv.setText(weatherInfo);
    }
}
