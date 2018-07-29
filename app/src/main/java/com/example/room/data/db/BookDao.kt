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

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.Update
import java.util.Date
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
@TypeConverters(DateConverter::class)
interface BookDao {

    @Query("select * from Book where id = :id")
    fun loadBookById(id: Int): Book

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User on User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName")
    fun findBooksBorrowedByName(userName: String): LiveData<List<Book>>

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User on User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName " +
            "AND Loan.endTime > :after ")
    fun findBooksBorrowedByNameAfter(userName: String, after: Date): LiveData<List<Book>>

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User on User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName")
    fun findBooksBorrowedByNameSync(userName: String): List<Book>

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id LIKE Book.id " +
            "WHERE Loan.user_id LIKE :userId ")
    fun findBooksBorrowedByUser(userId: String): LiveData<List<Book>>

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id LIKE Book.id " +
            "WHERE Loan.user_id LIKE :userId " +
            "AND Loan.endTime > :after ")
    fun findBooksBorrowedByUserAfter(userId: String, after: Date): LiveData<List<Book>>

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id LIKE Book.id " +
            "WHERE Loan.user_id LIKE :userId ")
    fun findBooksBorrowedByUserSync(userId: String): List<Book>

    @Query("SELECT * FROM Book")
    fun findAllBooks(): LiveData<List<Book>>


    @Query("SELECT * FROM Book")
    fun findAllBooksSync(): List<Book>

    @Insert(onConflict = IGNORE)
    fun insertBook(book: Book)

    @Update(onConflict = REPLACE)
    fun updateBook(book: Book)

    @Query("DELETE FROM Book")
    fun deleteAll()
}
