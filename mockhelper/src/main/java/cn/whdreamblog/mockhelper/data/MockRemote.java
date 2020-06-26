package cn.whdreamblog.mockhelper.data;

import android.text.TextUtils;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

import cn.whdreamblog.mockhelper.EasyMockHelperApplication;
import cn.whdreamblog.mockhelper.Injection;
import cn.whdreamblog.mockhelper.data.model.MocksResponse;
import cn.whdreamblog.mockhelper.util.MockInitHelper;
import okhttp3.Call;

/**
 * @author blackjuly wanghao <a href="blackjuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/05 18:13
 * desc : 用于开发工具网络请求
 */
public class MockRemote {
    private static MockRemote remote;
    public static  String BASE_IP ="http://192.168.99.101:7300/";
    public static  String userId = "blackjuly@outlook.com";
    public static String password = "123456";
    /**
     * mock列表工程对应的 id
     */
    public static  String mockDataProjectId = "5cad5c0ead170a13bc0f138c";
    private static final String DEVELOP_MOCK_AUTH_TAG = "DevelopMockAuthTag";
    private static String auth ;
    static final String cookie = "hudson_auto_refresh=false; easy-mock_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYWFmY2YwYWQxNzBhMTNiYzBmMTM3YSIsImlhdCI6MTU1NDg3NDA0NSwiZXhwIjoxNTU2MDgzNjQ1fQ.pehc1q-lymxo2HHP9qZcKj1G56wfXHn3Y5YbBxQlfwg";
    private static final int MAX_REQUEST = 30;


    /**
     * 获取easymock的列表，必备的界面数据
     */
    public static final String page_size = "2000";
    public static final String page_index = "1";

    private static final Deque<MocksResponse> DEQUE = new LinkedList<>();
    public static Set<MocksResponse> SelectDataSet = new HashSet<>();
    public static final String SELECT_MOCK_LIST_TAG = "SELECT_MOCK_LIST_TAG";
    public static String projectBaseUrl = MockRemote.BASE_IP+"mock/5cad5c0ead170a13bc0f138c/driverDemo";
    /**
     * mock所有的数据
     */
    private static boolean mockAll = false;
    /**
     *  默认前缀为空所以要小心，必须调用一次整体列表接口
     */
    private static String mockPrefix = "";
    private static boolean openMockRecord = false;

    private EasyMockHelperApplication context;
    /**
     * 接口分割符
     * // TODO: 2020/5/23 下个版本分割成 真实url和 mock的url分开使用      */
    private  String splitter;

    private MockRemote(EasyMockHelperApplication context) {
        Objects.requireNonNull(context);
        this.context = context;
        this.splitter = context.getUrlMatcher().splitter();
        //初始化网络请求类
        ServiceFactory.init();
    }

    public static void init(EasyMockHelperApplication context){
       remote = new MockRemote(context);
    }
    public static MockRemote get(){
        Objects.requireNonNull(remote);
        return remote;
    }

    public String getSplitter() {
        return splitter;
    }

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
        // TODO: 2020/5/31 目前这种策略只适合， xxx/api/sss  中间有相同分隔符的url
        String[] parts = url.split(get().splitter);
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
        return !openMockRecord;
    }

    public static void updatePrefix(String mockResponseUrl){
        if (TextUtils.isEmpty(mockResponseUrl)){
            return;
        }
        if (!mockResponseUrl.contains(get().splitter)){
            return;
        }
        //说明没有奇怪的东西被添加进来
        if (mockResponseUrl.startsWith(get().splitter) ||
                mockResponseUrl.startsWith("/"+get().splitter)){
            return;
        }
        /*
        此处处理 mock url在用swagger生成时，有时会有奇怪的前缀
        真实地址 www.demo.cn/getList
        例如swagger地址 http://xxx.cn/foo/swagger/docs/v1 则mock地址就会包含 foo/getList/
        所以需要将前缀保留下来
         */
        String[] parts = mockResponseUrl.split(get().splitter);
        if (parts.length <= 0){
            return;
        }
        mockPrefix = parts[0].concat(get().splitter);
    }
    public static Call.Factory getClient(){
        return Injection.getCallFactory();
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
