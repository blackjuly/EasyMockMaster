package cn.whdreamblog.mockhelper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.whdreamblog.mockhelper.util.AppBarUtil;
import cn.whdreamblog.mockhelper.util.MyLogger;
import cn.whdreamblog.mockhelper.util.Style;

/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/18 17:44
 * desc : The class is used for base activity
 */
public class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseViewSupport<T> {

    // Sub class can use Presenter from this field.
    protected T presenter;
    // Sub class can use MyLogger from this field.
    protected MyLogger myLogger = MyLogger.getLogger(BaseActivity.class.getSimpleName());
    // Sub class can use IDialogFactory from this field.
    protected IDialogFactory dialogFactory;
    // Perform BaseViewSupport logistical actual.
    private BaseViewSupport<T> mImpl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置沉浸式状态栏
        AppBarUtil.with(this).setStatusBarStyle(Style.TRANSPARENT).apply();
        // 初始化相关参数
        initArgs();
    }

    private void initArgs() {
        // Initialize DialogFactory
        dialogFactory = new CommonDialogFactory(this);
        // Initialize BaseViewSupportImpl
        mImpl = new BaseViewSupportImpl<>(this, dialogFactory);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unbindPresenter();
        super.onDestroy();
    }

    /*================ 以下实现 BaseViewSupport 中相关方法的实现  ================*/
    @Override
    public void bindPresenter(T presenter) {
        this.presenter = presenter;
        mImpl.bindPresenter(presenter);
    }

    @Override
    public T getPresenter() {
        return this.presenter;
    }

    @Override
    public void setProgressInterceptor(ProgressInterceptor progressInterceptor) {
        mImpl.setProgressInterceptor(progressInterceptor);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        mImpl.setProgressIndicator(active);
    }

    @Override
    public void setProgressIndicator(boolean active, boolean cancelable) {
        mImpl.setProgressIndicator(active, cancelable);
    }

    @Override
    public void setProgressDefaultIndicator(boolean active) {
        mImpl.setProgressDefaultIndicator(active);
    }

    @Override
    public void setProgressDefaultIndicator(boolean active, boolean cancelable) {
        mImpl.setProgressDefaultIndicator(active, cancelable);
    }

    @Override
    public void setProgressOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        mImpl.setProgressOnKeyListener(onKeyListener);
    }

    @Override
    public void tip(@NonNull int strRes) {
        mImpl.tip(strRes);
    }

    @Override
    public void tip(@NonNull String msg) {
        mImpl.tip(msg);
    }

    @Override
    public void tip(@NonNull String msg, TipEnum tipEnum) {
        mImpl.tip(msg, tipEnum);
    }

    @Override
    public void showEmptyData() {
        mImpl.showEmptyData();
    }

    @Override
    public void showEmptyData(@Nullable CharSequence brief) {
        mImpl.showEmptyData(brief);
    }

    @Override
    public void showEmptyData(@NonNull View anchorView) {
        mImpl.showEmptyData(anchorView);
    }

    @Override
    public void showEmptyData(@Nullable CharSequence brief, @NonNull View anchorView) {
        mImpl.showEmptyData(brief, anchorView);
    }

    @Override
    public void hindEmptyData() {
        mImpl.hindEmptyData();
    }

    @Override
    public void hindEmptyData(@NonNull View anchorView) {
        mImpl.hindEmptyData(anchorView);
    }

    @Override
    public void showNetworkError(@NonNull NetworkErrorCallback callback) {
        mImpl.showNetworkError(callback);
    }

    @Override
    public void showNetworkError(@NonNull View anchorView, @NonNull NetworkErrorCallback callback) {
        mImpl.showNetworkError(anchorView, callback);
    }

    @Override
    public void unbindPresenter() {
        mImpl.unbindPresenter();
    }

    /*================ 以上实现 BaseViewSupport 中相关方法的实现  ================*/




}
