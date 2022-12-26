package com.optimahorizonapps.studentregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.optimahorizonapps.studentregister.db.Student
import com.optimahorizonapps.studentregister.db.StudentDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button

    private lateinit var viewModel: StudentViewModel
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var adapter: StudentRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.name_et)
        emailEditText = findViewById(R.id.email_et)
        saveButton = findViewById(R.id.save_btn)
        clearButton = findViewById(R.id.clear_btn)
        studentRecyclerView = findViewById(R.id.studentList_rv)

        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        saveButton.setOnClickListener {
            saveStudentData()
            clearInput()
        }

        clearButton.setOnClickListener {
            clearInput()
        }

        initRecyclerView()
    }

    private fun saveStudentData() {
//        val name = nameEditText.text.toString()
//        val email = emailEditText.text.toString()
//        val student = Student(0, name, email)
//        viewModel.insertStudent(student)

        //better way of doing the same thing(less code)
        viewModel.insertStudent(Student(0, nameEditText.text.toString(), emailEditText.text.toString()))
    }

    private fun clearInput() {
        nameEditText.setText("")
        emailEditText.setText("")
    }

    private fun initRecyclerView() {
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerViewAdapter()
        studentRecyclerView.adapter = adapter

        displayStudentsList()
    }

    private fun displayStudentsList() {
        viewModel.students.observe(this) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }

    }
}