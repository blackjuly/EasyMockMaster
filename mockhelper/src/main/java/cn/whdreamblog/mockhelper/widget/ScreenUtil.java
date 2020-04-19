package cn.whdreamblog.mockhelper.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/11 ${Time}
 * desc : The class is used for .....
 */
public class ScreenUtil {

    private ScreenUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 获取 ActionBar 的高度
     */
    public static int getActionBarHeight(@NonNull Context context) {
        TypedValue typedValue = new TypedValue();
        // 将属性解析到TypedValue中
        context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
    }


    /**
     * 获取屏幕高度
     */
    public static int getScreenWidth(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenHeight(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap snapshotWithStatusBar(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap decorViewBitmap = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap destBitmap = Bitmap.createBitmap(decorViewBitmap, 0, 0, width, height);
        view.destroyDrawingCache();
        return destBitmap;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap snapshotWithoutStatusBar(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap decorViewBitmap = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight(activity);
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap destBitmap = Bitmap.createBitmap(decorViewBitmap, 0, statusBarHeight, width,
                height - statusBarHeight);
        view.destroyDrawingCache();
        return destBitmap;
    }

}