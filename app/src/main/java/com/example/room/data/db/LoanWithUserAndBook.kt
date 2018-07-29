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

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.TypeConverters

import java.util.Date

data class LoanWithUserAndBook(
        var id: String,
        @ColumnInfo(name = "title") var bookTitle: String? = null,
        @ColumnInfo(name = "name") var userName: String? = null,
        @TypeConverters(DateConverter::class) var startTime: Date? = null,
        @TypeConverters(DateConverter::class) var endTime: Date? = null
)
