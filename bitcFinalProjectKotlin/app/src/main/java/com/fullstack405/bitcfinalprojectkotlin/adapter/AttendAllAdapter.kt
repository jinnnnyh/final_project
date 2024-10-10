package com.fullstack405.bitcfinalprojectkotlin.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fullstack405.bitcfinalprojectkotlin.data.EventAppData
import com.fullstack405.bitcfinalprojectkotlin.data.UserAttendData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ItemAttendBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendDetailActivity

class AttendAllAdapter(val attendList:MutableList<EventAppData>,val userId:Long):RecyclerView.Adapter<AttendAllAdapter.Holder>() {
    class Holder(val binding: ItemAttendBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemAttendBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var a = attendList.get(position)
        holder.binding.title.text = a.eventTitle
        holder.binding.date.text = a.appDate

        if(a.eventComp == 'Y'){
            holder.binding.complete.text = "수료"
        }else{
            holder.binding.complete.text = "미수료"
        }

        // 항목 누르면 상세보기 이동
        holder.itemView.setOnClickListener {
            var intent = Intent(holder.binding.root.context,AttendDetailActivity::class.java)
            intent.putExtra("userId",userId)
            intent.putExtra("eventId",a.eventId)
            intent.putExtra("complete",a.eventComp)
            (holder.binding.root.context as Activity).startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return attendList.size
    }

}