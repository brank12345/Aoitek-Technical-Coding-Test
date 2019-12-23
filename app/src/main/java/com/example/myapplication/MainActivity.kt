package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity()
    , UserListAdapter.ReachNearestBottomListener {
    private var recyclerView: RecyclerView? = null
    private var viewManager: RecyclerView.LayoutManager? = null
    private var isLoadingComplete: Boolean = false
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.user_list)

        initViewModel()
        requestData()
    }

    private fun requestData() {
        isLoadingComplete = false
        viewModel.requestData()
    }

    private fun initViewModel() {
        val factory = ViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java).apply {
            getUserInfoList().observe(this@MainActivity, Observer { data ->
                data ?: return@Observer
                data.add(UserInfo("","")) // add empty data in the end for progress bar
                handleDataUpdate(data)
                isLoadingComplete = true
            })

            getErrorMessage().observe(this@MainActivity, Observer { errorMessage ->
                Log.d("QAQ", errorMessage)
                isLoadingComplete = true
            })
        }
    }

    private fun handleDataUpdate(data: MutableList<UserInfo>) {
        recyclerView?.apply {
            setHasFixedSize(true)

            if((this.adapter as UserListAdapter?)?.userList?.isNotEmpty() == true) {
                // remove the last empty data, append data at the end
                (this.adapter as UserListAdapter?)?.apply {
                    this.userList.removeAt(this.userList.size-1)
                    this.userList.addAll(data)
                    this.notifyDataSetChanged()
                }
            } else {
                this.adapter = UserListAdapter(data, this@MainActivity)
                this.layoutManager = viewManager
            }
        }
    }

    override fun reachNearestBottom() {
        if (isLoadingComplete) {
            requestData()
        }
    }
}
