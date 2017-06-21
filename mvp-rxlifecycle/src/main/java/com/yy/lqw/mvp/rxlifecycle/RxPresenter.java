package com.yy.lqw.mvp.rxlifecycle;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.yy.lqw.mvp.Delegate;
import com.yy.lqw.mvp.Presenter;

import javax.annotation.Nonnull;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by lunqingwen on 2017/3/23.
 */

public abstract class RxPresenter implements Presenter, LifecycleProvider<PresenterEvent> {
    private final BehaviorSubject<PresenterEvent> mLifecycleSubject = BehaviorSubject.create();

    @Override
    public void onAttachedToView(Delegate delegate) {
        mLifecycleSubject.onNext(PresenterEvent.ATTACH);
    }

    @Override
    public void onDetachedFromView(Delegate delegate) {
        mLifecycleSubject.onNext(PresenterEvent.DETACH);
    }

    @Nonnull
    @Override
    public Observable<PresenterEvent> lifecycle() {
        return mLifecycleSubject.asObservable();
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@Nonnull PresenterEvent event) {
        return RxLifecycle.bindUntilEvent(mLifecycleSubject, event);
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return bindUntilEvent(PresenterEvent.DETACH);
    }
}
