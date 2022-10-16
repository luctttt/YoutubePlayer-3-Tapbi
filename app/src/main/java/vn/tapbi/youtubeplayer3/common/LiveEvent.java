package vn.tapbi.youtubeplayer3.common;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.Iterator;

public class LiveEvent<T> extends MediatorLiveData<T> {
    private ArraySet<ObserverWrapper<T>> observers = new ArraySet<>();
    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
        ObserverWrapper  wrapper = new ObserverWrapper(observer);
        observers.add(wrapper);
        super.observe(owner, wrapper);
    }
    @MainThread
    @Override
    public void observeForever(@NonNull Observer observer) {
        ObserverWrapper  wrapper = new ObserverWrapper(observer);
        observers.add(wrapper);
        super.observeForever(wrapper);
    }
    @MainThread
    @Override
    public void removeObserver(@NonNull Observer observer) {
        if(observers.remove(observer)){
            super.removeObserver(observer);
            return;
        }
        Iterator iterator = observers.iterator();
        while (iterator.hasNext()){
            ObserverWrapper wrapper = (ObserverWrapper) iterator.next();
            if(wrapper.observer==observer){
                iterator.remove();
                super.removeObserver(observer);
                break;
            }


        }

    }

    @Override
    public void postValue(T value) {
        for (ObserverWrapper  wrapper: observers) {
            wrapper.newValue();
        }
        super.postValue(value);
    }
    @MainThread
    @Override
    public void setValue(T value) {
        for (ObserverWrapper  wrapper: observers) {
            wrapper.newValue();
        }
        super.setValue(value);
    }

    private static class ObserverWrapper<T> implements Observer<T> {
        private final Observer<T> observer;
        private boolean pending  =false;

        public ObserverWrapper(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onChanged(Object o) {
            if(pending){
                pending=false;
                observer.onChanged((T)o);
            }

        }
        public void newValue(){
            pending=true;
        }
    }
}