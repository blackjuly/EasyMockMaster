package cn.whdreamblog.mockhelper.devdata.source.devremote;

import android.text.TextUtils;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import cn.whdreamblog.mockhelper.util.MockInitHelper;
import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;
import okhttp3.OkHttpClient;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/05 18:13
 * desc : 用于开发工具网络请求
 */
public class MockRemote {
    public static  String BASE_IP ="http://192.168.99.101:7300/";
    public static  String userId = "blackjuly@outlook.com";
    public static String password = "120026139";
    /**
     * mock列表工程对应的 id
     */
    public static  String mockDataProjectId = "5cad5c0ead170a13bc0f138c";
    public static final String DEVELOP_MOCK_AUTH_TAG = "DevelopMockAuthTag";
    public static String auth ;
    public static final String cookie = "hudson_auto_refresh=false; easy-mock_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYWFmY2YwYWQxNzBhMTNiYzBmMTM3YSIsImlhdCI6MTU1NDg3NDA0NSwiZXhwIjoxNTU2MDgzNjQ1fQ.pehc1q-lymxo2HHP9qZcKj1G56wfXHn3Y5YbBxQlfwg";
    private static final int MAX_REQUEST = 30;

    /**
     * 司机app 接口分割符
     */
    public static final String API = "/api/";
    /**
     * 获取easymock的列表，必备的界面数据
     */
    public static final String page_size = "2000";
    public static final String page_index = "1";
    public static MockService mockService = DevRetrofitClient.getInstance().getRetrofit().create(MockService.class);
    public static DevConfigApi devConfigApi = DevRetrofitConfigClient.getInstance().getRetrofit().create(DevConfigApi.class);
    private static final Deque<MocksResponse> DEQUE = new LinkedList<>();
    public static Set<MocksResponse> SelectDataSet = new HashSet<>();
    public static final String SELECT_MOCK_LIST_TAG = "SELECT_MOCK_LIST_TAG";
    /**
     * mock所有的数据
     */
    private static boolean mockAll = false;
    /**
     *  默认前缀为空所以要小心，必须调用一次整体列表接口
     */
    private static String mockPrefix = "";
    private static boolean openMockRecord = false;

    /**
     *  添加请求
     * @param url 链接
     * @param method 方法
     * @param mode 响应json
     */
    public static void addRequest(String url,String method,String mode){
        //前缀为空
        if (disAllowRecord()){
            return;
        }
        if (TextUtils.isEmpty(url)
                || TextUtils.isEmpty(method)
                || TextUtils.isEmpty(mode)
        ){
            return;
        }
        String[] parts = url.split(API);
        if (parts.length < 2){
            return;
        }
        url = mockPrefix.concat(parts[1]);
        MocksResponse mocksResponse = new MocksResponse();
        mocksResponse.setUrl(url);
        mocksResponse.setMethod(method);
        mocksResponse.setMode(mode);
        synchronized (DEQUE){
            while (DEQUE.size() > MAX_REQUEST ){
                DEQUE.removeFirst();
            }
        }
        DEQUE.remove(mocksResponse);
        DEQUE.addLast(mocksResponse);
    }

    public static Deque<MocksResponse> getDEQUE() {
        return DEQUE;
    }

    /**
     *
     * @return 开关管理是否可以录制
     */
    private static boolean disAllowRecord() {
        if (!openMockRecord){
            return true;
        }
        return TextUtils.isEmpty(mockPrefix);
    }

    public static void updatePrefix(String mockResponseUrl){
        if (TextUtils.isEmpty(mockResponseUrl)){
            return;
        }
        if (!mockResponseUrl.contains(API)){
            return;
        }
        String[] parts = mockResponseUrl.split(API);
        if (parts.length <= 0){
            return;
        }
        mockPrefix = parts[0].concat(API);
    }
    public static OkHttpClient getClient(){
        return DevRetrofitClient.getInstance().getOkHttpClient();
    }

    public static boolean isOpenMockRecord() {
        return openMockRecord;
    }

    public static void setOpenMockRecord(boolean openMockRecord) {
        MockRemote.openMockRecord = openMockRecord;
    }

    public static boolean isMockAll() {
        return mockAll;
    }

    public static void setMockAll(boolean mockAll) {
        MockRemote.mockAll = mockAll;
    }

    public static Set<MocksResponse> getSelectDataSet() {
        return SelectDataSet;
    }

    public static void setSelectDataSet(Set<MocksResponse> selectDataSet) {
        SelectDataSet = selectDataSet;
    }

    public static DevConfigApi getDevConfigApi() {
        return devConfigApi;
    }

    public static void setAuth(String auth) {
        MockRemote.auth = auth;
        MockInitHelper.get().getSpUtil().putString(DEVELOP_MOCK_AUTH_TAG,auth);
    }

    public static String getAuth() {
        if (TextUtils.isEmpty(auth)){
          auth = MockInitHelper.get().getSpUtil().getString(DEVELOP_MOCK_AUTH_TAG,"");
        }
        return MockRemote.auth;
    }
}
