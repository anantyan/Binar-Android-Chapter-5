package id.anantyan.moviesapp.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.databinding.ListItemHomeBinding
import id.anantyan.moviesapp.model.Profile
import id.anantyan.moviesapp.model.ResultsItem

class HomeAdapter : ListAdapter<ResultsItem, RecyclerView.ViewHolder>(diffUtilCallback),
    HomeAdapterHelper {

    private var _onClick: ((Int, Int) -> Unit)? = null

    inner class ViewHolder(val binding: ListItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(adapterPosition, getItem(adapterPosition).id!!)
                }
            }
        }

        fun bind(item: ResultsItem) {
            binding.txtNama.text = item.title
            binding.txtRate.text = item.voteAverage.toString()
            binding.imgPosterPath.load(item.posterPath) {
                crossfade(true)
                placeholder(R.drawable.ic_outline_image_24)
                error(R.drawable.ic_outline_image_not_supported_24)
                transformations(RoundedCornersTransformation(16F))
                size(ViewSizeResolver(binding.imgPosterPath))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemHomeBinding.inflate(
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

    override fun init(): ListAdapter<ResultsItem, RecyclerView.ViewHolder> {
        return this
    }

    override fun differ(list: List<ResultsItem>) {
        submitList(list)
    }

    override fun onClick(listener: (Int, Int) -> Unit) {
        _onClick = listener
    }
}

val diffUtilCallback = object : DiffUtil.ItemCallback<ResultsItem>() {
    override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem == newItem
    }
}