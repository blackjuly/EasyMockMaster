package cn.whdreamblog.mockhelper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import cn.whdreamblog.mockhelper.schedulers.BaseSchedulerProvider;
import cn.whdreamblog.mockhelper.schedulers.SchedulerProvider;
import cn.whdreamblog.mockhelper.util.ErrorUtil;
import cn.whdreamblog.mockhelper.util.MyLogger;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/18 18:30
 * desc :  base class BasePresenterImp
 */
public class BasePresenterImp<T extends BaseView> implements BasePresenter {

    protected final MyLogger myLogger = MyLogger.getLogger(BasePresenterImp.class.getSimpleName());  // MyLogger 日志工具
    protected final CompositeDisposable compositeSubscription = new CompositeDisposable();           //管理所有的Disposable

    protected final T view;

    protected final BaseSchedulerProvider schedulerProvider;


    public BasePresenterImp(T view) {
        this.view = view;
        if (view != null) {
            view.bindPresenter(this);
        }
        this.schedulerProvider = SchedulerProvider.getInstance();

    }



    /**
     * 取消注册
     */
    @Override
    public void unSubscribe() {
        compositeSubscription.clear();
    }

    /**
     * 处理业务的时候的统一处理,这里是纯粹的处理错误的,不能添加其他不相关的代码,比如让视图关闭对话框这种
     *
     * @param t 错误
     * @return 返回一个值表示是否已经处理了
     */
    public final boolean normalErrorSolve(@NonNull Throwable t) {
        myLogger.e(t);
       if (ErrorUtil.isNetWorkError(t)) {
            view.tip("网络异常,请检查您的网络 \n error : " + t.getMessage(), BaseViewSupport.TipEnum.Error);
            return true;
        } else if (ErrorUtil.isServerError(t)) {
            view.tip("服务器发生异常", BaseViewSupport.TipEnum.Error);
            return true;
        } else if (ErrorUtil.isServerTimeoutError(t)) {
            view.tip("请求服务器超时，请稍后重试", BaseViewSupport.TipEnum.Error);
        } else {
            view.tip("请求异常，请稍后重试 \n error message:" + t.getMessage(), BaseViewSupport.TipEnum.Error);
        }

        return false;
    }

    /**
     * @see #normalErrorSolveOnlyRecord(Throwable)
     * 只用作记录筛选记录一些接口的问题，而不进行弹窗
     */
    public final boolean normalErrorSolveOnlyRecord(@NonNull Throwable t) {

       if (ErrorUtil.isNetWorkError(t)) {
            return true;
        } else if (ErrorUtil.isServerError(t)) {
            return true;
        } else if (ErrorUtil.isServerTimeoutError(t)) {
            return true;
        }
        return false;
    }
    // ==========================================没有弹框==========================start

    protected final <T> void subscribeNoDialog(@NonNull Observable<T> o, @NonNull Consumer<T> success) {
        subscribe(o, success, null, null, false, false);
    }

    protected final <T> void subscribeNoDialog(@NonNull Observable<T> o, @NonNull Consumer<T> success, @NonNull Consumer<Throwable> fail) {
        subscribe(o, success, fail, null, false, false);
    }

    protected final <T> void subscribeNoDialog(@NonNull Observable<T> o, @NonNull Consumer<T> success,
                                               @NonNull Consumer<Throwable> fail, @NonNull final Action complete) {
        subscribe(o, success, fail, complete, false, false);
    }

    protected final <T> void subscribeNoDialog(@NonNull Observable<T> o, @NonNull Consumer<T> success, @NonNull final Action complete) {
        subscribe(o, success, null, complete, false, false);
    }

    // ==========================================没有弹框==========================end

    // ==========================================不能取消弹框==========================start

    public final <T> void subscribeCannotCancel(@NonNull Observable<T> o, @NonNull Consumer<T> success) {
        subscribe(o, success, null, null, true, false);
    }

    public final <T> void subscribeCannotCancel(@NonNull Observable<T> o, @NonNull Consumer<T> success, @NonNull Consumer<Throwable> fail) {
        subscribe(o, success, fail, null, true, false);
    }

