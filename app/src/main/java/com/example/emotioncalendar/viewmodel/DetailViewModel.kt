package com.example.emotioncalendar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emotioncalendar.data.Memo
import com.example.emotioncalendar.data.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val memoRepository: MemoRepository
): ViewModel(){

    var currentMemo = MutableLiveData<Memo>()

    fun getMemo(date: String){
        Log.e("Jungbong", date)

        CoroutineScope(Dispatchers.IO).launch {
            currentMemo.postValue(memoRepository.getMemo(date))
        }
    }
}