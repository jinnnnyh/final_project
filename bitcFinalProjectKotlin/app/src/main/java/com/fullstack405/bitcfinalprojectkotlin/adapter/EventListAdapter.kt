package com.fullstack405.bitcfinalprojectkotlin.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fullstack405.bitcfinalprojectkotlin.data.EventListData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ItemEvnetBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.event.EventDetailActivity

class EventListAdapter(val eventList:MutableList<EventListData>, val userId:Long, val userPermission:String):RecyclerView.Adapter<EventListAdapter.Holder>() {
    class Holder(val binding: ItemEvnetBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemEvnetBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        var event = eventList.get(position)

        // 모집중 Y, 마감 N
        if(event.isRegistrationOpen == 'Y'){
            holder.binding.state.text = "[모집중]"
            holder.binding.state.setTextColor(Color.parseColor("#dd0b0b")) // 빨간색
        }
        else{
            holder.binding.state.text = "[마감]"
            holder.binding.state.setTextColor(Color.parseColor("#666666")) // 회색
        }

        holder.binding.title.text = event.eventTitle
        holder.binding.txtDate.text = "게시일  ${event.visibleDate}" // 게시일

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.binding.root.context, EventDetailActivity::class.java)
            intent.putExtra("eventId",event.eventId)
            intent.putExtra("isRegistrationOpen",event.isRegistrationOpen)
            // 유저id
            intent.putExtra("userId",userId)
            intent.putExtra("userPermission",userPermission)

            (holder.binding.root.context as Activity).startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return eventList.size
    }
}