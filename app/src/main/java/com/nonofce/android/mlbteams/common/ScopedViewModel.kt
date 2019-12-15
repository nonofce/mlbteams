package com.nonofce.android.mlbteams.common

import androidx.lifecycle.ViewModel

abstract class ScopedViewModel:ViewModel(), Scope by Scope.Impl() {

    init {
        initScope()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}