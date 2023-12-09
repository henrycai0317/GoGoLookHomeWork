package com.example.gogolookhomework.view.fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gogolookhomework.R
import com.example.gogolookhomework.databinding.ItemListSearchHistoryBinding

class SearchHistoryAdapter(private val mHistoryList: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HistoryHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_search_history,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mHistoryList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemData = mHistoryList[position]
        (holder as HistoryHolder).setData(itemData)
    }

    inner class HistoryHolder(val pViewBinding: ItemListSearchHistoryBinding) :
        RecyclerView.ViewHolder(pViewBinding.root) {
        init {
            pViewBinding.apply {
                tvHistoryTxt.setOnClickListener {
                    Log.d("HistoryHolder", "tvHistoryTxt click")
                }

                ivDeleteHistory.setOnClickListener {
                    Log.d("HistoryHolder", "ivDeleteHistory click")
                }
            }
        }

        fun setData(pHistoryStr: String) {
            pViewBinding.tvHistoryTxt.text = pHistoryStr
        }
    }
}