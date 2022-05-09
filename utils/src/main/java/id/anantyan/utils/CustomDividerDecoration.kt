package id.anantyan.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration

fun dividerVertical(context: Context, start: Int, end: Int): MaterialDividerItemDecoration {
    val divider = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
    divider.dividerInsetStart = start
    divider.dividerInsetEnd = end
    return divider
}

fun dividerHorizontal(context: Context, start: Int, end: Int): MaterialDividerItemDecoration {
    val divider = MaterialDividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
    divider.dividerInsetStart = start
    divider.dividerInsetEnd = end
    return divider
}