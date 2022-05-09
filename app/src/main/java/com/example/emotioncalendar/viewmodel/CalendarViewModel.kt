package com.example.emotioncalendar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emotioncalendar.data.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val memoRepository: MemoRepository
): ViewModel() {

    val currentYearMonth = MutableLiveData<YearMonth>()
    var calendar = MutableLiveData<Map<String,Int>>()

    fun onScrolled(date: YearMonth) {
        currentYearMonth.value = date
    }

    fun getCalendar(){
        val map = mutableMapOf<String,Int>()
        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.getCalendar().forEach {
                map[it.date] = it.emotionId
            }
            calendar.postValue(map)
        }
    }
}