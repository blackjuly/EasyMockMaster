package cn.whdreamblog.mockhelper.data;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/11 16:42
 * desc : 开发者工具client
 */
public class ServiceFactory {
    private Retrofit retrofit = null;
    private OkHttpClient okHttpClient = null;

    private static class RetrofitClientHolder {
        public static ServiceFactory retrofitClient = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return RetrofitClientHolder.retrofitClient;
    }


    public Retrofit getRetrofit() {
        if (retrofit == null) {
            if (okHttpClient == null) {
                initOkHttp();
            }
            retrofit = new Retrofit.Builder().client(okHttpClient)
                    .baseUrl(MockRemote.BASE_IP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }

    private void initOkHttp() {
        if (okHttpClient != null){
            return;
        }
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor())
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
