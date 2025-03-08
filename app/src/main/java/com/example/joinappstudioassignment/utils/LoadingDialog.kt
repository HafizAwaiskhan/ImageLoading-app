package com.example.joinappstudioassignment.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import com.example.joinappstudioassignment.R


class LoadingDialog(context: Context) : AppCompatDialog(context, R.style.LoadingDialogTheme) {

    init {
        init(context)
    }

    private fun init(context: Context) {
        customizeDialogWindow(context)
    }

    @SuppressLint("InflateParams")
    private fun customizeDialogWindow(context: Context) {
        setContentView(
            LayoutInflater.from(context).inflate(
                R.layout.layout_loading_dialog,
                null
            )
        )

        window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}