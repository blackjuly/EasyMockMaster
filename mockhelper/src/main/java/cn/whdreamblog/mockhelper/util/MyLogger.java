package cn.whdreamblog.mockhelper.util;

import android.util.Log;


/**
 * Mock日志类
 */
public class MyLogger {

    private static String tag = MyLogger.class.getSimpleName();
    private static MyLogger sInstance;

    static {
        sInstance = new MyLogger();
    }

    @Deprecated
    public static MyLogger getLogger(String className) {
//        MyLogger sInstance = new MyLogger(MyLogger.class.getSimpleName());
//        sInstance.t(className);
        return sInstance;
    }

    public static MyLogger getLogger() {
        return sInstance;
    }

    private boolean isLocalPrint;

    private MyLogger() {

    }

    /**
     * 初始化打印的方式
     *
     * @param isLocalPrint 是否打印本地日志
     */
    public void init(boolean isLocalPrint) {
        this.isLocalPrint = isLocalPrint;
    }

    public void dByAndroidLog(String message, Object... args) {
        tag = defaultTag();
        if (!isLocalPrint) {
            return;
        }
        Log.d(tag, message);
    }
    public void eByAndroidLog(String message, Object... args) {
        tag = defaultTag();
        if (!isLocalPrint) {
            return;
        }
        Log.e(tag, message);
    }
    public void eByAndroidLog(String message, Throwable throwable) {
        tag = defaultTag();
        if (!isLocalPrint) {
            return;
        }
        Log.e(tag, message,throwable);
    }

    public void vByAndroidLog(String message, Object... args) {
        tag = defaultTag();
        if (!isLocalPrint) {
            return;
        }
        Log.v(tag, message);

    }

    public void iByAndroidLog(String message, Object... args) {
        tag = defaultTag();
        if (!isLocalPrint) {
            return;
        }
        Log.i(tag, message);

    }
    public void json(String json) {
        tag = defaultTag();
        if (!isLocalPrint) {
            return;
        }
        Log.d(tag, "origin json: " + json);
    }

    /**
     * 从线程栈中获取日志的名字
     */
    private static String defaultTag() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // 获取线程任务栈中深度为 4 的元素信息
        StackTraceElement log = stackTrace[4];
        String className = log.getClassName();
        if (className.isEmpty()) {
            return tag;
        }
        // 截取最后一个位置的信息
        int subIndex = className.lastIndexOf(".") + 1;
        return className.substring(subIndex);
    }

    public void e(Throwable e, String message) {
        eByAndroidLog(message,e);
    }
    public void e(Throwable e) {
        eByAndroidLog(e.getMessage());
    }
    public void i(String s) {
        iByAndroidLog(s);
    }

    public void d(String s) {
        dByAndroidLog(s);
    }
}
