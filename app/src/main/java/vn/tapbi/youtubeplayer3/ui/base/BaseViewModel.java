package vn.tapbi.youtubeplayer3.ui.base;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseViewModel extends ViewModel {

    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
