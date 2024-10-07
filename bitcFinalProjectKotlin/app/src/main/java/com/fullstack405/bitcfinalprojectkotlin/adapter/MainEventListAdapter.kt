package com.fullstack405.bitcfinalprojectkotlin.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fullstack405.bitcfinalprojectkotlin.data.EventData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ItemEvnetMainBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.event.EventDetailActivity
import java.lang.Integer.parseInt

class MainEventListAdapter(val eventList:MutableList<EventData>, val userId:Long, private val userPermission:String):RecyclerView.Adapter<MainEventListAdapter.Holder>() {
    class Holder(val binding: ItemEvnetMainBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemEvnetMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        var event = eventList.get(position)

        // attend테이블 해당행사 인원수와  event 테이블 인원수 비교해서 판단
        if(event.eventState == 'Y'){
            holder.binding.state.text = "[모집중]"
        }
        else{
            holder.binding.state.text = "[마감]"
        }

        holder.binding.title.text = event.eventTitle

        holder.binding.date.text = event.visibleDate // 게시일

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.binding.root.context, EventDetailActivity::class.java)
            intent.putExtra("eventId",event.evnetId)
            intent.putExtra("eventTitle",event.eventTitle)
            intent.putExtra("eventContent",event.eventContent)
            intent.putExtra("visibleDate",event.visibleDate)
            intent.putExtra("writerId",event.userId) // 작성자 id

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