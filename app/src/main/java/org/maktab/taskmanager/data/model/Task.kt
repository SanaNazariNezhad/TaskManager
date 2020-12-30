package org.maktab.taskmanager.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task")
class Task() {
    constructor(title: String, description: String, date: Date, state: String) : this() {
        mId = UUID.randomUUID()
        mTitle = title
        mDescription = description
        mDate = date
        mState = state
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private var primaryId: Long = 0

    @ColumnInfo(name = "uuid")
    private var mId: UUID? = null

    @ColumnInfo(name = "title")
    private var mTitle: String? = null

    @ColumnInfo(name = "description")
    private var mDescription: String? = null

    @ColumnInfo(name = "date")
    private var mDate: Date? = null

    @ColumnInfo(name = "state")
    private var mState: String? = null

    @ColumnInfo(name = "user_id_fk")
    private var userIdFk: Long = 0

    fun getUserIdFk(): Long {
        return userIdFk
    }

    fun setUserIdFk(userIdFk: Long) {
        this.userIdFk = userIdFk
    }

    fun getPrimaryId(): Long {
        return primaryId
    }

    fun setPrimaryId(primaryId: Long) {
        this.primaryId = primaryId
    }

    fun getId(): UUID? {
        return mId
    }

    fun setId(id: UUID?) {
        mId = id
    }

    fun getTitle(): String? {
        return mTitle
    }

    fun setTitle(title: String?) {
        mTitle = title
    }

    fun getDescription(): String? {
        return mDescription
    }

    fun setDescription(description: String?) {
        mDescription = description
    }

    fun getDate(): Date? {
        return mDate
    }

    fun setDate(date: Date?) {
        mDate = date
    }

    fun getState(): String? {
        return mState
    }

    fun setState(state: String?) {
        mState = state
    }

    fun getPhotoFileName(): String? {
        return "IMG_" + getId().toString() + ".jpg"
    }
}