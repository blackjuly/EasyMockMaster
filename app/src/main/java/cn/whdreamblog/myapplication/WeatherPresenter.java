package cn.whdreamblog.myapplication;

import android.os.AsyncTask;

import java.io.IOException;
import java.security.SignatureException;

import cn.whdreamblog.myapplication.data.NewWorkManger;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/5/16
 * desc : The class is get weather info to show in the page
 */
public class WeatherPresenter implements WeatherContract.Presenter {
    WeatherContract.View view;
    private MyTask myTask;
    public WeatherPresenter(WeatherContract.View view) {
        this.view = view;
        view.setPresenter(this);
        myTask = new MyTask();
    }

    @Override
    public void start() {
        myTask.execute("");
    }

    @Override
    public void unSubscribe() {
        myTask.cancel(true);
    }

    private class MyTask extends AsyncTask<String,Integer,String> {
        @Override
        protected void onPreExecute() {
            view.setProgressIndicator(true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String error;
            try {
                return NewWorkManger.get().getDiaryWeatherInfo();
            } catch (IOException | SignatureException e) {
                error = e.getMessage();
                e.printStackTrace();
            }
            return error;
        }



        @Override
        protected void onPostExecute(String result) {
            view.showWeatherInfo(result);
            view.setProgressIndicator(false);
        }


        @Override
        protected void onCancelled() {
            view.setProgressIndicator(false);
        }
    }

}
