package cn.whdreamblog.mockhelper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

/**
 * 轻量级存储的工具类
 */
public class MockSPUtil {
    private static MockSPUtil spUtil;
    private Context context;

    static {
        spUtil = new MockSPUtil();
    }

    private MockSPUtil() {
    }

    public static MockSPUtil get() {
        return spUtil;
    }

    public  void init(Context context) {
        this.context = context;
    }

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "mock_data";

    public String getString(@NonNull String key, @Nullable String defaultValue) {
        return getString(FILE_NAME, key, defaultValue);
    }

    public void putString(@NonNull String key, @NonNull String value) {
        putString(FILE_NAME, key, value);
    }

    public String getString(@NonNull String fileName, @NonNull String key, @Nullable String defaultValue) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public void putString(@NonNull String fileName, @NonNull String key, @NonNull String value) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public int getInt(@NonNull String key, int defaultValue) {
        return getInt(FILE_NAME, key, defaultValue);
    }

    public void putInt(@NonNull String key, int value) {
        putInt(FILE_NAME, key, value);
    }

    public int getInt(@NonNull String fileName, @NonNull String key, int defaultValue) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public void putInt(@NonNull String fileName, @NonNull String key, int value) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return getBoolean(FILE_NAME, key, defaultValue);
    }

    public void putBoolean(@NonNull String key, boolean value) {
        putBoolean(FILE_NAME, key, value);
    }

    public boolean getBoolean(@NonNull String fileName, @NonNull String key, boolean defaultValue) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public void putBoolean(@NonNull String fileName, @NonNull String key, boolean value) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public float getFloat(@NonNull String key, float defaultValue) {
        return getFloat(FILE_NAME, key, defaultValue);
    }

    public void putFloat(@NonNull String key, float value) {
        putFloat(FILE_NAME, key, value);
    }

    public float getFloat(@NonNull String fileName, @NonNull String key, float defaultValue) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public void putFloat(@NonNull String fileName, @NonNull String key, float value) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public long getLong(@NonNull String key, long defaultValue) {
        return getLong(FILE_NAME, key, defaultValue);
    }

    public void putLong(@NonNull String key, long value) {
        putLong(FILE_NAME, key, value);
    }

    public long getLong(@NonNull String fileName, @NonNull String key, long defaultValue) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public void putLong(@NonNull String fileName, @NonNull String key, long value) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        remove(FILE_NAME, key);
    }

    public void remove(String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        clear(FILE_NAME);
    }

    public void clear(String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return contains(FILE_NAME, key);
    }

    public boolean contains(String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return getAll(FILE_NAME);
    }

    public Map<String, ?> getAll(String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getAll();
    }

}
