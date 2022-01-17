package com.taghavi.roomtest.repository

import androidx.lifecycle.LiveData
import com.taghavi.roomtest.data.UserDao
import com.taghavi.roomtest.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}