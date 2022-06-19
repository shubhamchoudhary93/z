package com.green.zarryrpg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.green.zarryrpg.data.Inventory
import com.green.zarryrpg.databinding.InventoryPageItemBinding

class InventoryPageAdaptor(val clickListener: InventoryListener) :
    ListAdapter<Inventory, InventoryPageAdaptor.ViewHolder>(InventoryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: InventoryPageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Inventory,
            clickListener: InventoryListener
        ) {
            binding.inventory = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = InventoryPageItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class InventoryDiffCallback : DiffUtil.ItemCallback<Inventory>() {
        override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
            return oldItem == newItem
        }
    }

    class InventoryListener(val clickListener: (inventoryId: Long) -> Unit) {
        fun onClick(inventory: Inventory) = clickListener(inventory.id.toLong())
    }
}