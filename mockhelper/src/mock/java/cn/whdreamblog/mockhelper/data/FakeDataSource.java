package cn.whdreamblog.mockhelper.data;

import java.util.List;

import cn.whdreamblog.mockhelper.data.model.EasyMockCommonResponse;
import cn.whdreamblog.mockhelper.data.model.EasyMockResponseList;
import cn.whdreamblog.mockhelper.data.model.LoginRequest;
import cn.whdreamblog.mockhelper.data.model.LoginResponse;
import cn.whdreamblog.mockhelper.data.model.MocksResponse;
import cn.whdreamblog.mockhelper.util.GsonUtil;
import io.reactivex.Single;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/5/31 14.10
 * desc : The class is used for show fake data
 */
public class FakeDataSource implements MockService {

    @Override
    public Single<EasyMockResponseList> getMockList(String project_id, String page_size, String page_index, String keywords) {

        return Single.just(GsonUtil.fromJson(FakeJson.responseList,EasyMockResponseList.class));
    }

    @Override
    public Single<EasyMockCommonResponse<String>> updateRecord(MocksResponse response) {
        return Single.just(GsonUtil.fromJson(FakeJson.successJson,EasyMockCommonResponse.class));
    }

    @Override
    public Single<EasyMockCommonResponse<String>> addRecord(MocksResponse response) {
        return Single.just(GsonUtil.fromJson(FakeJson.successJson,EasyMockCommonResponse.class));
    }

    @Override
    public Single<EasyMockCommonResponse<LoginResponse>> loginMock(LoginRequest request) {
        return Single.just(GsonUtil.fromJson(FakeJson.loginJson,EasyMockCommonResponse.class));
    }

    @Override
    public Single<EasyMockCommonResponse<List>> testConnection() {
        return Single.just(GsonUtil.fromJson(FakeJson.testConnectionJson,EasyMockCommonResponse.class));

    }

    static class FakeJson {
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
                "        \"mode\": \"{\\n  data: \\\"sss11111 @now\\\"\\n}\",\n" +
                "        \"parameters\": null,\n" +
                "        \"response_model\": null\n" +
                "      },\n" +
                "      {\n" +
                "        \"_id\": \"5ed22358fc1c1f00166fa05e\",\n" +
                "        \"url\": \"/v3/life/suggestion.json\",\n" +
                "        \"method\": \"get\",\n" +
                "        \"description\": \"生活指数\",\n" +
                "        \"mode\": \"{\\n  \\\"data\\\": \\\" sss1111@now\\\"\\n}\",\n" +
                "        \"parameters\": null,\n" +
                "        \"response_model\": null\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        static final String loginJson = "{\"code\":200,\"success\":true,\"message\":\"success\",\"data\":{\"_id\":\"5caafcf0ad170a13bc0f137a\",\"name\":\"blackjuly@outlook.con\",\"nick_name\":\"1554709744476\",\"head_img\":\"//img.souche.com/20161230/png/fd9f8aecab317e177655049a49b64d02.png\",\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYWFmY2YwYWQxNzBhMTNiYzBmMTM3YSIsImlhdCI6MTU5MDkwNjcwNywiZXhwIjoxNTkyMTE2MzA3fQ.j_puMYNIF2EwuEAlvEo-303xP_TlGRMX2k6Q1fJt8gI\"}}";
        static final String testConnectionJson = "{\n" +
                "  \"code\": 200,\n" +
                "  \"success\": true,\n" +
                "  \"message\": \"success\",\n" +
                "  \"data\": []\n" +
                "}";
        static final String successJson  ="{\"code\":200,\"success\":true,\"message\":\"success\",\"data\":null}";
    }
}
