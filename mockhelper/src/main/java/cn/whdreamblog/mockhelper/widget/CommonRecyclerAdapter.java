package cn.whdreamblog.mockhelper.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用的 RecyclerViewAdapter
 * <p>
 * 1. 子类需要复写 {@link #getLayoutResId} 方法, 根据 当前位置的数据 或者 直接根据位置 来返回不同的布局文件
 * <p>
 * 2. 子类需要复写 {@link #convert} 方法, 用于绑定数据, holder 中封装了开发时常用的方法
 * <p>
 * 3. 实现 ItemView 点击事件/ 长按事件, 复写 {@link #onItemClick}/{@link #onItemLongClick}
 * <p>
 * 4. 实现 ItemView 的 Sub view 点击事件/ 长按事件
 * step1: 在 convert 中调用 {@link CommonViewHolder#addClickListener}/{@link CommonViewHolder#addLongClickListener}
 * step2: 复写 {@link #onItemChildClick}/ {@link #onItemChildLongClick}
 *
 * @author Frank <a href="xiaoyu.zhu@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2017/10/11 9:30
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonViewHolder>
        implements CommonViewHolder.OnItemClickInteraction {

    private Context mContext;
    private List<T> mDataSet;
    private RecyclerView mRecyclerView;
    private LayoutInflater mInflater;

    public CommonRecyclerAdapter(Context context, List<T> dataSet) {
        this.mContext = context;
        this.mDataSet = dataSet;
        this.mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 根据当前位置获取不同的viewType
     */
    @Override
    public int getItemViewType(int position) {
        return getLayoutResId(mDataSet.get(position), position);
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mRecyclerView == null) {
            mRecyclerView = (RecyclerView) parent;
        }
        // 1. 先inflate数据
        View itemView = mInflater.inflate(viewType, parent, false);
        // 2. 构建 ViewHolder
        return new CommonViewHolder(itemView, viewType, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        convert(holder, mDataSet.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * item 的点击事件
     *
     * @param v        itemView
     * @param position item 的位置(不包括 Header / Footer / RefreshView / LoadView)
     */
    @Override
    public void onItemClick(View v, int position) {

    }

    /**
     * item 的长按击事件
     *
     * @param v        itemView
     * @param position item 的位置(不包括 Header / Footer / RefreshView / LoadView)
     */
    @Override
    public boolean onItemLongClick(View v, int position) {
        return false;
    }

    /**
     * Sub view 的点击事件
     *
     * @param v        itemView
     * @param position item 的位置(不包括 Header / Footer / RefreshView / LoadView)
     */
    @Override
    public void onItemChildClick(View v, int position) {

    }

    /**
     * Sub view 的长按事件
     *
     * @param v        itemView
     * @param position item 的位置(不包括 Header / Footer / RefreshView / LoadView)
     */
    @Override
    public boolean onItemChildLongClick(View v, int position) {
        return false;
    }

    /**
     * 多布局支持, 可以根据数据内容或者item的位置去自行设置布局文件
     *
     * @param data 当前position位置的数据, 根据数据返回不同的布局文件
     */
    protected abstract int getLayoutResId(T data, int position);

    /**
     * 绑定数据
     */
    protected abstract void convert(CommonViewHolder holder, T data, int position);

    /**
     * 获取数据集合
     */
    public List<T> getDataSet() {
        return mDataSet;
    }

    /**
     * 获取上下文
     */
    public Context getContext() {
        return mContext;
    }
}
