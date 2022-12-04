package com.azamovhudstc.bookappwithretrofit2.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import kotlinx.android.synthetic.main.books_item.view.*

class FavouriteAdapter(var setLongClickListener: ContactItemCallBack.SetLongClickListener) :
    ListAdapter<BooksResponseItem, FavouriteAdapter.Wh>(ContactItemCallBack) {
    inner class Wh(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("NewApi", "SetTextI18n")
        fun onBind(contact: BooksResponseItem, position: Int) {
            itemView.imageView.visibility=View.GONE
            itemView.name.text = contact.title
            itemView.author.text = contact.author

            if (contact.fav) {
                itemView.like.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                itemView.like.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            itemView.like.visibility=View.GONE

            itemView.setOnClickListener {
                setLongClickListener.showClick(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Wh {
        return Wh(LayoutInflater.from(parent.context).inflate(R.layout.books_item, parent, false))
    }

    override fun onBindViewHolder(holder: Wh, position: Int) {
        holder.onBind(getItem(position), position)
    }

    object ContactItemCallBack : DiffUtil.ItemCallback<BooksResponseItem>() {
        override fun areItemsTheSame(
            oldItem: BooksResponseItem,
            newItem: BooksResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BooksResponseItem,
            newItem: BooksResponseItem
        ): Boolean {
            return oldItem == newItem
        }


        interface SetLongClickListener {
            fun showClick(contact: BooksResponseItem)
        }

    }

}