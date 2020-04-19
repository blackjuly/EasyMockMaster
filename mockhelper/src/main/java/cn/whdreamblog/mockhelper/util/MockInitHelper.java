package cn.whdreamblog.mockhelper.util;

import android.content.Context;


/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/18 17:14
 * desc : The class is used for init helper
 */
public class MockInitHelper {
    private static MockInitHelper helper;
    private Context context;
    private MockSPUtil spUtil;


    static {
        helper = new MockInitHelper();
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
        initDevelop();
    }

    private void initDevelop() {
        MockSPUtil.get().init(context);
        spUtil = MockSPUtil.get();
    }

    public static MockInitHelper get() {
        return helper;
    }

    public MockSPUtil getSpUtil() {
        return spUtil;
    }
}
