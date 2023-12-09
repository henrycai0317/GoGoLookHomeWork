package com.example.gogolookhomework.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gogolookhomework.databinding.FragmentPhotoSearchHomeBinding
import com.example.gogolookhomework.model.SearchViewModel
import com.example.gogolookhomework.view.fragment.adapter.PhotoAdapter

class PhotoSearchHomeFragment : Fragment() {

    private var mViewBinding: FragmentPhotoSearchHomeBinding? = null
    private val mViewModel by activityViewModels<SearchViewModel>()
    private val TAG = "PhotoSearchHomeFragment"
    private val mNowSearchStr = StringBuilder("")
    private val mTempSearchStr = StringBuilder("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!mViewModel.getHadInitHomeFragment()) {
            mViewModel.callSearchApi("default")
            mViewModel.setHadInitHomeFragment(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = FragmentPhotoSearchHomeBinding.inflate(inflater, container, false)
        return mViewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initRecyclerView()
        initListener()
    }

    private fun initListener() {
        mViewBinding?.apply {
            //搜尋框監聽
            icSearchBar.editInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(pInput: Editable?) {
                    val iStrInput = pInput.toString()
                    mNowSearchStr.clear()
                    mNowSearchStr.append(iStrInput)
                    Log.d(TAG, "afterTextChanged: ${pInput.toString()}")
                    icSearchBar.ivClearBack.visibility =
                        if (iStrInput.isNotEmpty()) View.VISIBLE else View.GONE
                }
            })

            //搜尋框清除文字
            icSearchBar.ivClearBack.setOnClickListener {
                icSearchBar.editInput.setText("")
            }

            //KeyBoard 監聽事件
            icSearchBar.editInput.setOnEditorActionListener { _, keyCode, event ->
                if (keyCode == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (mNowSearchStr.toString() != mTempSearchStr.toString()) {
                        Log.d(TAG, "initListener: star search $mNowSearchStr")
                        mViewModel.callSearchApi(mNowSearchStr.toString())
                        mTempSearchStr.clear()
                        mTempSearchStr.append(mNowSearchStr.toString())
                    }
                }
                false
            }
        }
    }

    private fun initRecyclerView() {
        mViewBinding?.apply {
            context?.let { iContext ->
                recyclerView.layoutManager = GridLayoutManager(iContext, 3)
            }
        }
    }

    private fun initObserver() {
        mViewModel.getSearchRes().observe(viewLifecycleOwner) {
            it.let { iRes ->
                if (iRes.isSuccessful) {
                    iRes.body()?.hits?.let { iData ->
                        if (iData.isNotEmpty()) {
                            mViewBinding?.recyclerView?.adapter = PhotoAdapter(iData)
                        } else {
                            //Error service dialog
                        }
                    }
                } else {
                    //Error service dialog
                }
            }
        }
    }
}