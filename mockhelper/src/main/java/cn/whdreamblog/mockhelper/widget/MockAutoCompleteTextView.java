package cn.whdreamblog.mockhelper.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Filterable;
import android.widget.ListAdapter;

/**
 * <pre>
 *     author : blackjuly
 *     e-mail : blackjuly@outlook.com
 *     time   : 2017年09月21日
 *     desc   : 自动补全搜索输入框
 *     version:
 * </pre>
 */

public class MockAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {
  public MockAutoCompleteTextView(@NonNull Context context) {
    super(context);
  }

  public MockAutoCompleteTextView(@NonNull Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MockAutoCompleteTextView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

    private View emptyView;


    private DataEmptyLister dataEmptyLister;

    public interface DataEmptyLister {
        /**
         * 监听数据为空
         *
         * @param isEmpty true 为空 false 不空
         */
        void isDataChange(boolean isEmpty);
    }


    final private DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onInvalidated() {
            checkIfEmpty();
        }
    };

    private void checkIfEmpty() {
        ListAdapter adapter = getAdapter();
        if (adapter != null && emptyView != null) {
            if (adapter.getCount() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                if (dataEmptyLister != null) {
                    dataEmptyLister.isDataChange(true);
                }
            } else {
                emptyView.setVisibility(View.GONE);
                if (dataEmptyLister != null) {
                    dataEmptyLister.isDataChange(false);
                }
            }
        }
    }

    @Override
    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
        super.setAdapter(adapter);
        adapter.registerDataSetObserver(observer);
    }

    public View getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public DataEmptyLister getDataEmptyLister() {
        return dataEmptyLister;
    }

    public void setDataEmptyLister(DataEmptyLister dataEmptyLister) {
        this.dataEmptyLister = dataEmptyLister;
    }
}
