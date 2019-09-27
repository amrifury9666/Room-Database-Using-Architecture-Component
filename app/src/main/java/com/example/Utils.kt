package com.example

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

object Utils {


    fun convertDpToPx(context: Context, dp: Int): Int {

        val r: Resources = context.resources
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
        ).roundToInt()
    }

}