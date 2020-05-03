package cn.whdreamblog.mockhelper.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * <pre>
 *     author : blackjuly
 *     e-mail : blackjuly@outlook.com
 *     time   : 2017年09月11日
 *     desc   : 为解决弹窗的内容textView单行居中对齐，多行居中对齐
 *     version: v1.0
 * </pre>
 */

public class AutoAlignTextView extends android.support.v7.widget.AppCompatTextView {
  private OnLayoutListener onLayoutListener;

  public void setOnLayoutListener(@Nullable OnLayoutListener onLayoutListener) {
    this.onLayoutListener = onLayoutListener;
  }

  /**
   * 布局监听器，解决Return the number of lines of text, or 0 if the internal Layout has not been built.的问题
   * 未布局直接获取行号会返回0,故监听布局完成，再获取行号
   */
  public interface OnLayoutListener {
    void onLayout(TextView textView);
  }

  public AutoAlignTextView(@NonNull Context context) {
    super(context);
  }

  public AutoAlignTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public AutoAlignTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    if (onLayoutListener == null) {//当布局监听为空时
      //默认 单行居中对齐，多行居左对齐
      int line = this.getLineCount();
      if (line <= 1) {
        this.setGravity(Gravity.CENTER);
      } else {
        this.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
      }
      return;
    }
    onLayoutListener.onLayout(this);
  }
}
