package com.example.gogolookhomework.view.fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gogolookhomework.R
import com.example.gogolookhomework.databinding.ItemListSearchHistoryBinding
import com.example.gogolookhomework.model.room.SearchHistory

class SearchHistoryAdapter(
    private val mHistoryList: List<SearchHistory>,
    val mItemClickConsumer: (String) -> Unit,
    val mDeleteClickConsumer: (Int) -> Unit,
    val mDeleteAllConsumer: () -> Unit
) :
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
        (holder as HistoryHolder).apply {
            val itemData = mHistoryList[position]
            pViewBinding.tvClearAll.visibility = if (position == 0) View.VISIBLE else View.GONE
            setData(itemData.query, itemData.id)
        }
    }

    inner class HistoryHolder(val pViewBinding: ItemListSearchHistoryBinding) :
        RecyclerView.ViewHolder(pViewBinding.root) {

        fun setData(pHistoryStr: String, position: Int) {
            pViewBinding.tvHistoryTxt.text = pHistoryStr

            pViewBinding.apply {
                clHistoryItem.setOnClickListener {
                    Log.d("HistoryHolder", "root click")
                    mItemClickConsumer(pHistoryStr)
                }

                ivDeleteHistory.setOnClickListener {
                    Log.d("HistoryHolder", "ivDeleteHistory click")
                    mDeleteClickConsumer(position)
                }

                tvClearAll.setOnClickListener {
                    Log.d("HistoryHolder", "tvClearAll click")
                    mDeleteAllConsumer()
                }
            }
        }
    }
}