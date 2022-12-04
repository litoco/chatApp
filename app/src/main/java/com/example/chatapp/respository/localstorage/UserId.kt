package com.example.chatapp.respository.localstorage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserId")
class UserId(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "userId") val userId:String
)