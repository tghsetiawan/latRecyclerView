package com.example.latrecyclerview

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.latrecyclerview.databinding.ListItemBinding

class TodoAdapter(private val dataSet: MutableList<String>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.list_item, viewGroup, false)

        val binding = ListItemBinding.inflate(LayoutInflater.from(viewGroup.context))

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.todoText.text = dataSet[position]

        //menghapus todos
        viewHolder.delBtn.setOnClickListener {
            dataSet.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, dataSet.size)
        }

        //mengedit todos
        viewHolder.editBtn.setOnClickListener {
            val context = viewHolder.itemView.context

            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.edit_todo, null)

            //mengambil data sebelumnya
            val prevText = dataSet[position]
            val editText = view.findViewById<TextView>(R.id.editTextTextPersonName)
            editText.text = prevText

            //dialog
            var alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Edit Todo")
                    .setView(view)
                    .setPositiveButton("Edit", DialogInterface.OnClickListener { dialog, id ->

                        dataSet[position] = editText.text.toString()
                        notifyDataSetChanged()
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                        //do nothing
                    })
            alertDialog.create().show()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    class ViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val todoText = binding.todoItem
        val delBtn = binding.btnDelete
        val editBtn = binding.btnEdit
    }

}