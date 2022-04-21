package id.anantyan.moviesapp.ui.main.home

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.moviesapp.model.Profile
import id.anantyan.moviesapp.model.ResultsItem

interface HomeAdapterHelper {
    fun init(): ListAdapter<ResultsItem, RecyclerView.ViewHolder>
    fun differ(list: List<ResultsItem>)
    fun onClick(listener: (Int, Int) -> Unit)
}

val adapterTrending: HomeAdapterHelper by lazy { HomeAdapter() }
val adapterPopular: HomeAdapterHelper by lazy { HomeAdapter() }
val adapterTopRated: HomeAdapterHelper by lazy { HomeAdapter() }
val adapterNowPlaying: HomeAdapterHelper by lazy { HomeAdapter() }
val adapterUpComing: HomeAdapterHelper by lazy { HomeAdapter() }