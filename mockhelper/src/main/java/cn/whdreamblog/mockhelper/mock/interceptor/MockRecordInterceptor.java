package cn.whdreamblog.mockhelper.mock.interceptor;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.whdreamblog.mockhelper.devdata.source.devremote.MockRemote;
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
    private static final List<String> blackUrlList = new ArrayList<>(Arrays.asList(
            "api/gps"
    ));

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String json = response.body() == null?"":response.body().string();
        if (response.isSuccessful()){
            for (String blackPath :blackUrlList){
                if (!request.url().url().getPath().contains(blackPath)){
                    MockRemote.addRequest(request.url().url().getPath(),request.method(),json);
                }
            }
        }
        return response.newBuilder()
                .body(
                        ResponseBody.create(response.body().contentType(),json)
                ).build();
    }
}
