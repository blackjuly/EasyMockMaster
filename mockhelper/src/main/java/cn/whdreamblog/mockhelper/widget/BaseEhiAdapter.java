package cn.whdreamblog.mockhelper.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by 18834 on 2015/12/8.
 * Function：统一的adapter基类
 * Version: v1.0
 */
public abstract class BaseEhiAdapter<T> extends ArrayAdapter<T> {
  protected List<T> list;
  public BaseEhiAdapter(@NonNull Context context, @NonNull List<T> list) {
    //此处传输0是由于虽然继承ArrayAdapter,但是并不使用默认的textView，为了使用ArrayAdapter的Filterable功能
    //这样可以直接交给autoCompeteTextView去使用Adapter
    super(context, 0, list);
    this.list = list;
  }

  public List<T> getList() {
    return list;
  }

  @Override
  public T getItem(int position) {
    int listPosition = position;
    if (listPosition >= list.size()) {
      listPosition = list.size() - 1;
    }
    if (listPosition < 0) {
      listPosition = 0;
    }
    return list.get(listPosition);
  }

}
