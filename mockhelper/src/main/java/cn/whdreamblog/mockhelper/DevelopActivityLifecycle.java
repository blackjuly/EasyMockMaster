package cn.whdreamblog.mockhelper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import cn.whdreamblog.mockhelper.util.MockUtils;


/**
 * 开发者工具使用到的生命周期回调的类,为开发者而生
 * <p>
 * time   : 2018/11/06
 *
 * @author : xiaojinzi 30212
 */
public class DevelopActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(final Activity act, Bundle savedInstanceState) {
        if (!MockUtils.isDevelopDebug()) {
            return;
        }
        MockUtils.reStoreSelectMockList();
    }

    @Override
    public void onActivityStarted(Activity activity) {
        //do nothing
    }

    @Override
    public void onActivityResumed(final Activity act) {
        if (!MockUtils.isDevelopDebug()){
            return;
        }
        if (DevelopToolWindow.canInsertToolsView(act)) {
            DevelopToolWindow.fitDevelopView(act);
        }
        DevelopToolWindow.updateView(act);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        //do nothing
    }

    @Override
    public void onActivityStopped(Activity activity) {
        //do nothing
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        //do nothing
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //do nothing
    }


}
