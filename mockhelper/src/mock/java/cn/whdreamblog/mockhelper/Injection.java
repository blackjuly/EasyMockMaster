
package cn.whdreamblog.mockhelper;

import android.support.annotation.NonNull;

import java.util.Objects;

import cn.whdreamblog.mockhelper.data.MockService;
import cn.whdreamblog.mockhelper.data.ServiceFactory;
import cn.whdreamblog.mockhelper.schedulers.BaseSchedulerProvider;
import cn.whdreamblog.mockhelper.schedulers.SchedulerProvider;


/**
 * Enables injection of production implementations for
 * {@link } at compile time.
 * // TODO: 2020/5/24 将此处改为假数据
 */
public class Injection {

    @NonNull
    public static MockService provideTasksRepository(@NonNull EasyMockHelperApplication context) {
        Objects.requireNonNull(context);
        return  ServiceFactory.getInstance().getRetrofit().create(MockService.class);
    }

    @NonNull
    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }



}
