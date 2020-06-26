
package cn.whdreamblog.mockhelper;

import android.support.annotation.NonNull;

import java.util.Objects;

import cn.whdreamblog.mockhelper.data.FakeDataSource;
import cn.whdreamblog.mockhelper.data.MockService;
import cn.whdreamblog.mockhelper.schedulers.BaseSchedulerProvider;
import cn.whdreamblog.mockhelper.schedulers.SchedulerProvider;
import okhttp3.Call;


/**
 * Enables injection of production implementations for
 * {@link } at compile time.
 * // TODO: 2020/5/24 将此处改为假数据
 */
public class Injection {
    private static FakeDataSource dataSource = new FakeDataSource();
    @NonNull
    public static MockService provideTasksRepository(@NonNull EasyMockHelperApplication context) {
        Objects.requireNonNull(context);
        return dataSource;
    }

    @NonNull
    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

    @NonNull
    public static Call.Factory getCallFactory(){
        return dataSource;
    }

}
