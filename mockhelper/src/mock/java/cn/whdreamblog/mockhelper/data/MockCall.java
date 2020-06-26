package cn.whdreamblog.mockhelper.data;

import java.io.IOException;
import java.util.List;

import cn.whdreamblog.mockhelper.data.model.EasyMockResponseList;
import cn.whdreamblog.mockhelper.data.model.MocksResponse;
import cn.whdreamblog.mockhelper.mock.interceptor.UrlMatcher;
import cn.whdreamblog.mockhelper.util.GsonUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/6/26 16.13
 * desc : The class is used for .....
 */
public class MockCall implements Call {
    private Request request;
    private static List<MocksResponse> mocks = GsonUtil.fromJson(FakeDataSource.FakeJson.responseList, EasyMockResponseList.class).getData().getMocks();;
    private UrlMatcher matcher;
    public MockCall(Request request, UrlMatcher matcher) {
        this.request = request;
       this.matcher = matcher;
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public Response execute() throws IOException {
        byte[] serializedBody = null;
        for (MocksResponse m :
                mocks) {
          boolean canMatch =  matcher.isUrlEquals(request,m);
          if (canMatch){
             // MockCall.Mode mode = GsonUtil.fromJson(m.getMode(),MockCall.Mode.class);
              serializedBody = m.getMode().getBytes();
              break;
          }
        }
        if (serializedBody == null){
            throw new IOException("未找到匹配的mockResponse"+request+"mocks:"+mocks);
        }
        final Response response = new Response.Builder()
                .request(new Request.Builder().url("http://url.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200).message("").body(
                        ResponseBody.create(
                                MediaType.parse("application/json"),
                                serializedBody
                        ))
                .build();
        return response;
    }
    public boolean replaceMockResponse(MocksResponse newResponse){
        for (MocksResponse m :
                mocks) {
            if (m.getUrl().equalsIgnoreCase(newResponse.getUrl()) &&
                m.getMethod().equalsIgnoreCase(newResponse.getMethod())
            ){
                mocks.remove(m);
                mocks.add(newResponse);
                return true;
            }
        }
        return false;
    }
    @Override
    public void enqueue(Callback responseCallback) {
        //do nothing
    }

    @Override
    public void cancel() {
        //do nothing
    }

    @Override
    public boolean isExecuted() {
        return true;
    }

    @Override
    public boolean isCanceled() {
        return true;
    }

    @Override
    public Call clone() {
        return new MockCall(request,matcher);
    }

    /**
     * 由于此类只在假数据中使用
     */
    public static class Mode {
        String data;
        String parameters;
        String response_model;

        public String getData() {
            return data;
        }
    }
}
