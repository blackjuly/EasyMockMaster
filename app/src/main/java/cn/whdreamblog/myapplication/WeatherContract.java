package cn.whdreamblog.myapplication;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/5/16
 * desc : The class is used for show weather contract
 */
public interface WeatherContract {
    interface View {
        /**
         * 展示加载框 true ,反之不展示
         */
        void setProgressIndicator(boolean active);

        /**
         * @param presenter 绑定presenter
         */
        void setPresenter(Presenter presenter);

        /**
         * 展示
         * @param weatherInfo json
         */
        void showWeatherInfo(String weatherInfo);
    }

    interface Presenter {
        /**
         * 进入app做的第一个请求
         */
        void start();

        /**
         * 解绑定
         */
        void unSubscribe();
    }
}
