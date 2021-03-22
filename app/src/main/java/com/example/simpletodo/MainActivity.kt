package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}