package cn.whdreamblog.mockhelper.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 收集管理 {@link Activity} 的创建与销毁
 *
 * @author Frank <a href="xiaoyu.zhu@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2018/9/19  17:44
 */
public class ActivityTaskManager {

    private static final List<Activity> mActivities = new ArrayList<>();

    public ActivityTaskManager() {
        throw new UnsupportedOperationException(ActivityTaskManager.class.getSimpleName()
                + "Cannot support instantiation.");
    }

    /**
     * 添加一个 Activity 到任务栈中
     */
    public static void add(Activity activity) {
        mActivities.add(activity);
    }

    /**
     * 将 Activity 从任务栈中移除
     */
    public static void remove(Activity activity) {
        mActivities.remove(activity);
    }

    /**
     * 移动到任务栈首部
     */
    public static void moveToTop(Activity activity) {
        mActivities.remove(activity);
        mActivities.add(activity);
    }

    /**
     * 获取栈顶 Activity
     */
    public static Activity getTop() {
        if (mActivities.size() == 0) {
            return null;
        }
        return mActivities.get(mActivities.size() - 1);
    }

    /**
     * 获取所有 Activity
     */
    public static Collection<Activity> getAll() {
        return mActivities;
    }

    /**
     * 关闭所有 Activity
     */
    public static void finishAll() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
    }

}
