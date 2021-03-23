package com.example.simpletodo.todolist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpletodo.R
import com.example.simpletodo.databinding.FragmentToDoListBinding
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileNotFoundException
import java.nio.charset.Charset

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ToDoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ToDoListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ToDoListAdapter
    private lateinit var tasks: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        val view = binding.root


        val submitField = binding.itemInput
        val submitButton = binding.submitButton
        submitButton.setOnClickListener { view -> onSubmit(submitField.text.toString()) }

        loadItems()

        val taskList = binding.taskList
        val onItemLongClicked: (Int) -> Boolean = { position ->
            tasks.removeAt(position)
            adapter.notifyItemRemoved(position)
            saveItems()
            println(getDataFile().toString())
            true
        }

        val itemOnClicked = { position: Int ->
            val action = ToDoListFragmentDirections.actionToDoListFragmentToEditTaskFragment(taskName = tasks[position], position = position)
            Navigation.findNavController(view).navigate(action)
            true
        }

        val position: Int? = arguments?.getInt("position")
        val realPosition: Int = if (position == -1) -1 else position!!
        val newTaskName: String? = arguments?.getString("taskName")

        if (realPosition != -1 && newTaskName != null) {
            tasks.set(realPosition, newTaskName)
            saveItems()
        }

        adapter = ToDoListAdapter(tasks, onItemLongClicked, itemOnClicked)

        taskList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        taskList.adapter = adapter

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ToDoListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ToDoListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


    fun onSubmit(newTask: String) {
        tasks.add(newTask)
        adapter.notifyItemInserted(tasks.size - 1)
        saveItems()
    }

    private fun getDataFile() : File {
        return File(context?.filesDir, "data.txt")
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