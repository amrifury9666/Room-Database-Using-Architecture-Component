package com.example

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecorator(private val topMargin : Int, private val bottomMargin: Int, private val leftMargin : Int, private val rightMargin : Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.top = topMargin
        outRect.bottom = bottomMargin
        outRect.left = leftMargin
        outRect.right = rightMargin

    }


}