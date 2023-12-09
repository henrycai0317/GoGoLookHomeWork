package com.example.gogolookhomework.view.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gogolookhomework.R
import com.example.gogolookhomework.connect.Hit
import com.example.gogolookhomework.databinding.ItemListPhotoBinding

class PhotoAdapter(private var mItemList: List<Hit>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext:Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        return ImageHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_photo,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mItemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemData = mItemList[position]
        (holder as ImageHolder).setData(itemData)
    }

  inner class ImageHolder(val pViewBinding: ItemListPhotoBinding) : RecyclerView.ViewHolder(pViewBinding.root) {
            fun setData(pHit:Hit) {
                val imageUrl = pHit.userImageURL
                mContext?.let { iContext ->
                    Glide.with(iContext).load(imageUrl)
                        .placeholder(R.drawable.default_image).into(pViewBinding.ivPhoto)
                }

            }
        }
}