package id.anantyan.exerciseproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.exerciseproject.databinding.ListItemOnBoardBinding

class OnBoardAdapter(
    val item: List<Int>
) : ListAdapter<Int, RecyclerView.ViewHolder>(DiffUtilOnBoard.diffCallback) {

    inner class ViewHolder(private val binding: ListItemOnBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            binding.imgView.setImageResource(item)
        }
    }

    init {
        submitList(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemOnBoardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val item = getItem(position)
        item?.let {
            holder.bind(item)
        }
    }
}

object DiffUtilOnBoard {
    val diffCallback = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}