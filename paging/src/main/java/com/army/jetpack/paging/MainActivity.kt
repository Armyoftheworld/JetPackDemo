package com.army.jetpack.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.layoutManager = LinearLayoutManager(this)
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val adapter = PagingAdapter()
        list.adapter = adapter
        val viewModel = ViewModelProvider(this).get(WanAndroidViewModel::class.java)
        swipe_refresh.setOnRefreshListener {
            viewModel.dataSource.invalidate()
        }
        viewModel.articleRes.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.boundaryPageData.observe(this, Observer {
            if (!it) {
                swipe_refresh.finishLoadMore()
                swipe_refresh.finishRefresh()
            }
        })
    }
}
