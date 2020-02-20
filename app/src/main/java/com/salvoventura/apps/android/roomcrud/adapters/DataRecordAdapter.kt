/****************************************************************
 *   Copyright (c) 2020 Salvatore Ventura <salvoventura@gmail.com>
 *
 *     File: DataRecordAdapter.kt
 *
 *   Author: Salvatore Ventura <salvoventura@gmail.com>
 *     Date: 2/19/2020
 *  Purpose: Adapter to populate the RecyclerView with Record Data
 *
 * Revision: 1
 *  Comment: An adapter is needed to inflate the ViewHolder using
 *           the data for each Record item. The layout for the ViewHolder
 *           is described in file `datarecord_viewholder.xml` referenced
 *           here as `R.layout.datarecord_viewholder`.
 *
 *           Note how an OnClickListener is defined and attached to
 *           every new ViewHolder to handle item-specific actions.
 *
 ****************************************************************/
 package com.salvoventura.apps.android.roomcrud.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.salvoventura.apps.android.roomcrud.Constants
import com.salvoventura.apps.android.roomcrud.DataRecordDetail
import com.salvoventura.apps.android.roomcrud.R
import com.salvoventura.apps.android.roomcrud.data.DataRecord

private const val TAG = "DataRecordAdapter"

class DataRecordAdapter internal constructor(context: Context) :

    RecyclerView.Adapter<DataRecordAdapter.DataRecordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var itemsList = emptyList<DataRecord>().toMutableList()

    private val onClickListener: View.OnClickListener

    init {
        /* Remember that his receives a single item of type DataRecord during iteration
           from:
                datarecordViewModel.allItems.observe(this, Observer { items ->
                    items?.let { adapter.setItems(it) }
                })
           in the `DataRecordListActivity`
        * */
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as DataRecord

            Log.d(TAG, "Setting onClickListener for item ${item.id}")

            /* We also want to start a new Intent with Extra Data customized
               to the `id` of the associated item.
             */

            val intent = Intent(v.context, DataRecordDetail::class.java).apply {
                putExtra(Constants.DATA_RECORD_ID, item.id)
            }
            v.context.startActivity(intent)
        }
    }

    /* This is an `inner class` that associates associates the items in the ViewHolder
       layout with variables that will be used inside OnBindViewHolder.
    */
    inner class DataRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemId: TextView = itemView.findViewById(R.id.datarecord_viewholder_id)
        val itemRecord: TextView = itemView.findViewById(R.id.datarecord_viewholder_record)
    }

    /* Basically, inflates the ViewHolder layout and returns a ViewHolder object
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataRecordViewHolder {
        val itemView = inflater.inflate(R.layout.datarecord_viewholder, parent, false)
        return DataRecordViewHolder(itemView)
    }

    /* This is where the ViewHolder gets populated with data from the Item.
       Position inside the RecyclerView is also available.
     */
    override fun onBindViewHolder(holder: DataRecordViewHolder, position: Int) {
        val current = itemsList[position]

        // Needed: will be referenced in the View.OnClickListener above
        holder.itemView.tag = current

        with(holder) {
            // Set UI values
            itemId.text = current.id.toString()
            itemRecord.text = current.record

            // Set handlers
            itemView.setOnClickListener(onClickListener)
        }
    }

    internal fun setItems(items: List<DataRecord>) {
        this.itemsList = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemsList.size
}