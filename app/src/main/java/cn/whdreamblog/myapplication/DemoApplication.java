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
    public static  String userId = "hao.wang@1hai.cn";
    public static String password = "120026139";
    public static  String mockDataProjectId = "5cd2a275ad170a13bc0f155d";
    @Override
    public void onCreate() {
        super.onCreate();
        EasyMockHelperApplication.init(this, userId, password, baseIp, mockDataProjectId
                , "http://192.168.99.101:7300/mock/5cd2a275ad170a13bc0f155d/JavaTest"
                // 传入url的通用的分隔符
                , new UrlMatcher.SplitterUrlMatcher("v3")
        );
        NewWorkManger.init();
    }
}
