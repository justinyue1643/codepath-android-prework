package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpletodo.databinding.ActivityMainBinding
import com.example.simpletodo.todolist.ToDoListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ToDoListAdapter

    private var tasks = mutableListOf<String>("hello", "world")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val submitField = binding.itemInput
        val submitButton = binding.submitButton
        submitButton.setOnClickListener { view -> onSubmit(submitField.text.toString()) }

        val taskList = binding.taskList
        val onItemLongClicked: (Int) -> Boolean = { position ->
            tasks.removeAt(position)
            adapter.notifyItemRemoved(position)
            true
        }

        adapter = ToDoListAdapter(tasks, onItemLongClicked)


        taskList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        taskList.adapter = adapter
    }

    fun onSubmit(newTask: String) {
        tasks.add(newTask)
        adapter.notifyItemInserted(tasks.size - 1)
    }


//    class RemoveListener : ToDoListAdapter.OnLongClickListener {
//        override fun onItemLongClicked(position: Int): Boolean {
//
//        }
//    }
}