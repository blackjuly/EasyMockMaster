package cn.whdreamblog.mockhelper.devdata.source.devremote;


import java.io.IOException;

import cn.whdreamblog.mockhelper.util.MyLogger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/11 18:00
 * desc :
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request authorised = originalRequest.newBuilder()
                .header("Origin", MockRemote.BASE_IP)
                .header("Referer", MockRemote.BASE_IP)
                .header("Cookie", MockRemote.cookie)
                .header("Authorization", MockRemote.getAuth())
                .build();
        MyLogger.getLogger().dByAndroidLog("test");
        return chain.proceed(authorised);
    }
}
