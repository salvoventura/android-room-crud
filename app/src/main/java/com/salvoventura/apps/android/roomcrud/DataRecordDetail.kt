/****************************************************************
 *   Copyright (c) 2020 Salvatore Ventura <salvoventura@gmail.com>
 *
 *     File: DataRecordDetail.kt
 *
 *   Author: Salvatore Ventura <salvoventura@gmail.com>
 *     Date: 2/19/2020
 *  Purpose: Activity for showing the Detail page of Record data
 *
 * Revision: 1
 *  Comment: We reuse the same XML Layout to either Create a new
 *           Record or to Edit/Delete and existing one.
 *
 *           The way to distinguish the cases is through examination
 *           of the received Intent.
 *
 *           If the intent has Extra Data, then it should be a case
 *           of Edit/Delete (with implicit Get)
 *
 *           Otherwise, it will be a Create new.
 *
 *           The UI is designed with three static buttons, however
 *           we hide/show the appropriate buttons based on the same
 *           analysis, so as to make it less confusing and error-prone.
 *
 *           All database operations are performed using an instance
 *           of the DataRecordViewModel.
 *
 ****************************************************************/
 package com.salvoventura.apps.android.roomcrud

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.salvoventura.apps.android.roomcrud.data.DataRecord
import com.salvoventura.apps.android.roomcrud.viewmodels.DataRecordViewModel
import kotlinx.android.synthetic.main.activity_datarecord_detail.*

class DataRecordDetail : AppCompatActivity() {

    private lateinit var datarecordViewModel: DataRecordViewModel
    private var recordId: Long = 0L
    private var isEdit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the layout from `activity_datarecord_detail.xml`
        setContentView(R.layout.activity_datarecord_detail)

        /* Retrieve the ViewModel that we have defined (`DataRecordViewModel::class.java`)
           from the ViewModelProvider service.
        */
        datarecordViewModel = ViewModelProvider(this).get(DataRecordViewModel::class.java)

        /* Check Intent for Extra Data, based on the predefined key (which is the same that we use
           to insert such data. Also notice that since we expect it to be of type Long, we use
           `getLongExtra`. There's getters for different data types.
        */
        if (intent.hasExtra(Constants.DATA_RECORD_ID)) {
            recordId = intent.getLongExtra(Constants.DATA_RECORD_ID, 0L)

            /*  In this case, since the DAO `get(id)` method returns a `LiveData` object,
                we use the `observer` pattern to populate the view.
             */
            datarecordViewModel.get(recordId).observe(this, Observer {

                // Ger references to the UI items in the layout
                val viewId = findViewById<TextView>(R.id.datarecord_id)
                val viewRecord = findViewById<EditText>(R.id.datarecord_record)

                // Protect from null, which occurs when we delete the item
                if (it != null) {
                    // populate with data
                    viewId.text = it.id.toString()
                    viewRecord.setText(it.record)
                }
            })
            isEdit = true
        }

        /* Prepare OnClickListeners for each button:
            Save, Update and Delete.

           They pretty much do the same operations and checks, but use the specific
           insert, update, delete method from the ViewModel.

         */
        val btnSave = btnSave
        btnSave.setOnClickListener { view ->
            val id = 0L
            val record = datarecord_record.text.toString()

            if (record.isBlank() or record.isEmpty()) {
                Snackbar.make(view, "Empty data is not allowed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                val item = DataRecord(id = id, record = record)
                datarecordViewModel.insert(item)
                finish()
            }
        }

        val btnUpdate = btnUpdate
        btnUpdate.setOnClickListener { view ->
            val id = datarecord_id.text.toString().toLong()
            val record = datarecord_record.text.toString()

            if (record.isBlank() or record.isEmpty()) {
                Snackbar.make(view, "Empty data is not allowed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                val item = DataRecord(id = id, record = record)
                datarecordViewModel.update(item)
                finish()
            }
        }

        val btnDelete = btnDelete
        btnDelete.setOnClickListener {
            val id = datarecord_id.text.toString().toLong()
            val record = datarecord_record.text.toString()

            val item = DataRecord(id = id, record = record)
            datarecordViewModel.delete(item)
            finish()
        }

        /* Hide buttons depending on our case: this is a very simplistic UI management
           example, and you need to correctly set the constraints on the Layout to make
           this at least marginally pleasant. There's better ways, of course. :-)
         */
        if (isEdit) {
            /* btnSave calls the dao.save method, which actually creates a new record
               By hiding it, we correctly allow only Update and Delete
             */
            btnSave.visibility = View.GONE
        } else {
            /* No reason to Update or Delete a new Record yet to be saved */
            btnUpdate.visibility = View.GONE
            btnDelete.visibility = View.GONE
        }
    }
}
