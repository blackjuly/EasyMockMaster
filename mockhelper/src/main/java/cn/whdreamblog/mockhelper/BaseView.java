package cn.whdreamblog.mockhelper;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * MVP base view.
 * 构建 Fragment View 的时候推荐直接继承
 *
 * @author Frank <a href="xiaoyu.zhu@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2018/11/1  9:33
 */
public interface BaseView<T> {

    /**
     * 绑定 presenter
     */
    void bindPresenter(T presenter);

    T getPresenter();

    /**
     * 用于解绑 Presenter
     */
    void unbindPresenter();

    /**
     * 设置拦截器
     */
    void setProgressInterceptor(ProgressInterceptor progressInterceptor);

    /**
     * 显示加载框
     *
     * @param active 是否激活
     */
    void setProgressIndicator(boolean active);

    void setProgressIndicator(boolean active, boolean cancelable);

    /**
     * 忽略拦截器，直接使用默认加载框
     *
     * @param active true 激活加载框
     */
    void setProgressDefaultIndicator(boolean active, boolean cancelable);

    /**
     * @param active 使用默认加载框，不使用任何拦截加载框
     */
    void setProgressDefaultIndicator(boolean active);

    /**
     * 此方法用于兼容老版本的网络请求类使用
     */
    void setProgressOnKeyListener(DialogInterface.OnKeyListener onKeyListener);

    /**
     * Progress bar style interceptor.
     */
    interface ProgressInterceptor {

        void setProgressIndicator(boolean active);

    }

    /**
     * 普通提示
     *
     * @param msg 提示的文本
     */
    void tip(@NonNull String msg);

    /**
     * @param strRes 文本资源文件
     */
    void tip(@StringRes int strRes);

    /**
     * 提示
     *
     * @param msg     提示的文本
     * @param tipEnum 提示的类型
     */
    void tip(@NonNull String msg, TipEnum tipEnum);

    /**
     * 信息提示的类型
     */
    enum TipEnum {
        Normal, Error, MsgBox
    }

}
