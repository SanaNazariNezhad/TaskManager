package org.maktab.taskmanager.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.maktab.taskmanager.data.model.Task
import org.maktab.taskmanager.data.model.User

@Database(entities = [Task::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getTaskDatabaseDAO(): TaskDatabaseDAO
}