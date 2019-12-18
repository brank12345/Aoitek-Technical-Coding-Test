package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val api = TestAPI()
    private var recyclerView: RecyclerView? = null
    private var viewManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.user_list)

        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        requestData()
    }

    private fun requestData() {
        api.callAPI()
    }

    private fun initViewModel() {
        api.updateUserList().observe(this, Observer { data ->
            data ?: return@Observer

            recyclerView?.apply {
                setHasFixedSize(true)
                this.adapter = UserListAdapter(data)
                this.layoutManager = viewManager
            }
        })
    }
}
