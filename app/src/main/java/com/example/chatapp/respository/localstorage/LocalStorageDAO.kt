package com.example.chatapp.respository.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chatapp.models.AllChatsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalStorageDAO {
    @Query("SELECT userId FROM UserId")
    suspend fun getUserId(): Flow<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserId(userId: UserId)

    @Query("DELETE FROM UserId")
    fun deleteUserDetails()

    @Query("SELECT * FROM AllChats")
    fun getAllChats(): Flow<List<AllChatsModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMetaData(chatsMetaData: ChatsMetaData)
}