package com.dsm.hackathon.feature.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.dsm.hackathon.databinding.ItemHomeDicRecyclerBinding
import com.dsm.hackathon.feature.home.model.InfoData

class DictionaryAdapter(private val dicList: List<InfoData>, private val dicClickListener: DictionaryClickListener) : Adapter<DictionaryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val binding = ItemHomeDicRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DictionaryViewHolder(binding)
    }

    override fun getItemCount(): Int = dicList.size
    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(dicList[position])
        holder.itemView.setOnClickListener {
            dicClickListener.onDicClicked(dicList[position])
        }
    }
    interface DictionaryClickListener {
        fun onDicClicked(data: InfoData)
    }
}