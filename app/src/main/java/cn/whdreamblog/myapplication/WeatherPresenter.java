package cn.whdreamblog.myapplication;

import android.os.AsyncTask;

import java.io.IOException;
import java.lang.ref.WeakReference;
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
    private MyTask weatherInfoTask;
    private MyTask suggestionTask;
    private String currentCity = "shanghai";
    public WeatherPresenter(WeatherContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        unSubscribe();
        weatherInfoTask = new WeatherTask(view);
        weatherInfoTask.execute(currentCity);
    }

    @Override
    public void getSuggestion() {
        unSubscribe();
        suggestionTask = new SuggestionTask(view);
        suggestionTask.execute(currentCity);
    }


    @Override
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    @Override
    public void unSubscribe() {
        if (weatherInfoTask != null){
            weatherInfoTask.cancel(true);
        }

        if (suggestionTask != null){
            suggestionTask.cancel(true);
        }

    }

    private static abstract class MyTask extends AsyncTask<String,Integer,String> {
        private WeakReference<WeatherContract.View> viewWeakReference;

        MyTask(WeatherContract.View view) {
            this.viewWeakReference = new WeakReference<WeatherContract.View>(view);
        }

        @Override
        protected void onPreExecute() {
            if (checkViewNull()){
                return;
            }
            viewWeakReference.get().setProgressIndicator(true);
        }

        protected boolean checkViewNull() {
            return viewWeakReference.get() == null;
        }




        @Override
        protected void onPostExecute(String result) {
            if (checkViewNull()){
                return;
            }
            viewWeakReference.get().showInfo(result);
            viewWeakReference.get().setProgressIndicator(false);
        }


        @Override
        protected void onCancelled() {
            if (checkViewNull()){
                return;
            }
            viewWeakReference.get().setProgressIndicator(false);
            viewWeakReference.clear();
        }
    }
    private static class WeatherTask extends MyTask{
        WeatherTask(WeatherContract.View view) {
            super(view);
        }

        @Override
        protected String doInBackground(String... strings) {
            String error;
            try {
                return NewWorkManger.get().getDiaryWeatherInfo(strings[0]);
            } catch (IOException | SignatureException e) {
                error = e.getMessage();
                e.printStackTrace();
            }
            return error;
        }
    }

    private static class SuggestionTask extends MyTask {

        SuggestionTask(WeatherContract.View view) {
            super(view);
        }

        @Override
        protected String doInBackground(String... strings) {
            String error;
            try {
                return NewWorkManger.get().getSuggestion(strings[0]);
            } catch (IOException e) {
                error = e.getMessage();
                e.printStackTrace();
            }
            return error;
        }
    }
}
