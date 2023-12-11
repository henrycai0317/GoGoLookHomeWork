package com.example.gogolookhomework.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.example.gogolookhomework.databinding.DialogProcessBinding


class ProcessDialog(mActivity: Context) : Dialog(mActivity) {

    private lateinit var mViewBinding: DialogProcessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = DialogProcessBinding.inflate(LayoutInflater.from(context))
        setContentView(mViewBinding.root)

    }

}