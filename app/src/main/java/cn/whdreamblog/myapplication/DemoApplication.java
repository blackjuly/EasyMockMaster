package cn.whdreamblog.myapplication;

import android.app.Application;

import cn.whdreamblog.mockhelper.EasyMockHelperApplication;
import cn.whdreamblog.mockhelper.mock.interceptor.UrlMatcher;
import cn.whdreamblog.myapplication.data.NewWorkManger;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/11 ${Time}
 * desc : The class is used for .....
 */
public class DemoApplication extends Application {
    public static  String baseIp ="http://192.168.99.101:7300/";
    public static  String userId = "demo";
    public static String password = "123456";
    public static  String mockDataProjectId = "5ed35eb2fc1c1f00166fa061";
    @Override
    public void onCreate() {
        super.onCreate();
        EasyMockHelperApplication.init(this, userId, password, baseIp, mockDataProjectId
                , "http://192.168.99.101:7300/mock/5ed35eb2fc1c1f00166fa061/example"
                // 传入url的通用的分隔符
                , new UrlMatcher.SplitterUrlMatcher("v3")
        );
        EasyMockHelperApplication.logOutPut(true);
        NewWorkManger.init();
    }
}
