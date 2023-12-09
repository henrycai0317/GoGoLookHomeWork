package com.example.gogolookhomework.view

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


object CommonUtil {
    fun hideKeyboard(pContext: Context, et: EditText) {
        val imm =
            pContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et.windowToken, 0)
        et.clearFocus()
    }

    fun showKeyboard(pContext: Context, et: EditText) {
        et.requestFocus()
        val imm = pContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }

}