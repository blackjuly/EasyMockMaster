package cn.whdreamblog.mockhelper.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Implementation of the {@link BaseSchedulerProvider} making all {@link Scheduler}s execute
 * synchronously so we can easily run assertions in our tests.
 * <p>
 * To achieve this, we are using the {@link io.reactivex.internal.schedulers.TrampolineScheduler} from the {@link Schedulers} class.
 */
public class ImmediateSchedulerProvider implements BaseSchedulerProvider {

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler newThread() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler single() {
        return Schedulers.trampoline();
    }
}
