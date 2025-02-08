package com.itson.giftapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itson.giftapp.R
import com.itson.giftapp.data.Friend

class FriendAdapter(private val context: Context, private val friendList: List<Friend>) :
    RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    // Interfaz para el evento de clic del botón "Enviar regalo"
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Variable para almacenar el listener
    private var listener: OnItemClickListener? = null

    // Método para establecer el listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendList[position]
        holder.bind(friend)
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val buttonSendGift: Button = itemView.findViewById(R.id.buttonSendGift)

        fun bind(friend: Friend) {
            imageView.setImageResource(friend.photoResId)
            textViewName.text = friend.name
            buttonSendGift.setOnClickListener {
                // Cuando se hace clic en el botón, se notifica al listener
                listener?.onItemClick(adapterPosition)
            }
        }
    }
}
