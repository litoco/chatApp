package com.example.chatapp.respository.localstorage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AllChats")
class ChatsMetaData (
    @PrimaryKey val userId:String,
    @ColumnInfo val userName:String,
    @ColumnInfo val userPic:String,
    @ColumnInfo val messageMetaData: String
)