package com.army.jetpack.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @author daijun
 * @date 2020/2/24
 * @description
 */
class PagingAdapter :
    PagedListAdapter<WanAndroidBean.DataBean.DatasBean, PagingAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<WanAndroidBean.DataBean.DatasBean>() {
        override fun areItemsTheSame(
            oldItem: WanAndroidBean.DataBean.DatasBean,
            newItem: WanAndroidBean.DataBean.DatasBean
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: WanAndroidBean.DataBean.DatasBean,
            newItem: WanAndroidBean.DataBean.DatasBean
        ) = oldItem == newItem
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = getItem(position)?.title
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(android.R.id.text1)
    }
}