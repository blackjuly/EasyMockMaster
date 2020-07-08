package cn.whdreamblog.mockhelper;

import android.app.Application;

import java.util.Objects;

import cn.whdreamblog.mockhelper.data.MockRemote;
import cn.whdreamblog.mockhelper.mock.interceptor.UrlMatcher;
import cn.whdreamblog.mockhelper.util.MockInitHelper;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/6 ${Time}
 * desc : The class is used for .....
 */
public class EasyMockHelperApplication {
    public static final String HTTP_STRING = "http";
    private Application content;
    private static EasyMockHelperApplication app ;
    private UrlMatcher urlMatcher;
    private EasyMockHelperApplication(Application application,UrlMatcher matcher){
        Objects.requireNonNull(application);
        Objects.requireNonNull(matcher);
        this.content = application;
        this.urlMatcher = matcher;
    }
    public static void init(Application application, String userName, String password, String baseUrl
            , String projectId, String projectBaseUrl){
        init(application, userName, password, baseUrl, projectId, projectBaseUrl,UrlMatcher.defaultMatcher);
    }
    /**
     *
     * @param application 上下文
     * @param userName 用户名
     * @param password 密码
     * @param baseUrl baseUrl
     * @param projectId 工程id
     * @param matcher 匹配url
     */
    public static void init(Application application, String userName, String password, String baseUrl
            , String projectId, String projectBaseUrl, UrlMatcher matcher){

        Objects.requireNonNull(application);
        Objects.requireNonNull(userName);
        Objects.requireNonNull(password);
        Objects.requireNonNull(baseUrl);
        Objects.requireNonNull(projectId);
        Objects.requireNonNull(projectBaseUrl);
        Objects.requireNonNull(matcher);
        if (!baseUrl.toLowerCase().startsWith(HTTP_STRING)){
            throw new IllegalArgumentException("baseUrl必须是以http开头，类似 http://ip:7300/");
        }
        MockRemote.BASE_IP = baseUrl;
        MockRemote.userId = userName;
        MockRemote.password = password;
        MockRemote.mockDataProjectId = projectId;
        MockRemote.projectBaseUrl = projectBaseUrl;
        MockInitHelper.get().init(application);
        application.registerActivityLifecycleCallbacks(new DevelopActivityLifecycle());
        app = new EasyMockHelperApplication(application,matcher);
        MockRemote.init(app);
    }
    public static EasyMockHelperApplication get(){
        if (app == null){
            throw new RuntimeException("You must call init first");
        }
        return app;
    }

    public UrlMatcher getUrlMatcher() {
        return urlMatcher;
    }
}
