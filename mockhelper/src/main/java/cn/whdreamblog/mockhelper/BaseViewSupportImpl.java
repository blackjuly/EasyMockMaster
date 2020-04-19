package cn.whdreamblog.mockhelper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import cn.whdreamblog.mockhelper.util.ResourceUtil;


/**
 * BaseViewSupport 的基础实现类
 *
 * @author Frank <a href="xiaoyu.zhu@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2018/8/27  15:23
 */
public class BaseViewSupportImpl<T extends BasePresenter> implements BaseViewSupport<T> {

    private Activity mActivity;
    private final IDialogFactory mDialogFactory;                           // DialogFactory must be instantiation at constructor.
    private T mPresenter;                                                  // Bind presenter.
    private BaseViewSupport.ProgressInterceptor mProgressInterceptor;      // Progress bar interceptor, U can custom progress bar style by implements ProgressInterceptor
    private View mDefaultAnchorView;

    public BaseViewSupportImpl(Context context, @NonNull IDialogFactory dialogFactory) {
        if (context instanceof Activity) {
            mActivity = (Activity) context;
            mDefaultAnchorView = mActivity.findViewById(android.R.id.content);
        } else {
            throw new IllegalArgumentException("Please ensure parameter context is an instance of Activity.");
        }
        if (null == dialogFactory) {
            throw new NullPointerException("Please ensure parameter dialogFactory non null!");
        }
        this.mDialogFactory = dialogFactory;
    }

    @Override
    public void bindPresenter(T presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public T getPresenter() {
        return mPresenter;
    }

    @Override
    public void setProgressInterceptor(BaseViewSupport.ProgressInterceptor progressInterceptor) {
        this.mProgressInterceptor = progressInterceptor;
    }

    @Override
    public void setProgressIndicator(boolean active) {
        setProgressIndicator(active, false);
    }

    @Override
    public void setProgressIndicator(boolean active, boolean cancelable) {
        // 当加载框拦截器不为空的情况
        if (null != mProgressInterceptor) {
            //通过实现类指定加载框显示形式
            mProgressInterceptor.setProgressIndicator(active);
            return;
        }
        setProgressDefaultIndicator(active, cancelable);
    }

    @Override
    public void setProgressDefaultIndicator(boolean active) {
        setProgressDefaultIndicator(active, false);
    }

    @Override
    public void setProgressDefaultIndicator(boolean active, boolean cancelable) {
        if (active) {
            mDialogFactory.showProgressBarDialog(cancelable);
        } else {
            mDialogFactory.hideProgressBarDialog();
        }
    }

    @Override
    public void setProgressOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        // 使用加载框拦截器的，不进行处理
        if (null != mProgressInterceptor) {
            return;
        }
        mDialogFactory.setOnKeyListenerForProgressBarDialog(onKeyListener);
    }

    @Override
    public void tip(@NonNull int strRes) {
        tip(ResourceUtil.getString(mActivity, strRes));
    }

    @Override
    public void tip(@NonNull String msg) {
        tip(msg, TipEnum.Normal);
    }

    @Override
    public void tip(@NonNull String msg, TipEnum tipEnum) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        switch (tipEnum) {
            case Error:
            case Normal:
            case MsgBox:
                mDialogFactory.showNetErrorDialog(msg);
                break;
            default:
                break;
        }
    }

    @Override
    public void showEmptyData() {
        showEmptyData(null, mDefaultAnchorView);
    }

    @Override
    public void showEmptyData(@Nullable CharSequence brief) {
        showEmptyData(brief, mDefaultAnchorView);
    }

    @Override
    public void showEmptyData(@NonNull View anchorView) {
        showEmptyData(null, anchorView);
    }

    @Override
    public void hindEmptyData() {
        hindEmptyData(mDefaultAnchorView);
    }

    @Override
    public void hindEmptyData(@NonNull View anchorView) {

    }

    @Override
    public void showEmptyData(@Nullable CharSequence brief, @NonNull View anchorView) {

    }

    @Override
    public void showNetworkError(@NonNull NetworkErrorCallback callback) {
        showNetworkError(mActivity.findViewById(android.R.id.content), callback);
    }

    @Override
    public void showNetworkError(@NonNull View anchorView, @NonNull final NetworkErrorCallback callback) {

    }

    @Override
    public void unbindPresenter() {
        if (null != mPresenter) {
            mPresenter.unSubscribe();
        }
    }

}
