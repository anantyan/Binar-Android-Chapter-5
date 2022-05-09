package id.anantyan.exerciseproject.ui.adapter.messages

import androidx.recyclerview.widget.RecyclerView
import id.anantyan.exerciseproject.model.Messages

interface MessagesHelper {
    /**
     * inisialisasi adapter
     * */
    fun init(): RecyclerView.Adapter<RecyclerView.ViewHolder>

    /**
     * add list to adapter
     * */
    fun differ(listItem: List<Messages>)

    /**
     * state onclick
     * */
    fun onClick(listener: (Int, Messages) -> Unit)
    fun onLongClick(listener: (Int, Messages) -> Unit)
}