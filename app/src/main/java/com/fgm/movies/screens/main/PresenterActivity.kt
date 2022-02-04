package com.fgm.movies.screens.main

import android.widget.ProgressBar
import android.widget.TextView
import com.fgm.movies.R

class PresenterActivity(val activity: MainActivity) {

    fun setTitleToolBar(title : String){
        activity.supportActionBar?.setTitle(title)
    }

    fun backArrow(visible : Boolean){
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    fun progressBar() : ProgressBar{
        return activity.findViewById(R.id.progressBar)
    }

}