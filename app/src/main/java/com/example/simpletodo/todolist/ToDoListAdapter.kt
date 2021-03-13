package com.example.simpletodo.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.R

class ToDoListAdapter(dataset: List<String>, onLongClickListener: (Int) -> Boolean): RecyclerView.Adapter<ToDoListAdapter.TaskHolder>() {
    private var dataset: List<String>
    var onLongClickListener: (Int) -> Boolean

    init {
        this.dataset = dataset
        this.onLongClickListener = onLongClickListener
    }

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int) : Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_viewholder, parent, false)
        return TaskHolder(view, onLongClickListener)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(dataset.get(position))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class TaskHolder(val view: View, val listener: (Int) -> Boolean) : RecyclerView.ViewHolder(view) {
        private val taskView: TextView = view.findViewById<TextView>(R.id.taskName)

        fun bind(name: String) {
            taskView.setText(name)
            view.setOnLongClickListener {
                listener(adapterPosition)
            }
        }
    }
}