    protected final <T> void subscribeCannotCancel(@NonNull Observable<T> o, @NonNull Consumer<T> success, @NonNull final Action complete) {
        subscribe(o, success, null, complete, true, false);
    }

    protected final <T> void subscribeCannotCancel(@NonNull Observable<T> o, @NonNull Consumer<T> success, @NonNull Consumer<Throwable> fail, @NonNull final Action complete) {
        subscribe(o, success, fail, complete, true, false);
    }

    // ==========================================不能取消弹框==========================end

    /**
     * 简化耗时操作
     * 1.弹框
     * 2.弹框可取消
     *
     * @see #subscribe(Observable, Consumer, Consumer, Action, boolean, boolean)
     */
    protected final <T> void subscribe(@NonNull Observable<T> o, @NonNull Consumer<T> success) {
        subscribe(o, success, null, null, true, true);
    }

    /**
     * 简化耗时操作
     * 有弹窗 成功失败都自己处理
     *
     * @see #subscribe(Observable, Consumer, Consumer, Action, boolean, boolean)
     */
    protected final <T> void subscribe(@NonNull Observable<T> o, @NonNull Consumer<T> success, @Nullable Consumer<Throwable> fail) {
        subscribe(o, success, fail, null, true, true);
    }

    /**
     * 简化耗时操作 针对 fail complete 都使用默认情况
     *
     * @see #subscribe(Observable, Consumer, Consumer, Action, boolean, boolean)
     */
    protected final <T> void subscribe(@NonNull Observable<T> o, @NonNull Consumer<T> success, final boolean isShowDialog) {
        subscribe(o, success, null, null, isShowDialog, true);
    }

    /**
     * 简化耗时操作 针对 complete 使用默认情况
     *
     * @see #subscribe(Observable, Consumer, Consumer, Action, boolean, boolean)
     */
    protected final <T> void subscribe(@NonNull Observable<T> o, @NonNull Consumer<T> success, @Nullable Consumer<Throwable> fail, final boolean isShowDialog) {
        subscribe(o, success, fail, null, isShowDialog, true);
    }

    /**
     * note:只有错误或者完成的时候才算是一个Observable结束了,所以只能在错误和完成的回调中关闭对话框
     *
     * @param o            观察源
     * @param success      成功回调
     * @param fail         失败回调
     * @param complete     完成的回调
     * @param isShowDialog 是否启用加载框
     * @param cancelable   是否可以点击阴影取消记载
     * @param <T>          对应数据类型的泛型
     */
    protected final <T> void subscribe(@NonNull Observable<T> o,
                                       @NonNull final Consumer<T> success,
                                       @Nullable final Consumer<Throwable> fail,
                                       @Nullable final Action complete,
                                       final boolean isShowDialog,
                                       final boolean cancelable) {

        final Consumer<T> mAccept = new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                success.accept(t);
            }
        };

        final Consumer<Throwable> mError = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception { // 出错处理
                if (view != null && isShowDialog) {
                    view.setProgressIndicator(false);
                }
                if (fail != null) {
                    fail.accept(t);
                } else { // default
                    normalErrorSolve(t);
                }
            }
        };

        final Action mComplete = new Action() { // 关闭菊花
            @Override
            public void run() throws Exception {
                if (view != null && isShowDialog) {
                    view.setProgressIndicator(false);
                }
                if (complete != null) {
                    complete.run();
                }
            }
        };

        Disposable disposable = o
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                // 订阅的时候弹出菊花
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (view != null && isShowDialog) {
                            view.setProgressIndicator(true, cancelable);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (view != null && isShowDialog) {
                            view.setProgressIndicator(false);
                        }
                    }
                })
                .subscribeOn(schedulerProvider.ui())
                .subscribe(mAccept, mError, mComplete);

        compositeSubscription.add(disposable);

    }

    protected void addDisposable(Disposable d) {
        if (compositeSubscription == null) {
            return;
        }
        compositeSubscription.add(d);
    }

}
