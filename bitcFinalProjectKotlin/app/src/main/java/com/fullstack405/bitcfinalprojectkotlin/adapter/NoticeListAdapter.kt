package com.fullstack405.bitcfinalprojectkotlin.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fullstack405.bitcfinalprojectkotlin.data.NoticeData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ItemNoticeBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.notice_XXX.NoticeDetailActivity

class NoticeListAdapter(val noticeList:MutableList<NoticeData>):RecyclerView.Adapter<NoticeListAdapter.Holder>() {
    class Holder(val binding: ItemNoticeBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemNoticeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val n = noticeList.get(position)
        holder.binding.title.text = n.notiTitle
        holder.binding.date.text = n.notiDate

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.binding.root.context, NoticeDetailActivity::class.java)
            intent.putExtra("noticeId",n.notiId)
            intent.putExtra("notiTitle",n.notiTitle)
            intent.putExtra("notiContent",n.notiContent)
            intent.putExtra("notiDate",n.notiDate)

            (holder.binding.root.context as Activity).startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

}