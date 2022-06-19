package com.green.zarryrpg

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.green.zarryrpg.data.Inventory
import com.green.zarryrpg.data.Quest

@BindingAdapter("nameString")
fun TextView.setNameString(item: Inventory) {
    item.let {
        text = item.name
    }
}

@BindingAdapter("quantityString")
fun TextView.setQuantityString(item: Inventory) {
    item.let {
        text = item.quantity.toString()

    }
}

@BindingAdapter("costString")
fun TextView.setCostString(item: Inventory) {
    item.let {
        text = item.cost.toString()

    }
}

@BindingAdapter("sellString")
fun TextView.setSellString(item: Inventory) {
    item.let {
        text = item.sell.toString()

    }
}

@BindingAdapter("nameString")
fun TextView.setNameString(item: Quest) {
    item.let {
        text = item.name
    }
}
