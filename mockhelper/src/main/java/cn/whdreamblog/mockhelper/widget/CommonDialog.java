package cn.whdreamblog.mockhelper.widget;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 自定 Dialog, 使用 Builder 构建, 涵盖常用开发中所需要使用的方法
 *
 * @author Sharry <a href="xiaoyu.zhu@1hai.cn">Contact me.</a>
 * @version 2.0
 * @since 4/24/2019 3:26 PM
 */
public class CommonDialog extends android.app.Dialog {

    private DialogViewHelper mViewHelper;

    private CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 绑定DialogViewHelper, 用于处理view查询
     */
    void bindViewHelper(DialogViewHelper viewHelper) {
        mViewHelper = viewHelper;
    }

    /**
     * 代替findViewById
     */
    public <T extends View> T getView(int viewId) {
        return mViewHelper.viewById(viewId);
    }

    public static class Builder {

        private final DialogParams P = new DialogParams();
        private Context mContext;
        private int mThemeResId;

        public Builder(Context context) {
            this(context, 0);
        }

        public Builder(Context context, int themeResId) {
            mContext = context;
            mThemeResId = themeResId;
        }

        /**
         * 设置ContentView
         */
        public Builder setLayoutRes(int layoutResId) {
            P.contentView = LayoutInflater.from(mContext).inflate(layoutResId, null);
            return this;
        }

        /**
         * 是否可以取消当前Dialog
         * 默认为true
         */
        public Builder setCancelable(boolean cancelable) {
            P.cancelable = cancelable;
            return this;
        }

        /**
         * 是否需要适应沉浸式状态栏
         * 默认为 false
         */
        public Builder setFitStatusBar(boolean fitStatusBar) {
            P.fitStatusBar = fitStatusBar;
            return this;
        }

        /**
         * 点击Dialog外部是否取消Dialog的显示
         * 只有在cancelable为true时, 该设置才会生效
         */
        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            P.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        /**
         * 设置Dialog的监听器
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.onKeyListener = onKeyListener;
            return this;
        }

        /**
         * 设置文本
         */
        public Builder setText(int viewId, CharSequence text) {
            P.textMap.put(viewId, text);
            return this;
        }

        /**
         * 通过Id给View绑定点击事件
         */
        public Builder setOnClickListener(int viewId, View.OnClickListener listener) {
            P.clickMap.put(viewId, listener);
            return this;
        }

        /**
         * 设置Gravity
         */
        public Builder setGravity(int gravity) {
            P.gravity = gravity;
            return this;
        }

        /**
         * 填充屏幕的宽度
         */
        public Builder setFillScreenWidth() {
            setWidthScale(1.0f);
            return this;
        }

        /**
         * 指定宽度缩放的百分比
         */
        public Builder setWidthScale(float widthPercent) {
            if (widthPercent > 1f) {
                throw new IllegalArgumentException("Dialog宽高的百分比不能超过100%");
            }
            setWidth((int) (mContext.getResources().getDisplayMetrics().widthPixels * widthPercent));
            return this;
        }

        /**
         * 设置Window的高度
         */
        public Builder setWidth(int width) {
            P.width = width;
            return this;
        }

        /**
         * 填充屏幕的高度
         */
        public Builder setFillScreenHeight() {
            setHeightScale(1.0f);
            return this;
        }

        /**
         * 指定高度缩放的百分比
         */
        public Builder setHeightScale(float heightPercent) {
            if (heightPercent > 1f) {
                throw new IllegalArgumentException("Dialog宽高的百分比不能超过100%");
            }
            setWindowHeight((int) (mContext.getResources().getDisplayMetrics().heightPixels * heightPercent));
            return this;
        }

        /**
         * 设置Window的高度
         */
        public Builder setWindowHeight(int height) {
            P.height = height;
            return this;
        }

        /**
         * 指定Dialog相对于屏幕缩放的比(根据屏幕百分比)
         */
        public Builder setWindowScale(float widthPercent, float heightPercent) {
            setWidthScale(widthPercent);
            setHeightScale(heightPercent);
            return this;
        }

        /**
         * 添加动画
         */
        public Builder addAnimation(@StyleRes int animResId) {
            P.animResId = animResId;
            return this;
        }

        /**
         * 设置Window背景透明度
         */
        public Builder setWindowDimAmount(float dimAmount) {
            P.dimAmount = dimAmount;
            return this;
        }

        public CommonDialog create() {
            final CommonDialog dialog = new CommonDialog(mContext, mThemeResId);
            // 配置参数
            P.apply(dialog);
            return dialog;
        }

        public CommonDialog show() {
            final CommonDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }

}
