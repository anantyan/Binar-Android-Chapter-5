package id.anantyan.moviesapp.ui.main.profile

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.moviesapp.model.Profile

interface ProfileAdapterHelper {
    fun init(): ListAdapter<Profile, RecyclerView.ViewHolder>
    fun differ(list: List<Profile>)
}

val adapter: ProfileAdapterHelper by lazy { ProfileAdapter() }