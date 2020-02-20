/****************************************************************
 *   Copyright (c) 2020 Salvatore Ventura <salvoventura@gmail.com>
 *
 *     File: DataRecordDao.kt
 *
 *   Author: Salvatore Ventura <salvoventura@gmail.com>
 *     Date: 2/19/2020
 *  Purpose: DAO for my Record Data class
 *
 * Revision: 1
 *  Comment: Provide data access methods for the various operations
 *           that the app will need. The app is simple so we will
 *           only need:
 *           - getall() : retrieve the full list to populate the
 *              RecyclerView
 *           - get() : retrieve a specific item by its id. For this
 *              one we need to specify the full query. Notice how the
 *              `id` parameter in the method is passed inside the query
 *              as `:id`
 *           - insert() : to insert a new item in the database
 *           - update() : to update an existing item in the database
 *           - delete() : to delete an existing item in the database
 *
 ****************************************************************/
 package com.salvoventura.apps.android.roomcrud.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataRecordDao {

    @Query("SELECT * from datarecords")
    fun getall(): LiveData<List<DataRecord>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: DataRecord)

    @Query("SELECT * FROM datarecords WHERE datarecords.id == :id")
    fun get(id: Long): LiveData<DataRecord>

    @Update
    suspend fun update(vararg items: DataRecord)

    @Delete
    suspend fun delete(vararg items: DataRecord)
}