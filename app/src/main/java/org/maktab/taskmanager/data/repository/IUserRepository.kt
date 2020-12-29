package org.maktab.taskmanager.data.repository

import org.maktab.taskmanager.data.model.Task
import org.maktab.taskmanager.data.model.User

interface IUserRepository {
    fun getUsers(): List<User>
    fun getUser(username: String, password: String): User
    fun insertUser(user: User)
    fun deleteUser(user: User)
    fun deleteUserTasks(userId: Long)
    fun getUserTasks(userId: Long): List<Task>
    fun numberOfTask(userId: Long): Int
}