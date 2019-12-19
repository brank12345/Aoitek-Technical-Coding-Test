package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity()
    , UserListAdapter.ReachNearestBottomListener {
    private val api = TestAPI()
    private var recyclerView: RecyclerView? = null
    private var viewManager: RecyclerView.LayoutManager? = null
    private var isLoadingComplete: Boolean = false

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
        isLoadingComplete = false
        api.callAPI()
    }

    private fun initViewModel() {
        api.updateUserList().observe(this, Observer { data ->
            data ?: return@Observer
            data.add(UserInfo("","")) // add empty data in the end for progress bar

            recyclerView?.apply {
                setHasFixedSize(true)

                if((this.adapter as UserListAdapter?)?.userList?.isNotEmpty() == true) {
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

            isLoadingComplete = true
        })
    }

    override fun reachNearestBottom() {
        if (isLoadingComplete) {
            requestData()
        }
    }
}
