package id.anantyan.exerciseproject.ui.adapter.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.CircleCropTransformation
import id.anantyan.exerciseproject.R
import id.anantyan.exerciseproject.databinding.ListItemMessagesBinding
import id.anantyan.exerciseproject.model.Messages

class MessagesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), MessagesHelper {
    private var oldMessages: MutableList<Messages> = mutableListOf()
    private var _onClick : ((Int, Messages) -> Unit)? = null
    private var _onLongClick : ((Int, Messages) -> Unit)? = null

    inner class ViewHolder(val binding: ListItemMessagesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(adapterPosition, oldMessages[adapterPosition])
                }
            }
            itemView.setOnLongClickListener {
                _onLongClick?.let {
                    it(adapterPosition, oldMessages[adapterPosition])
                }
                true
            }
        }

        fun bind(items: Messages) {
            binding.txtName.text = items.name
            binding.txtMessage.text = items.message
            binding.imgView.load(items.image) {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_image_24)
                error(R.drawable.ic_baseline_image_not_supported_24)
                transformations(CircleCropTransformation())
                size(ViewSizeResolver(binding.imgView))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemMessagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val items = oldMessages[position]
        holder.bind(items)
    }

    override fun getItemCount(): Int {
        return oldMessages.size
    }

    override fun init(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return this
    }

    override fun differ(listItem: List<Messages>) {
        val diffUtil = DiffUtilMessages(oldMessages, listItem)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldMessages.clear()
        oldMessages.addAll(listItem)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onClick(listener: (Int, Messages) -> Unit) {
        _onClick = listener
    }

    override fun onLongClick(listener: (Int, Messages) -> Unit) {
        _onLongClick = listener
    }
}

class DiffUtilMessages(
    private val oldMessages: List<Messages>,
    private val newMessages: List<Messages>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldMessages.size

    override fun getNewListSize() = newMessages.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMessages[oldItemPosition].id == newMessages[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldMessages[oldItemPosition].name != newMessages[newItemPosition].name -> {
                false
            }
            oldMessages[oldItemPosition].image != newMessages[newItemPosition].image -> {
                false
            }
            oldMessages[oldItemPosition].message != newMessages[newItemPosition].message -> {
                false
            }
            else -> true
        }
    }
}