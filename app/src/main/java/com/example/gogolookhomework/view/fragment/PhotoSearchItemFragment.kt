package com.example.gogolookhomework.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gogolookhomework.R
import com.example.gogolookhomework.databinding.FragmentPhotoSearchItemBinding

class PhotoSearchItemFragment : Fragment() {
    private var mViewBinding: FragmentPhotoSearchItemBinding? = null
    private var mPhotoUrl: String? = null

    companion object {
        private const val EXTRA_PHOTO_URL = "PHOTO_URL"

        fun newBundle(pUrl: String) = Bundle().apply {
            putString(EXTRA_PHOTO_URL, pUrl)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPhotoUrl = arguments?.getString(EXTRA_PHOTO_URL) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = FragmentPhotoSearchItemBinding.inflate(inflater, container, false)
        return mViewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initListener() {
        mViewBinding?.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initView() {
        mViewBinding?.apply {
            activity?.let { iAct ->
                mPhotoUrl?.let { iUrl ->
                    Glide.with(iAct).load(iUrl).placeholder(R.drawable.default_image)
                        .into(ivPhoto)
                }
            }
        }
    }
}