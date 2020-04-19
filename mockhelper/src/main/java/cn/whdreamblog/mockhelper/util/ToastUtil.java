package cn.whdreamblog.mockhelper.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 弹框工具类
 */
public class ToastUtil {

    public static void makeText(String text) {
        Context context = ActivityTaskManager.getTop();
        if (context == null) {
            return;
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void makeText(Object text) {
        Context context = ActivityTaskManager.getTop();
        if (context == null) {
            return;
        }
        Toast.makeText(context, text.toString(), Toast.LENGTH_SHORT).show();
    }

    public static void makeText(int strRes) {
        Context context = ActivityTaskManager.getTop();
        if (context == null) {
            return;
        }
        Toast.makeText(context, context.getResources().getText(strRes), Toast.LENGTH_SHORT).show();
    }

}
