package nl.bijdorpstudio.feature.search

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

//TODO: tests
abstract class RxViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    @CallSuper
    public override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
