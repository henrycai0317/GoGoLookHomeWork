package com.example.gogolookhomework.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gogolookhomework.databinding.ActivityPhotoSearchBinding

class PhotoSearchActivity : AppCompatActivity() {

    private var mViewBinding: ActivityPhotoSearchBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityPhotoSearchBinding.inflate(layoutInflater)
        setContentView(mViewBinding?.root)

    }
}