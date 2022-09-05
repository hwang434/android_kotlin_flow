package com.example.android_kotlin_flow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    var currentValue = 10
    val countDownFlow = flow<Int> {
        val startingValue = currentValue

        emit(startingValue)
        while (currentValue > 0) {
            delay(1000)
            currentValue--
            // "emit"으로 데이터를 전송한다.
            emit(currentValue)
        }
    }
}