package cn.whdreamblog.mockhelper.widget;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * The params associated with CommonDialog.
 *
 * @author Sharry <a href="xiaoyu.zhu@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 4/24/2019 3:27 PM
 */
class DialogParams {

    // Dialog 内部的 View
    public View contentView = null;
    public SparseArray<CharSequence> textMap = new SparseArray<>();
    public SparseArray<View.OnClickListener> clickMap = new SparseArray<>();

    // Dialog 相关的接口
    public DialogInterface.OnCancelListener onCancelListener;
    public DialogInterface.OnDismissListener onDismissListener;
    public DialogInterface.OnKeyListener onKeyListener;

    // Dialog 的 Window 相关参数
    public boolean cancelable = true;
    public boolean canceledOnTouchOutside = true;
    public boolean fitStatusBar = false;
    public int gravity = Gravity.CENTER;
    public int animResId = 0;
    public int width = 0;
    public int height = 0;
    public float dimAmount = 0.5f;// 背景灰度

    public void apply(CommonDialog dialog) {
        // 1. 设置布局
        setupViews(dialog);
        // 2.设置Dialog的监听器
        setupListener(dialog);
        // 3. 设置Dialog的window相关参数
        setupWindowParams(dialog.getWindow());
    }

    private void setupViews(CommonDialog dialog) {
        DialogViewHelper viewHelper = new DialogViewHelper(contentView);
        if (viewHelper.getContentView() == null) {
            throw new RuntimeException("请先调用setContentView()方法加载Dialog布局");
        }
        // 绑定ViewHelper
        dialog.bindViewHelper(viewHelper);
        // 设置ContentView
        dialog.setContentView(viewHelper.getContentView());
        // 设置文本
        for (int i = 0; i < textMap.size(); i++) {
            viewHelper.setText(textMap.keyAt(i), textMap.valueAt(i));
        }
        // 设置点击事件
        for (int i = 0; i < clickMap.size(); i++) {
            viewHelper.setOnClickListener(clickMap.keyAt(i), clickMap.valueAt(i));
        }
    }

    private void setupListener(CommonDialog dialog) {
        dialog.setCancelable(cancelable); // 是否是可以取消的
        if (cancelable) {
            // 点击外部是否可以取消
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
        if (onCancelListener != null) {
            dialog.setOnCancelListener(onCancelListener);
        }
        if (onDismissListener != null) {
            dialog.setOnDismissListener(onDismissListener);
        }
        if (onKeyListener != null) {
            dialog.setOnKeyListener(onKeyListener);
        }
    }

    private void setupWindowParams(Window window) {
        if (animResId != 0) {
            window.setWindowAnimations(animResId);
        }
        // 去除Dialog的边界
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(gravity);

        // 设置Window宽高
        WindowManager.LayoutParams params = window.getAttributes();
        if (width != 0) {
            params.width = width;
        }
        if (height != 0) {
            params.height = height;
        }
        params.dimAmount = dimAmount;
        if (fitStatusBar) {
            params.flags = params.flags | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }
        window.setAttributes(params);
    }

}
