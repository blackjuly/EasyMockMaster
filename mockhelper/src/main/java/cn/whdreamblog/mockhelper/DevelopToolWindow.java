package cn.whdreamblog.mockhelper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import cn.whdreamblog.mockhelper.main.DevelopMainActivity;
import cn.whdreamblog.mockhelper.util.MockUtils;
import cn.whdreamblog.mockhelper.widget.ToolsTextView;

/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/18 17:31
 * desc : The class is used for show little ball
 */
public class DevelopToolWindow {


    private DevelopToolWindow() {
    }


    private static final String WIFI_DEFAULT_DESC = "未知";


    /**
     * 表示手机什么wifi
     */
    private static String wifiDesc = WIFI_DEFAULT_DESC;


    public static boolean canInsertToolsView(final Activity activity) {
        return activity.getWindow().getDecorView().findViewById(R.id.mock_suspend_content) == null;
    }

    /**
     * 插入工具视图
     *
     * @param activity
     */
    public static void fitDevelopView(final Activity activity) {

        boolean isDevelopPage = activity.getClass().isAnnotationPresent(MockUtils.DisableDevToolsActivity.class);
        if (isDevelopPage) {
            return;
        }

        if (!canInsertToolsView(activity)) {
            return;
        }

        // 添加悬浮视图
        View suspendContent = View.inflate(activity, R.layout.mock_layout_entrance, null);
        suspendContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewGroup contentView = (ViewGroup) activity.getWindow().getDecorView();
        contentView.addView(suspendContent);

        if (MockUtils.isToolsAbled()) {
            initDevelopListener(activity, suspendContent);
        }

    }

    private static void initDevelopListener(final Activity act, View suspendContent) {

        final boolean[] flags = new boolean[1];

        final ToolsTextView tvDevelop = suspendContent.findViewById(R.id.mock_tv_develop);

        tvDevelop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flags[0]) {
                    return;
                }
                DevelopMainActivity.startActivity(act);
            }
        });

        tvDevelop.setOnTouchListener(new View.OnTouchListener() {


            private float downX = 0f;
            private float downY = 0f;

            private int left;
            private int top;

            @Override
            public boolean onTouch(View v, MotionEvent e) {

                int actionMasked = e.getActionMasked();
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flags[0] = false;

                        downX = e.getRawX();
                        downY = e.getRawY();

                        left = tvDevelop.getLeft();
                        top = tvDevelop.getTop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int diffX = (int) (e.getRawX() - downX);
                        int diffY = (int) (e.getRawY() - downY);

                        if (flags[0]) {

                            tvDevelop.layout(
                                    left + diffX,
                                    top + diffY,
                                    left + tvDevelop.getWidth() + diffX,
                                    top + tvDevelop.getHeight() + diffY
                            );
                        } else {
                            if (Math.abs(diffX) > 100 && Math.abs(diffY) > 100) {

                                flags[0] = true;

                                tvDevelop.layout(
                                        left + diffX,
                                        top + diffY,
                                        left + tvDevelop.getWidth() + diffX,
                                        top + tvDevelop.getHeight() + diffY
                                );

                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                return false;
            }


        });
    }

    /**
     * 每一个界面出现了都会调用
     *
     * @param activity
     */
    public static void updateView(final Activity activity) {
        updateNormalInfo(activity);
    }

    private static void updateNormalInfo(@NonNull final Activity act) {

        if (act == null) {
            return;
        }

        View toolsView = act.getWindow().getDecorView().findViewById(R.id.mock_suspend_content);
        if (toolsView == null) {
            return;
        }

        ToolsTextView tvDevelop = toolsView.findViewById(R.id.mock_tv_develop);

        if (MockUtils.isToolsAbled()) {
            tvDevelop.setAlpha(1f);
        } else {
            tvDevelop.setAlpha(0.4f);
            tvDevelop.setOnClickListener(null);
            tvDevelop.setOnLongClickListener(null);
            tvDevelop.setOnTouchListener(null);
            tvDevelop.setClickable(false);
            tvDevelop.setLongClickable(false);
        }

    }


}
