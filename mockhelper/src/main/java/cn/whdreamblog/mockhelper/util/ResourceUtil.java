package cn.whdreamblog.mockhelper.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * <pre>
 *     author : blackjuly
 *     e-mail : blackjuly@outlook.com
 *     time   : 2018/05/23/10:32
 *     desc   : 资源工具类
 *     version: 1.0
 * </pre>
 */


public class ResourceUtil {
    private ResourceUtil() {
    }

    public static int getColor(Context context, int rsd) {
        return getColor(context.getApplicationContext().getResources(), rsd);
    }

    public static int getColor(Resources resources, int rsd) {
        return Build.VERSION.SDK_INT >= 23 ? resources.getColor(rsd, (Resources.Theme) null) : resources.getColor(rsd);
    }

    public static Drawable getDrawable(Context context, int rsd) {
        return getDrawable(context.getApplicationContext().getResources(), rsd);
    }

    public static Drawable getDrawable(Resources resources, int rsd) {
        return Build.VERSION.SDK_INT >= 21 ? resources.getDrawable(rsd, (Resources.Theme) null) : resources.getDrawable(rsd);
    }

    public static String getString(Context context, int rsd) {
        return getString(context.getApplicationContext().getResources(), rsd);
    }

    public static String getString(Resources resources, int rsd) {
        return resources.getString(rsd);
    }

    public static int getDimen(Context context, int rsd) {
        return getDimen(context.getApplicationContext().getResources(), rsd);
    }

    public static int getDimen(Resources resources, int rsd) {
        return resources.getDimensionPixelSize(rsd);
    }

}
