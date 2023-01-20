package com.sample.chat.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sample.chat.data.entities.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDaoTests {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java,
        ).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun noUsers() = runBlocking {
        val userById = userDao.getUserById("any").first()
        assertThat(userById).isNull()
    }

    @Test
    @Throws(Exception::class)
    fun getUserById() = runBlocking {
        val user = UserEntity("any", "any", "any")
        userDao.insertAll(user)

        val userById = userDao.getUserById("any").first()
        assertThat(userById).isEqualTo(user)
    }
}
