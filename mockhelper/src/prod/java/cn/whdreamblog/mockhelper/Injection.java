/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.whdreamblog.mockhelper;

import android.support.annotation.NonNull;

import java.util.Objects;

import cn.whdreamblog.mockhelper.data.MockService;
import cn.whdreamblog.mockhelper.data.ServiceFactory;
import cn.whdreamblog.mockhelper.schedulers.BaseSchedulerProvider;
import cn.whdreamblog.mockhelper.schedulers.SchedulerProvider;
import okhttp3.Call;

/**
 * Enables injection of production implementations for
 * {@link } at compile time.
 * TODO: 2020/5/24 考虑单例？？
 */
public class Injection {

    public static MockService provideTasksRepository(@NonNull EasyMockHelperApplication context) {
        Objects.requireNonNull(context);
        return  ServiceFactory.getInstance().getRetrofit().create(MockService.class);
    }

    @NonNull
    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

    @NonNull
    public static Call.Factory getCallFactory(){
        return ServiceFactory.getInstance().getOkHttpClient();
    }
}
