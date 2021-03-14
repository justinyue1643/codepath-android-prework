package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpletodo.databinding.ActivityMainBinding
import com.example.simpletodo.todolist.ToDoListAdapter
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FileUtils.readLines
import org.apache.commons.io.FileUtils.writeLines
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ToDoListAdapter
    private lateinit var tasks: MutableList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val submitField = binding.itemInput
        val submitButton = binding.submitButton
        submitButton.setOnClickListener { view -> onSubmit(submitField.text.toString()) }

//        tasks = mutableListOf()
        loadItems()

        val taskList = binding.taskList
        val onItemLongClicked: (Int) -> Boolean = { position ->
            tasks.removeAt(position)
            adapter.notifyItemRemoved(position)
            saveItems()
            println(getDataFile().toString())
            true
        }

        adapter = ToDoListAdapter(tasks, onItemLongClicked)

        taskList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        taskList.adapter = adapter
    }

    fun onSubmit(newTask: String) {
        tasks.add(newTask)
        adapter.notifyItemInserted(tasks.size - 1)
        saveItems()
    }

    private fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    private fun loadItems() {
        try {
            val charList = mutableListOf(FileUtils.readLines(getDataFile(), Charset.defaultCharset()))
            tasks = charList.toMutableList()[0]
        }
        catch (e: FileNotFoundException) {
            Log.e("MainActivity", "Error reading items", e)
            tasks = mutableListOf()
        }
    }

    private fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), tasks)
        }
        catch (e: FileNotFoundException) {
            Log.e("MainActivity", "Error writing items", e)
        }
    }

}