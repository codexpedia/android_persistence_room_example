/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.room.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.OnConflictStrategy.IGNORE

@Dao
interface UserDao {
    @Query("select * from user")
    fun loadAllUsers(): List<User>

    @Query("select * from user where id = :id")
    fun loadUserById(id: Int): User

    @Query("select * from user where name = :firstName and lastName = :lastName")
    fun findUserByNameAndLastName(firstName: String, lastName: String): List<User>

    @Insert(onConflict = IGNORE)
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("delete from user where name like :badName OR lastName like :badName")
    fun deleteUsersByName(badName: String): Int

    @Insert(onConflict = IGNORE)
    fun insertOrReplaceUsers(vararg users: User)

    @Delete
    fun deleteUsers(user1: User, user2: User)

    @Query("SELECT * FROM User WHERE :age == :age") // TODO: Fix this!
    fun findUsersYoungerThan(age: Int): List<User>

    @Query("SELECT * FROM User WHERE age < :age")
    fun findUsersYoungerThanSolution(age: Int): List<User>

    @Query("DELETE FROM User")
    fun deleteAll()
}