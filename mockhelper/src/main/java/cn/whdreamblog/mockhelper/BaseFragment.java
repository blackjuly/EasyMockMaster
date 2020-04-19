package cn.whdreamblog.mockhelper;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/18 18:29
 * desc : fragment base class
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView<T> {

    /*
      Sub class can use this fields.
     */
    protected T presenter;
    protected IDialogFactory dialogFactory;

    /*
      Private fields.
     */
    private BaseViewSupport<T> mImpl;        // Perform BaseViewSupport logistical actual.
    private View mFragmentView;                  // Fragment container.
    private boolean mIsVisibleToUser = false;    // Fragment 页面是否可见
    private boolean mIsViewInitiated = false;    // 控件是否完成初始化

    /**
     * Invoke time:
     * 1. Invoke before onAttach.
     * 2. Invoke when fragment visibility changed.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisibleToUser = isVisibleToUser;
        handleLazyWork();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (null != getArguments()) {
            parseArguments(getArguments());
        }
        dialogFactory = new CommonDialogFactory(getActivity());
        mImpl = new BaseViewSupportImpl<>(context, dialogFactory);
        if (presenter == null) {
            presenter = createPresenter();
        }
        mImpl.bindPresenter(presenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(getLayoutResId(), container, false);
        return mFragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        // 此时Fragment已经初始化完毕, 控件已可用, 不会出现空指针异常
        mIsViewInitiated = true;
        initData();
        handleLazyWork();
    }

    @Override
    public void onDestroyView() {
        mIsVisibleToUser = false;
        mIsViewInitiated = false;
        unbindPresenter();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /*================ 以下实现 BaseView 中相关方法的实现  ================*/


    @Override
    public void bindPresenter(T presenter) {
        this.presenter = presenter;

    }

    @Override
    public T getPresenter() {
        return this.presenter;
    }

    @Override
    public void unbindPresenter() {
        mImpl.unbindPresenter();
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
    public void tip(@StringRes int strRes) {
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

    /*================ 以上实现 BaseView 中相关方法的实现  ================*/

    /**
     * U can parse arguments that {@link #setArguments(Bundle)}
     */
    protected void parseArguments(@NonNull Bundle arguments) {
    }

    /**
     * Create a presenter related on this view.
     *
     * @return a presenter related on this view.
     */
    protected T createPresenter() {
        return null;
    }

    /**
     * Get layout resource associated with this Fragment.
     */
    public abstract int getLayoutResId();

    /**
     * Initialize views associated with this Fragment.
     */
    protected void initViews(View container) {
    }

    /**
     * Initialize data associated with this Fragment.
     */
    protected void initData() {
    }

    /**
     * Perform lazy work associated with this Fragment.
     */
    protected void performLazyWork() {
    }

    protected <T extends View> T viewById(int id) {
        return (T) mFragmentView.findViewById(id);
    }

    /**
     * 当前的 Fragment 是否为可见状态
     */
    protected boolean isVisibleToUser() {
        return mIsVisibleToUser;
    }

    /**
     * 当前 Fragment 的 View 是否已经初始化了
     */
    protected boolean isViewInitiated() {
        return mIsViewInitiated;
    }


    /**
     * Handle lazy work associated with the Fragment.
     */
    private void handleLazyWork() {
        // 只有当 Fragment 可见且控件均已初始化完成时执行懒加载的方法
        if (mIsVisibleToUser && mIsViewInitiated) {
            performLazyWork();
        }
    }
}
