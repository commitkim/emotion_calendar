package com.example.emotioncalendar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.emotioncalendar.data.Memo
import com.example.emotioncalendar.data.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemoListViewModel @Inject constructor(
    memoRepository: MemoRepository
) : ViewModel() {

    val memos: LiveData<List<Memo>> = memoRepository.getAllMemos().asLiveData()

}