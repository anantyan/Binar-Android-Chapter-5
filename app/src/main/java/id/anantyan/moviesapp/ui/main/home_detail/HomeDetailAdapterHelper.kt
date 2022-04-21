package id.anantyan.moviesapp.ui.main.home_detail

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.moviesapp.model.CastItem
import id.anantyan.moviesapp.model.GenresItem

interface HomeDetailAdapterHelper {
    interface Genres {
        fun init(): ListAdapter<GenresItem, RecyclerView.ViewHolder>
        fun differ(list: List<GenresItem>)
    }

    interface Caster {
        fun init(): ListAdapter<CastItem, RecyclerView.ViewHolder>
        fun differ(list: List<CastItem>)
    }
}

val adapterCaster: HomeDetailAdapterHelper.Caster by lazy { CasterAdapter() }
val adapterGenres: HomeDetailAdapterHelper.Genres by lazy { GenresAdapter() }