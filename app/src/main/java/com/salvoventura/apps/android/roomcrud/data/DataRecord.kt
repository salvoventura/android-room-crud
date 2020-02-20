/****************************************************************
 *   Copyright (c) 2020 Salvatore Ventura <salvoventura@gmail.com>
 *
 *     File: DataRecord.kt
 *
 *   Author: Salvatore Ventura <salvoventura@gmail.com>
 *     Date: 2/19/2020
 *  Purpose: Data class for my "Record" data.
 *
 * Revision: 1
 *  Comment: Very simple data type: we want to store just a String
 *           in column `record`, and have it indexed by a Long `id`
 *
 ****************************************************************/
 package com.salvoventura.apps.android.roomcrud.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "datarecords")
data class DataRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val record: String
)

