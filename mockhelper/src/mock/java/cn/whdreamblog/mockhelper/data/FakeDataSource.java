package cn.whdreamblog.mockhelper.data;

import java.util.List;

import cn.whdreamblog.mockhelper.EasyMockHelperApplication;
import cn.whdreamblog.mockhelper.data.model.EasyMockCommonResponse;
import cn.whdreamblog.mockhelper.data.model.EasyMockResponseList;
import cn.whdreamblog.mockhelper.data.model.LoginRequest;
import cn.whdreamblog.mockhelper.data.model.LoginResponse;
import cn.whdreamblog.mockhelper.data.model.MocksResponse;
import cn.whdreamblog.mockhelper.util.GsonUtil;
import io.reactivex.Single;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/5/31 14.10
 * desc : The class is used for show fake data
 */
public class FakeDataSource implements MockService, Call.Factory {
    private Call call;
    @Override
    public Single<EasyMockResponseList> getMockList(String project_id, String page_size, String page_index, String keywords) {

        return Single.just(GsonUtil.fromJson(FakeJson.responseList, EasyMockResponseList.class));
    }

    @Override
    public Single<EasyMockCommonResponse<String>> updateRecord(MocksResponse response) {
        if (call == null){
            return Single.just(GsonUtil.fromJson(FakeJson.failJson, EasyMockCommonResponse.class));
        }
        if (call instanceof MockCall){
           boolean success =  ((MockCall) call).replaceMockResponse(response);
           if (success){
               return Single.just(GsonUtil.fromJson(FakeJson.successJson, EasyMockCommonResponse.class));
           }
        }
        return Single.just(GsonUtil.fromJson(FakeJson.failJson, EasyMockCommonResponse.class));
    }

    @Override
    public Single<EasyMockCommonResponse<String>> addRecord(MocksResponse response) {
        return Single.just(GsonUtil.fromJson(FakeJson.successJson, EasyMockCommonResponse.class));
    }

    @Override
    public Single<EasyMockCommonResponse<LoginResponse>> loginMock(LoginRequest request) {
        return Single.just(GsonUtil.fromJson(FakeJson.loginJson, EasyMockCommonResponse.class));
    }

    @Override
    public Single<EasyMockCommonResponse<List>> testConnection() {
        return Single.just(GsonUtil.fromJson(FakeJson.testConnectionJson, EasyMockCommonResponse.class));

    }

    @Override
    public Call newCall(Request request) {
        call = new MockCall(request, EasyMockHelperApplication.get().getUrlMatcher());
        return call;
    }

    public static class FakeJson {
        static final String responseList = "{\n" +
                "  \"code\": 200,\n" +
                "  \"success\": true,\n" +
                "  \"message\": \"success\",\n" +
                "  \"data\": {\n" +
                "    \"project\": {\n" +
                "      \"user\": {\n" +
                "        \"_id\": \"5caafcf0ad170a13bc0f137a\",\n" +
                "        \"name\": \"\",\n" +
                "        \"nick_name\": \"1554709744476\",\n" +
                "        \"head_img\": \"//img.souche.com/20161230/png/fd9f8aecab317e177655049a49b64d02.png\",\n" +
                "        \"create_at\": \"2020-05-31T06:25:42.819Z\"\n" +
                "      },\n" +
                "      \"_id\": \"5cd2a275ad170a13bc0f155d\",\n" +
                "      \"name\": \"JavaTest\",\n" +
                "      \"url\": \"/JavaTest\",\n" +
                "      \"description\": \"JavaTest\",\n" +
                "      \"swagger_url\": \"1111\",\n" +
                "      \"members\": [],\n" +
                "      \"extend\": {\n" +
                "        \"_id\": \"5cd2a275ad170a13bc0f155e\",\n" +
                "        \"is_workbench\": false\n" +
                "      }\n" +
                "    },\n" +
                "    \"mocks\": [\n" +
                "      {\n" +
                "        \"_id\": \"5ed22911fc1c1f00166fa05f\",\n" +
                "        \"url\": \"/v3/weather/now.json\",\n" +
                "        \"method\": \"get\",\n" +
                "        \"description\": \"当前天气mock\",\n" +
                "        \"mode\": \"{\\n  data: \\\"这里是mock数据，今天天气很糟糕\\\"\\n}\",\n" +
                "        \"parameters\": null,\n" +
                "        \"response_model\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"_id\": \"5ed22358fc1c1f00166fa05e\",\n" +
                "        \"url\": \"/v3/life/suggestion.json\",\n" +
                "        \"method\": \"get\",\n" +
                "        \"description\": \"生活指数\",\n" +
                "        \"mode\": \"{\\n  \\\"data\\\": \\\"这里是生活指数mock，今天穿的帅气点吧~\\\"\\n}\",\n" +
                "        \"parameters\": null,\n" +
                "        \"response_model\": null\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        public static final String loginJson = "{\"code\":200,\"success\":true,\"message\":\"success\",\"data\":{\"_id\":\"5caafcf0ad170a13bc0f137a\",\"name\":\"blackjuly@outlook.con\",\"nick_name\":\"1554709744476\",\"head_img\":\"//img.souche.com/20161230/png/fd9f8aecab317e177655049a49b64d02.png\",\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYWFmY2YwYWQxNzBhMTNiYzBmMTM3YSIsImlhdCI6MTU5MDkwNjcwNywiZXhwIjoxNTkyMTE2MzA3fQ.j_puMYNIF2EwuEAlvEo-303xP_TlGRMX2k6Q1fJt8gI\"}}";
        public static final String testConnectionJson = "{\n" +
                "  \"code\": 200,\n" +
                "  \"success\": true,\n" +
                "  \"message\": \"success\",\n" +
                "  \"data\": []\n" +
                "}";
        public static final String successJson = "{\"code\":200,\"success\":true,\"message\":\"success\",\"data\":null}";
        public static final String failJson = "{\"code\":400,\"success\":false,\"message\":\"fail\",\"data\":null}";
    }
}
