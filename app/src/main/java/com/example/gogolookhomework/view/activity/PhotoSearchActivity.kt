package com.example.gogolookhomework.view.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.gogolookhomework.R
import com.example.gogolookhomework.databinding.ActivityPhotoSearchBinding

class PhotoSearchActivity : AppCompatActivity() {

    private var mViewBinding: ActivityPhotoSearchBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //狀態欄更新
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this@PhotoSearchActivity, R.color.cl60C395)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        mViewBinding = ActivityPhotoSearchBinding.inflate(layoutInflater)
        setContentView(mViewBinding?.root)

    }
}