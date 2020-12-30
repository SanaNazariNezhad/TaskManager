package org.maktab.taskmanager.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user")
class User(
    @ColumnInfo(name = "username")
    private var mUsername: String?,
    @ColumnInfo(name = "password")
    private var mPassword: String?
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private var primaryId: Long = 0

    @ColumnInfo(name = "date")
    private var mDate: Date? = null

    init {
        this.mDate = Date()
    }

    fun getDate(): Date? {
        return mDate
    }

    fun setDate(date: Date?) {
        mDate = date
    }

    fun getPrimaryId(): Long {
        return primaryId
    }

    fun setPrimaryId(primaryId: Long) {
        this.primaryId = primaryId
    }

    fun getUsername(): String? {
        return mUsername
    }

    fun setUsername(username: String?) {
        mUsername = username
    }

    fun getPassword(): String? {
        return mPassword
    }

    fun setPassword(password: String?) {
        mPassword = password
    }


}