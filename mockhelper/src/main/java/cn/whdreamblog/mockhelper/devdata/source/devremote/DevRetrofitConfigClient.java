package cn.whdreamblog.mockhelper.devdata.source.devremote;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/11 16:42
 * desc : 开发者工具配置client
 */
public class DevRetrofitConfigClient {
    private Retrofit retrofit = null;
    private OkHttpClient okHttpClient = null;

    private static class RetrofitClientHolder {
        public static DevRetrofitConfigClient retrofitClient = new DevRetrofitConfigClient();
    }

    public static DevRetrofitConfigClient getInstance() {
        return RetrofitClientHolder.retrofitClient;
    }


    public Retrofit getRetrofit() {
        if (retrofit == null) {
            if (okHttpClient == null) {
                initOkHttp();
            }
            retrofit = new Retrofit.Builder().client(okHttpClient)
                    .baseUrl(MockRemote.BASE_IP+"mock/5cbd25ffad170a13bc0f154f/DriverDevConfig/")
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
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
