package cn.whdreamblog.mockhelper.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.whdreamblog.mockhelper.devdata.model.MocksResponse;
import cn.whdreamblog.mockhelper.devdata.source.devremote.MockRemote;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author  wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/4/18 17:07
 * desc : The class is used for .....
 */
public class MockUtils {
    public static final SimpleDateFormat FORMAT1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat FORMAT2 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat FORMAT3 = new SimpleDateFormat("HH:mm:ss");
    /**
     * sp 中存储有关开发工具的信息的文件名称
     */
    public static final boolean DEBUG = true;

    /**
     * 页面的开发者工具是否开启
     */
    @Target(ElementType.TYPE)
    @Retention(RUNTIME)
    public @interface DisableDevToolsActivity {
    }


    public static boolean isToolsAbled() {
        return DEBUG;
    }

    public static boolean isDevelopDebug() {
        return DEBUG;
    }



    public static void reStoreSelectMockList(){
        //缓存中没有选中列表的数据
        if (MockRemote.getSelectDataSet()== null || MockRemote.getSelectDataSet().isEmpty()){
            String json = MockSPUtil.get().getString(MockRemote.SELECT_MOCK_LIST_TAG,"");
            //sp中，没有选中列表数据，就放弃
            if (TextUtils.isEmpty(json)){
                return;
            }
            MockRemote.getSelectDataSet().addAll(GsonUtil.fromJsonList(json, MocksResponse.class));
        }
    }

    public static void saveSelectMockList(){
        //将数据进行缓存
        MockSPUtil.get().putString(MockRemote.SELECT_MOCK_LIST_TAG, GsonUtil.toJson(MockRemote.getSelectDataSet()));
    }

    public static void reopenLogger() {
        MyLogger.getLogger().init(true);
    }


    public static final String format(@NonNull SimpleDateFormat format, @NonNull String time) {


        Date date = parse(time);

        if (date == null || format == null) {
            return null;
        }

        return format.format(date);

    }


    public static final Date parse(@NonNull String time) {

        if (time == null) return null;

        try {
            time = time.replace("T", " ");
            Date date = FORMAT1.parse(time);
            return date;
        } catch (Exception e) {
            return null;
        }

    }
}
