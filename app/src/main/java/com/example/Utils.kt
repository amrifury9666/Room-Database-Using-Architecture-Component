package com.example

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

object Utils {


    fun convertDpToPx(context: Context, dp: Int): Int {

        val r: Resources = context.resources
        return Math.round(
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dp.toFloat(),
                        r.displayMetrics
                )
        )
    }

}