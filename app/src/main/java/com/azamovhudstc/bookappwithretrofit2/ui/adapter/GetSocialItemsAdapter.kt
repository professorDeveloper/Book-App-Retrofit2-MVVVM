package com.azamovhudstc.bookappwithretrofit2.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.azamovhudstc.bookappwithretrofit2.R
import com.azamovhudstc.bookappwithretrofit2.retrofit2.response.GetSocialUserResponseItem
import kotlinx.android.synthetic.main.item_user.view.*

class GetSocialItemsAdapter(var setLongClickListener: ContactItemCallBack.SetLongClickListener) :
    ListAdapter<GetSocialUserResponseItem, GetSocialItemsAdapter.Wh>(ContactItemCallBack) {
    inner class Wh(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("NewApi", "SetTextI18n")
        fun onBind(userResponseItem: GetSocialUserResponseItem, position: Int) {
            itemView.name_user.text = "${userResponseItem.firstName} ${userResponseItem.lastName}"
            itemView.setOnClickListener {
                setLongClickListener.userClick(userResponseItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Wh {
        return Wh(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: Wh, position: Int) {
        holder.onBind(getItem(position), position)
    }

    object ContactItemCallBack : DiffUtil.ItemCallback<GetSocialUserResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GetSocialUserResponseItem,
            newItem: GetSocialUserResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetSocialUserResponseItem,
            newItem: GetSocialUserResponseItem
        ): Boolean {
            return oldItem == newItem
        }


        interface SetLongClickListener {
            fun userClick(contact: GetSocialUserResponseItem)
        }

    }

}