package cn.whdreamblog.mockhelper.widget;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Dialog 中 View 的辅助处理类
 *
 * @author Sharry <a href="xiaoyu.zhu@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 4/24/2019 3:26 PM
 */
class DialogViewHelper {

    private View mContentView;
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(View contentView) {
        mContentView = contentView;
        mViews = new SparseArray<>();
    }

    /**
     * 获取ContentView
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * 给View设置Text(View extends TextView)
     */
    public void setText(int viewId, CharSequence text) {
        TextView tv;
        WeakReference<View> wr = mViews.get(viewId);
        if (wr == null) {
            tv = viewById(viewId);
            wr = new WeakReference<View>(tv);
            mViews.put(viewId, wr);
        } else {
            tv = (TextView) wr.get();
        }
        tv.setText(text);
    }

    /**
     * 给View设置点击事件
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view;
        WeakReference<View> wr = mViews.get(viewId);
        if (wr == null) {
            view = viewById(viewId);
            wr = new WeakReference<View>(view);
            mViews.put(viewId, wr);
        } else {
            view = wr.get();
        }
        view.setOnClickListener(listener);
    }

    public <T extends View> T viewById(int viewId) {
        return (T) mContentView.findViewById(viewId);
    }
}
