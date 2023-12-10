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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gogolookhomework.R
import com.example.gogolookhomework.databinding.FragmentPhotoSearchHomeBinding
import com.example.gogolookhomework.model.SearchViewModel
import com.example.gogolookhomework.view.CommonUtil
import com.example.gogolookhomework.view.fragment.adapter.PhotoAdapter
import com.example.gogolookhomework.view.fragment.adapter.SearchHistoryAdapter

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
        toggleRecyclerViewLayout()
        initListener()
        initHistoryRecyclerView()
    }

    private fun initHistoryRecyclerView() {
        mViewBinding?.icShowHistory?.apply {
            context?.let { iContext ->
                listHistory.layoutManager = LinearLayoutManager(iContext)
            }
        }
    }

    private fun initListener() {
        mViewBinding?.apply {
            icSearchBar.apply {
                //搜尋框監聽
                editInput.addTextChangedListener(object : TextWatcher {
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

                //KeyBoard 監聽事件
                editInput.setOnEditorActionListener { _, keyCode, event ->
                    if (keyCode == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                        if (mNowSearchStr.toString() != mTempSearchStr.toString()) {
                            Log.d(TAG, "initListener: star search $mNowSearchStr")
                            mViewModel.callSearchApi(mNowSearchStr.toString())
                            mViewModel.insertSearchHistory(mNowSearchStr.toString())
                            mTempSearchStr.clear()
                            mTempSearchStr.append(mNowSearchStr.toString())
                        }
                        icSearchBar.editInput.clearFocus()
                    }
                    false
                }

                //Edit Text Focus 監聽
                editInput.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        icShowHistory.root.visibility = View.VISIBLE
                    } else {
                        icShowHistory.root.visibility = View.GONE
                    }
                }


                //搜尋框清除文字
                ivClearBack.setOnClickListener {
                    icSearchBar.editInput.setText("")
                }

                //切換RecyclerView 佈局
                ivSwitchRcLayout.setOnClickListener {
                    mViewModel.setIsGridLayout()
                    toggleRecyclerViewLayout()
                }

            }

            //歷史選單監聽
            icShowHistory.root.setOnClickListener {
                icShowHistory.root.visibility = View.GONE
                icSearchBar.editInput.clearFocus()
                closeKeyboard()
            }


        }
    }

    /** 處裡切換RecyclerView layout佈局*/
    private fun toggleRecyclerViewLayout() {
        context?.let { iContext ->
            mViewBinding?.apply {
                val iLayoutIcon = ContextCompat.getDrawable(
                    requireContext(),
                    if (mViewModel.getIsGridLayout()) R.drawable.icon_menu_grid else R.drawable.icon_list_view
                )
                icSearchBar.ivSwitchRcLayout.setImageDrawable(iLayoutIcon)
                val iLayoutManager = if (mViewModel.getIsGridLayout()) {
                    GridLayoutManager(iContext, 3)
                } else {
                    LinearLayoutManager(iContext)
                }

                recyclerView.layoutManager = iLayoutManager
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

        mViewModel.getAllHistoryData().observe(viewLifecycleOwner) { iHistoryList ->
            mViewBinding?.apply {
                icShowHistory.listHistory.adapter =
                    SearchHistoryAdapter(iHistoryList, { iSearchStr ->
                        if (iSearchStr != mNowSearchStr.toString()) {
                            icSearchBar.editInput.setText(iSearchStr)
                            mViewModel.callSearchApi(iSearchStr)
                        }
                        icShowHistory.root.visibility = View.GONE
                        icSearchBar.editInput.clearFocus()
                        closeKeyboard()
                    }, { iClearPosition ->
                        mViewModel.deleteSearchHistory(iClearPosition)
                    })
            }
        }
    }

    /**
     * 關閉虛擬鍵盤
     */
    private fun closeKeyboard() {
        mViewBinding?.apply {
            context?.let { iContext ->
                CommonUtil.hideKeyboard(iContext, icSearchBar.editInput)
            }
        }
    }

    /**
     * 顯示虛擬鍵盤
     */
    private fun showKeyboard() {
        mViewBinding?.apply {
            context?.let { iContext ->
                CommonUtil.showKeyboard(iContext, icSearchBar.editInput)
            }
        }
    }
}