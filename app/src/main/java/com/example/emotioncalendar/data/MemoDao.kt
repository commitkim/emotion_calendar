package com.example.emotioncalendar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM memos ORDER BY date")
    fun getAllMemos(): Flow<List<Memo>>

    @Query("SELECT * FROM memos")
    fun getDiariesMap(): List<Memo>

    @Query("SELECT * FROM memos WHERE date = :date")
    fun getMemoByDate(date: String): Memo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(diaries: List<Memo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memo: Memo)

}