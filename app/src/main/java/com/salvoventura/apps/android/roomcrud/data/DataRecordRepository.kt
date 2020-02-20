/****************************************************************
 *   Copyright (c) 2020 Salvatore Ventura <salvoventura@gmail.com>
 *
 *     File: DataRecordRepository.kt
 *
 *   Author: Salvatore Ventura <salvoventura@gmail.com>
 *     Date: 2/19/2020
 *  Purpose: Repository for the Record data class
 *           
 * Revision: 1
 *  Comment: Due to its simplicity, most of the code just exposes
 *           the functions from the DAO.
 *           However, notice how `allItems` is a local property
 *           populated via `DataRecordDao.getall()`
 *
 ****************************************************************/
 package com.salvoventura.apps.android.roomcrud.data

import androidx.lifecycle.LiveData

class DataRecordRepository(private val datarecordDao: DataRecordDao) {

    val allItems: LiveData<List<DataRecord>> = datarecordDao.getall()

    fun get(id: Long):LiveData<DataRecord> {
        return datarecordDao.get(id)
    }

    suspend fun update(item: DataRecord) {
        datarecordDao.update(item)
    }

    suspend fun insert(item: DataRecord) {
        datarecordDao.insert(item)
    }

    suspend fun delete(item: DataRecord) {
        datarecordDao.delete(item)
    }
}