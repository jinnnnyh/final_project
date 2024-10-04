package com.fullstack405.bitcfinalprojectkotlin.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fullstack405.bitcfinalprojectkotlin.data.UserData
import com.fullstack405.bitcfinalprojectkotlin.databinding.TempitemsBinding

class TempAdapter(val tempDatas :List<UserData>): RecyclerView.Adapter<TempAdapter.Holder>() {

  inner class Holder(val binding: TempitemsBinding): RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempAdapter.Holder {
    TODO("Not yet implemented")
  }

  override fun onBindViewHolder(holder: TempAdapter.Holder, position: Int) {
    TODO("Not yet implemented")
  }

  override fun getItemCount(): Int {
    TODO("Not yet implemented")
  }
}