package cn.whdreamblog.mockhelper.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

import java.lang.ref.SoftReference;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/11 ${Time}
 * desc : The class is used for .....
 */
public class AppBarUtil {

    private Window mWindow;
    private Activity mActivity;
    private int mOptions = 0;

    public static AppBarUtil with(@NonNull Context context) {
        return new AppBarUtil(context);
    }

    private AppBarUtil(Context context) {
        mOptions = 0;
        if (context instanceof Activity) {
            mActivity = new SoftReference<>((Activity) context).get();
            mWindow = mActivity.getWindow();
        } else {
            throw new IllegalArgumentException("AppBarUtil.Constructor -> Parameter context can not cast to Activity");
        }
    }

    /**
     * 设置StatusBar的风格
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppBarUtil setStatusBarStyle(Style style) {
        if (!isLollipop()) return this;
        switch (style) {
            // 设置状态栏为全透明
            case TRANSPARENT: {
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                mOptions = mOptions | option;
                mWindow.setStatusBarColor(Color.TRANSPARENT);
                break;
            }
            // 设置状态栏为半透明
            case TRANSLUCENCE: {
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                mOptions = mOptions | option;
                mWindow.setStatusBarColor(alphaColor(Color.BLACK, 0.3f));
                break;
            }
            // 隐藏状态栏
            case HIDE: {
                int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
                mOptions = mOptions | option;
                break;
            }
            // 清除透明状态栏
            case DEFAULT: {
                mOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                //获取当前Application主题中的状态栏Color
                TypedValue typedValue = new TypedValue();
                mActivity.getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true);
                int color = typedValue.data;
                mWindow.setStatusBarColor(color);
            }
        }
        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppBarUtil setStatusBarColor(int color) {
        if (!isLollipop()) return this;
        mOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        mWindow.setStatusBarColor(color);
        return this;
    }

    /**
     * 设置NavigationBar的风格
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppBarUtil setNavigationBarStyle(Style style) {
        if (!isLollipop()) return this;
        switch (style) {
            // 设置导航栏为全透明
            case TRANSPARENT: {
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                mOptions = mOptions | option;
                mWindow.setNavigationBarColor(Color.TRANSPARENT);
                break;
            }
            // 设置导航栏为半透明
            case TRANSLUCENCE: {
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                mOptions = mOptions | option;
                mWindow.setNavigationBarColor(alphaColor(Color.BLACK, 0.3f));
                break;
            }
            //隐藏导航栏
            case HIDE: {
                int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                mOptions = mOptions | option;
                break;
            }
            case DEFAULT: {
                mOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                //获取当前Activity主题中的color
                TypedValue typedValue = new TypedValue();
                mActivity.getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true);
                int color = typedValue.data;
                mWindow.setNavigationBarColor(color);
            }
        }
        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppBarUtil setNavigationBarColor(int color) {
        if (!isLollipop()) return this;
        mOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        mWindow.setNavigationBarColor(color);
        return this;
    }

    /**
     * 隐藏所有Bar(全屏模式)
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppBarUtil setAllBarsHide() {
        if (!isLollipop()) return this;
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        mOptions = option;
        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppBarUtil apply() {
        if (!isLollipop()) return this;
        if (mOptions != 0) {
            View decorView = mWindow.getDecorView();
            decorView.setSystemUiVisibility(mOptions);
        }
        return this;
    }

    /**
     * @param baseColor    需要进行透明的Color
     * @param alphaPercent 透明图(0-1)
     */
    private int alphaColor(int baseColor, float alphaPercent) {
        if (alphaPercent < 0) alphaPercent = 0f;
        if (alphaPercent > 1) alphaPercent = 1f;
        // 计算基础透明度
        int baseAlpha = (baseColor & 0xff000000) >>> 24;
        // 根基需求计算透明度
        int alpha = (int) (baseAlpha * alphaPercent);
        // 根基透明度拼接新的color
        int color = alpha << 24 | (baseColor & 0xffffff);
        return color;
    }

    private boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}