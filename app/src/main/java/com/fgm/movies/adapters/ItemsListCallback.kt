package com.fgm.movies.adapters

import androidx.recyclerview.widget.DiffUtil
import com.fgm.movies.model.placeholder.PlaceholderContent

class ItemsListCallback(val oldList: MutableList<PlaceholderContent.PlaceholderItem>, val newList: MutableList<PlaceholderContent.PlaceholderItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldListSize
    }

    override fun getNewListSize(): Int {
        return newListSize
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}