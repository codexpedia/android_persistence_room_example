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

package com.example.room

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.example.room.data.DatabaseInitializer
import com.example.room.data.db.AppDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CustomResultViewModel(application: Application) : AndroidViewModel(application) {

    var loansResult: LiveData<String>? = null
        private set

    private var mDb: AppDatabase? = null

    private val yesterdayDate: Date
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -1)
            return calendar.time
        }

    fun createDb() {
        mDb = AppDatabase.getInMemoryDatabase(getApplication())

        // Populate it with initial data
        DatabaseInitializer.populateAsync(mDb)

        // Receive changes
        subscribeToDbChanges()
    }

    private fun subscribeToDbChanges() {
        val loans = mDb!!.loanModel().findLoansByNameAfter("Mike", yesterdayDate)

        // Instead of exposing the list of Loans, we can apply a transformation and expose Strings.
        loansResult = Transformations.map(loans) { loansWithUserAndBook ->
            val sb = StringBuilder()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)

            for (loan in loansWithUserAndBook) {
                sb.append(String.format("%s\n  (Returned: %s)\n",
                        loan.bookTitle,
                        simpleDateFormat.format(loan.endTime))
                )
            }
            sb.toString()
        }
    }
}
