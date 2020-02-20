/****************************************************************
 *   Copyright (c) 2020 Salvatore Ventura <salvoventura@gmail.com>
 *
 *     File: DataRecordViewModel.kt
 *
 *   Author: Salvatore Ventura <salvoventura@gmail.com>
 *     Date: 2/19/2020
 *  Purpose: ViewModel for our Record data.
 *
 * Revision: 1
 *  Comment: Notice how you get the DAO from the Database, and how
 *           we have some LiveData defined as member of this class.
 *
 *           Other methods are propagating from the Repository.
 *
 *           Notice how `suspend` methods here are called within
 *           a `viewModelScope.launch {}` statement.
 *
 *           Async is automatically taken care of for LiveData.
 *
 ****************************************************************/
 package com.salvoventura.apps.android.roomcrud.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.salvoventura.apps.android.roomcrud.data.AppRoomDatabase
import com.salvoventura.apps.android.roomcrud.data.DataRecord
import com.salvoventura.apps.android.roomcrud.data.DataRecordRepository
import kotlinx.coroutines.launch

private const val TAG = "DataRecordViewModel "

class DataRecordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DataRecordRepository
    val allItems: LiveData<List<DataRecord>>

    init {
        Log.d(TAG, "Inside ViewModel init")
        val dao = AppRoomDatabase.getDatabase(application).datarecordDao()
        repository = DataRecordRepository(dao)
        allItems = repository.allItems
    }

    fun insert(item: DataRecord) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: DataRecord) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: DataRecord) = viewModelScope.launch {
        repository.delete(item)
    }

    fun get(id: Long) = repository.get(id)
}