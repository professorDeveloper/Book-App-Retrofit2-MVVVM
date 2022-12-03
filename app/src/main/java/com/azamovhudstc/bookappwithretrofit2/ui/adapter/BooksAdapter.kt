package com.azamovhudstc.bookappwithretrofit2.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.BooksResponseItem
import kotlinx.android.synthetic.main.books_item.view.*

class BooksAdapter(var setLongClickListener: ContactItemCallBack.SetLongClickListener) :
    ListAdapter<BooksResponseItem, BooksAdapter.Wh>(ContactItemCallBack) {
    inner class Wh(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("NewApi", "SetTextI18n")
        fun onBind(contact: BooksResponseItem, position: Int) {
            itemView.edit.setOnClickListener {
                setLongClickListener.editItemClick(contact)
                Log.d("!@#", "edit: asdasdasdasdasd")
            }
            itemView.name.text = contact.title
            itemView.author.text=contact.author
            itemView.delete.setOnClickListener {
                setLongClickListener.deleteClick(contact)
                Log.d("!@#", "delete: asdasdasdasdasd")
            }
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
            fun deleteClick(contact: BooksResponseItem)
            fun showClick(contact: BooksResponseItem)
            fun editItemClick(contact: BooksResponseItem)
        }

    }

}