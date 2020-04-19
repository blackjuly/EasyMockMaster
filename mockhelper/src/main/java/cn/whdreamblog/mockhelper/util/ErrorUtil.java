package cn.whdreamblog.mockhelper.util;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

/**
 * @author : xiaojinzi 30212
 * @desc : 异常处理工具
 * @time : 2017/12/06
 * @blog : http://blog.csdn.net/u011692041
 */
public class ErrorUtil {

    private static Class[] netWorkFlags = {
            // 超时
            SocketTimeoutException.class,
            // 不知道主机
            UnknownHostException.class,
            // socket 问题
            SocketException.class,
            // 子类 NoRouteToHostException.class
            ConnectException.class,
            // 子类 SSLHandshakeException.class
            // 协议异常
            ProtocolException.class,
            UnknownServiceException.class

    };

    private static Class[] serverError = {
            HttpException.class,
    };

    /**
     * 判断一个错误是不是网络错误
     *
     * @param t 网络返回的异常
     * @return 判断是否包含常见的几种指定异常 true 网络异常
     */
    public static boolean isNetWorkError(Throwable t) {

        for (Class flag : netWorkFlags) {
            if (t != null && t.getClass().isAssignableFrom(flag)) {
                return true;
            }
            if (t != null && t.getClass().getName().equals(flag.getName())) {
                return true;
            }
        }

        return false;

    }

    public static boolean isNeedTryAgainError(Throwable t) {
        return isNetWorkError(t) || isServerError(t);
    }
    /**
     * 判断一个错误是不是服务端错误
     *
     * @param t 判断是不是服务器的发生异常
     * @return true 服务器异常
     */
    public static boolean isServerError(Throwable t) {

        for (Class flag : serverError) {
            if (t != null && t.getClass().isAssignableFrom(flag)) {
                return true;
            }
            if (t != null && t.getClass().getName().equals(flag.getName())) {
                return true;
            }

        }
        return false;

    }


    /**
     * 判断一个错误是不是服务端超时
     *
     * @param t 对应的异常
     * @return true 此时为服务端超时
     */
    public static boolean isServerTimeoutError(Throwable t) {
        return t instanceof SocketTimeoutException;
    }

}
