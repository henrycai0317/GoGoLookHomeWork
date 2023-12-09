package com.example.gogolookhomework.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gogolookhomework.R
import com.example.gogolookhomework.databinding.FragmentPhotoSearchHomeBinding
import com.example.gogolookhomework.model.SearchViewModel
import com.example.gogolookhomework.view.fragment.adapter.PhotoAdapter

class PhotoSearchHomeFragment : Fragment() {

    private var mViewBinding: FragmentPhotoSearchHomeBinding? = null
    private val mViewModel by activityViewModels<SearchViewModel>()
    private val TAG = "PhotoSearchHomeFragment"
    private var mHasInit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        setHasOptionsMenu(true)
        if (!mViewModel.getHadInitHomeFragment()) {
            mViewModel.callSearchApi("default")
            mViewModel.setHadInitHomeFragment(true)
        }
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        @Suppress("DEPRECATION")
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_photo_bar, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menuItemSearch)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG, "onQueryTextSubmit: $query")
                    mViewModel.callSearchApi(query ?: "")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG, "onQueryTextChange: $newText")
                    return false
                }

            })
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