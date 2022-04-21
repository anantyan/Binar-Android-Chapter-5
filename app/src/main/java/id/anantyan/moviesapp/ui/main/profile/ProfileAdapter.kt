package id.anantyan.moviesapp.ui.main.profile

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.moviesapp.databinding.ListItemProfileBinding
import id.anantyan.moviesapp.model.Profile

class ProfileAdapter : ListAdapter<Profile, RecyclerView.ViewHolder>(diffUtilCallback),
    ProfileAdapterHelper {
    inner class ViewHolder(private val binding: ListItemProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Profile) {
            binding.imageView.setImageResource(item.resId!!)
            binding.txtTitle.text = item.title
            binding.txtField.text = item.field
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemProfileBinding.inflate(
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

    override fun init(): ListAdapter<Profile, RecyclerView.ViewHolder> {
        return this
    }

    override fun differ(list: List<Profile>) {
        submitList(list)
    }
}

val diffUtilCallback = object : DiffUtil.ItemCallback<Profile>() {
    override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem.resId == newItem.resId
    }

    override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem == newItem
    }
}