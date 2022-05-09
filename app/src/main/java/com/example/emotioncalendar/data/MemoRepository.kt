package com.example.emotioncalendar.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoRepository @Inject constructor(
    private val memoDao: MemoDao
    )
{
    fun getAllMemos() = memoDao.getAllMemos()

    fun getCalendar() = memoDao.getDiariesMap()

    fun getMemo(date: String) = memoDao.getMemoByDate(date)

    suspend fun insertMemo(memo: Memo) = memoDao.insert(memo)
}