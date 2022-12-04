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
import com.azamovhudstc.bookappwithretrofit2.retrofit2.request.GetUserBooksResponseItem
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.GetUserBookItem
import kotlinx.android.synthetic.main.books_item.view.*
import kotlinx.android.synthetic.main.books_item_byuser.view.*

class UserBooksAdapter(var setLongClickListener: ContactItemCallBack.SetLongClickListener) :
    ListAdapter<GetUserBooksResponseItem, UserBooksAdapter.Wh>(ContactItemCallBack) {
    inner class Wh(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("NewApi", "SetTextI18n")
        fun onBind(contact: GetUserBooksResponseItem) {
            itemView.name_book.text = contact.title
            itemView.author_book.text = contact.author
            itemView.setOnClickListener {
                setLongClickListener.show(contact)
            }
            itemView.liked.setOnClickListener {

                setLongClickListener.liked(contact,"like")


            }
            itemView.disLike.setOnClickListener {
                setLongClickListener.liked(contact,"unLike")

            }
            if (contact.likeCount>contact.disLikeCount) {
                itemView.liked.setImageResource(R.drawable.like)
                itemView.disLike.setImageResource(R.drawable.unlike)


            } else if (contact.likeCount<=contact.disLikeCount) {
                itemView.disLike.setImageResource(R.drawable.ic_baseline_thumb_down_24)
                itemView.liked.setImageResource(R.drawable.ic_baseline_thumb_up_24)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Wh {
        return Wh(
            LayoutInflater.from(parent.context).inflate(R.layout.books_item_byuser, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Wh, position: Int) {
        holder.onBind(getItem(position))
    }

    object ContactItemCallBack : DiffUtil.ItemCallback<GetUserBooksResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GetUserBooksResponseItem,
            newItem: GetUserBooksResponseItem
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.title==newItem.title
        }

        override fun areContentsTheSame(
            oldItem: GetUserBooksResponseItem,
            newItem: GetUserBooksResponseItem
        ): Boolean {
            return oldItem == newItem && oldItem.title==newItem.title
        }


        interface SetLongClickListener {
            fun liked(data:GetUserBooksResponseItem,type:String)
            fun show(data:GetUserBooksResponseItem)
        }

    }

}