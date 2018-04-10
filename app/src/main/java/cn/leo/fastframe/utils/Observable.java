package cn.leo.fastframe.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.util.ArrayList;

/**
 * Created by Leo on 2018/4/10.
 */

public class Observable<MSG> implements Handler.Callback {


    public abstract class Observer implements LifecycleObserver {
        private Observable mObservable;

        public abstract void notify(MSG msg);

        private void onRegister(Observable observable) {
            mObservable = observable;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            if (mObservable != null)
                mObservable.unregisterObserver(this);
        }
    }

    protected final ArrayList<Observer> mObservers = new ArrayList<>();
    private final Handler mHandler;

    public Observable() {
        HandlerThread handlerThread = new HandlerThread("observableThread");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper(), this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        MSG obj = (MSG) msg.obj;
        notifyObservers(obj);
        return true;
    }

    public void sendMsg(MSG msg) {
        mHandler.obtainMessage(0, msg).sendToTarget();
    }

    private void notifyObservers(MSG msg) {
        for (Observer observer : mObservers) {
            observer.notify(msg);
        }
    }

    public void registerObserver(Observer observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized (mObservers) {
            if (mObservers.contains(observer)) {
                throw new IllegalStateException("Observer " + observer + " is already registered.");
            }
            mObservers.add(observer);
            observer.onRegister(this);
        }
    }

    private void unregisterObserver(Observer observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized (mObservers) {
            /*int index = mObservers.indexOf(observer);
            if (index == -1) {
                throw new IllegalStateException("Observer " + observer + " was not registered.");
            }*/
            mObservers.remove(observer);
        }
    }

    public void unregisterAll() {
        synchronized (mObservers) {
            mObservers.clear();
        }
    }
}
