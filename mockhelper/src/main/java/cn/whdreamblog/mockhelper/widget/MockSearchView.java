package cn.whdreamblog.mockhelper.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import java.util.List;

import cn.whdreamblog.mockhelper.R;
/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/18 15:01
 * desc : The class is used for search page
 */
public class MockSearchView<T> extends FrameLayout {
    private MockAutoCompleteTextView acTvSearchBox;
    private AutoAlignTextView tvCancelBtn;
    private BaseEhiAdapter<T> adapter;
    private List<T> list;

    public MockSearchView(@NonNull Context context) {
        this(context, null);
    }

    public MockSearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MockSearchView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.module_mock_widget_mock_search_view, this);
        initView();
    }

    private void initView() {
        acTvSearchBox = (MockAutoCompleteTextView) findViewById(R.id.ac_tv_search_box);
        tvCancelBtn = (AutoAlignTextView) findViewById(R.id.tv_cancel_btn);
    }

    public BaseEhiAdapter<T> getAdapter() {
        return adapter;
    }

    /**
     * 设置了，目前发现基础adapter的重写影响到了
     * 搜索
     */
    @Deprecated
    public void setAdapter(@NonNull BaseEhiAdapter<T> adapter) {
        this.adapter = adapter;
        acTvSearchBox.setAdapter(adapter);
    }

    public List<T> getList() {
        return list;
    }

    /**
     * 直接new adapter时，可以通过setView中的list处理数据变化问题
     *
     * @param list 设置不为空的list
     */
    public void setList(@NonNull List<T> list) {
        this.list = list;
        if (adapter == null) {
            throw new UnsupportedOperationException("adapter不为空，才可以设置数据");
        }
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    //TODO 探究为啥监听事件设置 从构造方法设置 也可以完成监听事件的设置
    public void setCancelBtnListener(final OnClickCancelBtnListener cancelBtnListener) {
        tvCancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBtnListener.onClick(v);
            }
        });
    }

    /**
     * 取消点击事件
     */
    public interface OnClickCancelBtnListener {
        void onClick(View view);
    }

    /**
     * 为搜索框指定item点击事件
     *
     * @param onItemClickListener item设置点击事件
     */
    public void setSearchBoxItemListener(AdapterView.OnItemClickListener onItemClickListener) {
        acTvSearchBox.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 监听搜索文字字数该改变
     *
     * @param textWatcher 监听器
     */
    public void setSearchBoxTextWatcher(TextWatcher textWatcher) {
        acTvSearchBox.addTextChangedListener(textWatcher);
    }

    public void setMaxLength(int maxLength) {
        acTvSearchBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * 设置第几个数字开始显示
     *
     * @param num 指定数字数 默认为2个
     */
    public void setCompletionThreshold(int num) {
        acTvSearchBox.setThreshold(num);
    }

    public MockAutoCompleteTextView getAcTvSearchBox() {
        return acTvSearchBox;
    }

    public View getEmptyView() {
        return acTvSearchBox.getEmptyView();
    }

    public void setEmptyView(View emptyView) {
        this.acTvSearchBox.setEmptyView(emptyView);
    }
}
