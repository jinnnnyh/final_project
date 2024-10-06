package com.fullstack405.bitcfinalprojectkotlin.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fullstack405.bitcfinalprojectkotlin.data.AttendData
import com.fullstack405.bitcfinalprojectkotlin.data.UserAttendData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ItemAttendBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendDetailActivity

class AttendAllAdapter(val attendList:MutableList<UserAttendData>):RecyclerView.Adapter<AttendAllAdapter.Holder>() {
    class Holder(val binding: ItemAttendBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemAttendBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var a = attendList.get(position)
        holder.binding.title.text = a.title
        holder.binding.date.text = a.date
        if(a.complete == 'Y'){
            holder.binding.complete.text = "수료"
        }else{
            holder.binding.complete.text = "미수료"
        }

        // 항목 누르면 상세보기 이동
        holder.itemView.setOnClickListener {
            var intent = Intent(holder.binding.root.context,AttendDetailActivity::class.java)
            intent.putExtra("userId",a.userId)
            intent.putExtra("eventId",a.eventId)
            intent.putExtra("complete",a.complete)
            (holder.binding.root.context as Activity).startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return attendList.size
    }

}