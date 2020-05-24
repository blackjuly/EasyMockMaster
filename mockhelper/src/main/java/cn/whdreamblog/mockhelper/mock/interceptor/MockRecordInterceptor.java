package cn.whdreamblog.mockhelper.mock.interceptor;


import java.io.IOException;

import cn.whdreamblog.mockhelper.data.MockRemote;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/12 11:23
 * desc :
 */
public class MockRecordInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String json = response.body() == null ? "" : response.body().string();
        if (response.isSuccessful()) {
            MockRemote.addRequest(request.url().url().getPath(), request.method(), json);
        }
        return response.newBuilder()
                .body(
                        ResponseBody.create(response.body().contentType(), json)
                ).build();
    }
}
