/****************************************************************
 *   Copyright (c) 2020 Salvatore Ventura <salvoventura@gmail.com>
 *
 *     File: DataRecordListActivity.kt
 *
 *   Author: Salvatore Ventura <salvoventura@gmail.com>
 *     Date: 2/19/2020
 *  Purpose: The Activity that will drive all the operations on
 *           DataRecords.
 *
 * Revision: 1
 *  Comment: Simple activity, loads the XML layout named `activity_datarecord_list`
 *           which is defined `activity_datarecord_list.xml`
 *
 *           It exposes a FAB (Floating Action Button), which we will
 *           use for the CREATE operation.
 *
 *           Inside the layout, another layout is referenced: `data_record_list_recyclerview`
 *           which corresponds to file `data_record_list_recyclerview.xml`
 *
 *           This file is the one that will display the list of items
 *           by using a RecyclerView, whose ID is `datarecord_list`
 *
 ****************************************************************/
 package com.salvoventura.apps.android.roomcrud

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salvoventura.apps.android.roomcrud.adapters.DataRecordAdapter
import com.salvoventura.apps.android.roomcrud.viewmodels.DataRecordViewModel
import kotlinx.android.synthetic.main.activity_datarecord_list.*
import kotlinx.android.synthetic.main.data_record_list_recyclerview.*

private const val TAG = "DataRecordListActivity"  // TAG for Logs, if the need them

class DataRecordListActivity : AppCompatActivity() {

    private lateinit var datarecordViewModel: DataRecordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the layout
        setContentView(R.layout.activity_datarecord_list)
        setSupportActionBar(toolbar)

        // Set an action for the FAB: in particular, this will start a new activity
        fab_add.setOnClickListener { _ ->
            val intent = Intent(this, DataRecordDetail::class.java)
            startActivity(intent)
        }

        /* Reference objects inside the loaded layout: notice that in Kotlin we can use short
        names (the IDE will prompt for imports).

        To be clear, the following line could have been:
           val recyclerView = R.id.datarecord_list

        But since the IDE has imported:
           import kotlinx.android.synthetic.main.activity_datarecord_list.*
           import kotlinx.android.synthetic.main.data_record_list_recyclerview.*

        we can simply write:
           val recyclerView = datarecord_list


        For every RecyclerView we always need to set:
        - An Adapter --> we define
        - A LayoutManager --> predefined, either Linear or Grid
         */
        val recyclerView = datarecord_list
        val adapter = DataRecordAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        /* We need the data to populate the list with. For this we will retrieve the ViewModel
           that we have defined, which in our case is a `DataRecordViewModel::class.java`, from
           the ViewModelProvider service.
        */
        datarecordViewModel = ViewModelProvider(this).get(DataRecordViewModel::class.java)

        /* Simply associate an observer with each of the items contained in the viewmodel.

           Notice that this can be done because `allItems` in the `DataRecordViewModel` is a `LiveData`
           object.

           The method `setItems` of the `DataRecordAdapter` class, takes care of populating each
           line of the RecyclerView, according to the layout specified in `datarecord_viewholder`.

           This is also where we set any actions (click, longclick...) per item in the list.

        */
        datarecordViewModel.allItems.observe(this, Observer { items ->
            items?.let { adapter.setItems(it) }
        })
    }
}
