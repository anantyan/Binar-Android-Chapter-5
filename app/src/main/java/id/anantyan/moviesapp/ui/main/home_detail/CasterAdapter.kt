package id.anantyan.moviesapp.ui.main.home_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.databinding.ListItemCreditsBinding
import id.anantyan.moviesapp.model.CastItem

class CasterAdapter : ListAdapter<CastItem, RecyclerView.ViewHolder>(diffUtilCaster),
    HomeDetailAdapterHelper.Caster {
    inner class ViewHolder(private val binding: ListItemCreditsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CastItem) {
            binding.txtName.text = item.originalName
            binding.txtCharacter.text = item.character
            binding.imgProfilePath.load(item.profilePath) {
                crossfade(true)
                placeholder(R.drawable.ic_outline_image_24)
                error(R.drawable.ic_outline_image_not_supported_24)
                transformations(RoundedCornersTransformation(16F))
                size(ViewSizeResolver(binding.imgProfilePath))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemCreditsBinding.inflate(
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

    override fun init(): ListAdapter<CastItem, RecyclerView.ViewHolder> {
        return this
    }

    override fun differ(list: List<CastItem>) {
        submitList(list)
    }
}

val diffUtilCaster = object : DiffUtil.ItemCallback<CastItem>() {
    override fun areItemsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
        return oldItem == newItem
    }
}