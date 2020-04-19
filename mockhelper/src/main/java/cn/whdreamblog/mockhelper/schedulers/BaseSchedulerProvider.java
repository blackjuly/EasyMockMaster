package cn.whdreamblog.mockhelper.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;


/**
 * Allow providing different types of
 */
public interface BaseSchedulerProvider {

  @NonNull
  Scheduler computation();

  @NonNull
  Scheduler io();

  @NonNull
  Scheduler ui();

  @NonNull
  Scheduler newThread();

  @NonNull
  Scheduler single();
}
