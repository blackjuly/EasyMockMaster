package cn.whdreamblog.mockhelper.mock.interceptor;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;
import cn.whdreamblog.mockhelper.devdata.source.devremote.MockRemote;
import cn.whdreamblog.mockhelper.util.MockUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 28476 wanghao <a href="hao.wang@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2019/04/05 13:53
 * desc : 用于转发mock数据
 */
public class MockDataInterceptor implements Interceptor {
    public static final String API = "api";
    private final String baseUrl = MockRemote.BASE_IP+"mock/5cad5c0ead170a13bc0f138c/driverDemo";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (!MockUtils.isDevelopDebug()){
            return chain.proceed(originalRequest);
        }
        List<MocksResponse> list = new ArrayList<>(MockRemote.SelectDataSet);

        for (MocksResponse bean :
                list) {
            if ( isUrlEquals(originalRequest,bean)
            ) {
                String queryString = getQueryString(originalRequest.url());
                Request.Builder builder = new Request.Builder();
                Request mockRequest = builder.headers(originalRequest.headers())
                        .method(originalRequest.method(),originalRequest.body())
                        .tag(originalRequest.tag())
                        .url(baseUrl+bean.getUrl()+queryString)
                        .build();
                Response response = MockRemote.getClient().newCall(mockRequest).execute();
                response.header("mock","isMock");
                return response;
            }
        }
        return chain.proceed(originalRequest);
    }

    private String getQueryString(HttpUrl url) {
        if (url.querySize() <= 0){
            return "";
        }
        String queryString = url.url().getQuery();
        return "?".concat(queryString);
    }

    private boolean isUrlEquals( Request originalRequest,MocksResponse bean){
        if (!originalRequest.method().equalsIgnoreCase(bean.getMethod())){
            return false;
        }
        String path = originalRequest.url().url().getPath().split(API)[1];
        String beanUrl = bean.getUrl().split(API)[1];
        return path.equalsIgnoreCase(beanUrl);
    }
}
