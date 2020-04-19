package cn.whdreamblog.mockhelper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * <pre>
 *     author : 28476
 *     e-mail : hao.wang@1hai.cn
 *     time   : 2017年10月18日
 *     desc   : 新版的 Activity baseView 基础类, 构建 Activity View 的时候推荐直接继承
 *     version: v1.1
 * </pre>
 */

public interface BaseViewSupport<T> extends BaseView<T> {

    /**
     * It will invoke when U click assist page.
     */
    interface NetworkErrorCallback {
        // It will invoke when U click assist page.
        void tryAgain();
    }

    /**
     * 展示空数据
     * <p>
     * 默认锚定的是 {@link android.R.id#content} 这个容器
     * </p>
     */
    void showEmptyData();

    /**
     * 展示空数据
     * <p>
     * 默认锚定的是 {@link android.R.id#content} 这个容器
     * </p>
     */
    void showEmptyData(@Nullable CharSequence brief);

    /**
     * 展示空数据
     * <p>
     * 为了在锚点 View 上展示空数据, 会在锚点 View 上包装一个 AssistView 容器
     * 通过 findViewById() 找到的 view 找到的是 AssistView 容器, 若想获取锚点 view, 请调用
     * {@link com.ehi.lib.base.widget.assist.AssistView#getBindView()}
     * </p>
     *
     * @param anchorView 指定的锚点 View
     */
    void showEmptyData(@NonNull View anchorView);

    /**
     * 展示空数据
     * <p>
     * 默认锚定的是 {@link android.R.id#content} 这个容器
     * </p>
     */
    void showEmptyData(@Nullable CharSequence brief, @NonNull View anchorView);

    /**
     * 隐藏空数据
     */
    void hindEmptyData();

    /**
     * 隐藏空数据
     */
    void hindEmptyData(@NonNull View anchorView);

    /**
     * 展示网络异常的视图
     * <p>
     * 默认锚定的是 {@link android.R.id#content} 这个容器
     * </p>
     *
     * @param callback 网络请求重试回调
     */
    void showNetworkError(@NonNull NetworkErrorCallback callback);

    /**
     * 展示网络异常的视图
     * <p>
     * 为了在锚点 View 上展示空数据, 会在锚点 View 上包装一个 AssistView 容器
     * 通过 findViewById() 找到的 view 找到的是 AssistView 容器, 若想获取锚点 view, 请调用
     * </p>
     *
     * @param anchorView 指定的锚点 View,
     * @param callback   网络请求重试回调
     */
    void showNetworkError(@NonNull View anchorView, @NonNull NetworkErrorCallback callback);

}
