package com.example.emotioncalendar.di

import android.content.Context
import androidx.room.Room
import com.example.emotioncalendar.data.AppDatabase
import com.example.emotioncalendar.data.MemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "emotion_calendar.db"
        ).build()
    }

    @Provides
    fun providePlantDao(appDatabase: AppDatabase): MemoDao {
        return appDatabase.memoDao()
    }

}