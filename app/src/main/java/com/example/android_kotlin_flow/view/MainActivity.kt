package com.example.android_kotlin_flow.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.android_kotlin_flow.R
import com.example.android_kotlin_flow.databinding.ActivityMainBinding
import com.example.android_kotlin_flow.viewmodel.MainViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.stateIn

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG: String = "로그"
    }

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setEvent()
    }

    private fun setEvent() {
        binding.textViewForTimer.setOnClickListener {
            Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show()
        }

        // 단순한 예제 코드로 실제로 아래와 같이 작성하면 안됩니다.
        // 코루틴이 뷰모델 스코프로 동작하므로, 화면 회전을 하여도 코루틴이 취소되거나 하지는 않지만 UI를 다시 그리기 때문에 UI가 초기화 됩니다.
        binding.buttonStartTimer.setOnClickListener {
            mainViewModel.viewModelScope.launch {
                mainViewModel.countDownFlow.collectLatest {
                    withContext(Dispatchers.Main) {
                        delay(1500)
                        binding.textViewForTimer.text = it.toString()
                        Log.d(TAG,"MainActivity - time : $it")
                    }
                }
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(TAG,"MainActivity - onRestoreInstanceState() called")
        super.onRestoreInstanceState(savedInstanceState)
    }
}