package cn.whdreamblog.mockhelper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/6 14:20
 * desc : The class is used for show in your app top
 */
public class ToolsTextView extends AppCompatTextView {

    public ToolsTextView(Context context) {
        this(context, null);
    }

    public ToolsTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        p.setAntiAlias(true);
        p.setColor(bgColor);
    }

    private Paint p = new Paint();

    @ColorInt
    private int bgColor = Color.parseColor("#00CC88");

    @Override
    protected void onDraw(Canvas c) {
        c.drawCircle(
                getWidth()*1f / 2,
                getHeight()*1f / 2,
                Math.min(
                        (getWidth() - getPaddingLeft() - getPaddingRight()) / 2,
                        (getHeight() - getPaddingTop() - getPaddingBottom()) / 2
                ),
                p
        );
        super.onDraw(c);
    }

    public void setBgColor(@ColorInt int bgColor) {
        this.bgColor = bgColor;
        p.setColor(bgColor);
        invalidate();
    }

}
