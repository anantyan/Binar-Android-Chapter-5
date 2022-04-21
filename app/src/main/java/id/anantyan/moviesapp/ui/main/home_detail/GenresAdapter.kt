package id.anantyan.moviesapp.ui.main.home_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.moviesapp.databinding.ListItemGenresBinding
import id.anantyan.moviesapp.model.GenresItem

class GenresAdapter : ListAdapter<GenresItem, RecyclerView.ViewHolder>(diffUtilGenres),
    HomeDetailAdapterHelper.Genres {
    inner class ViewHolder(private val binding: ListItemGenresBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GenresItem) {
            binding.txtGenres.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemGenresBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val item = getItem(position)
        holder.bind(item)
    }

    override fun init(): ListAdapter<GenresItem, RecyclerView.ViewHolder> {
        return this
    }

    override fun differ(list: List<GenresItem>) {
        submitList(list)
    }
}

val diffUtilGenres = object : DiffUtil.ItemCallback<GenresItem>() {
    override fun areItemsTheSame(oldItem: GenresItem, newItem: GenresItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GenresItem, newItem: GenresItem): Boolean {
        return oldItem == newItem
    }
}