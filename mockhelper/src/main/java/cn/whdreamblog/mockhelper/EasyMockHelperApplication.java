package cn.whdreamblog.mockhelper;

import android.app.Application;

import java.util.Objects;

import cn.whdreamblog.mockhelper.devdata.source.devremote.MockRemote;
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
    private EasyMockHelperApplication(Application application){
        this.content = application;
    }

    /**
     *
     * @param application 上下文
     * @param userName 用户名
     * @param password 密码
     * @param baseUrl baseUrl
     * @param projectId 工程id
     */
    public static void init(Application application,String userName,String password,String baseUrl,String projectId){
        if (application == null){
            throw new RuntimeException("application must not null!");
        }
        Objects.requireNonNull(userName);
        Objects.requireNonNull(password);
        Objects.requireNonNull(baseUrl);
        Objects.requireNonNull(projectId);
        if (!baseUrl.toLowerCase().startsWith(HTTP_STRING)){
            throw new IllegalArgumentException("");
        }
        MockRemote.BASE_IP = baseUrl;
        MockRemote.userId = userName;
        MockRemote.password = password;
        MockRemote.mockDataProjectId = projectId;
        MockInitHelper.get().init(application);
        application.registerActivityLifecycleCallbacks(new DevelopActivityLifecycle());
        app = new EasyMockHelperApplication(application);
    }
    public static EasyMockHelperApplication get(){
        if (app == null){
            throw new RuntimeException("You must call init first");
        }
        return app;
    }
}
