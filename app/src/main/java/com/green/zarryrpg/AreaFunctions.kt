package com.green.zarryrpg

import android.view.View
import com.green.zarryrpg.data.Inventory
import com.green.zarryrpg.data.InventoryDatabaseDao
import com.green.zarryrpg.databinding.AreaExplorePageBinding

object AreaFunctions {

    fun calculateFindText(area: Int): String {
        return when (area) {
            1 -> {
                "Bread\n Butter\n Flour"
            }
            2 -> {
                "Straw\n Wood"
            }
            else -> ""
        }
    }

    fun calculateHead(area: Int): String {
        return when (area) {
            1 -> {
                "Muggle Area One"
            }
            2 -> {
                "Wizard Area One"
            }
            else -> ""
        }
    }

    fun search(area: Int, inventoryDatabase: InventoryDatabaseDao, user: User, binding: AreaExplorePageBinding): User {

        val itemList = when (area) {
            1 -> {
                arrayOf(
                    inventoryDatabase.getName("Bread"),
                    inventoryDatabase.getName("Butter"),
                    inventoryDatabase.getName("Flour"),
                    null,
                    null,
                    null,
                    null,
                    null
                )
            }
            2 -> {
                arrayOf(
                    inventoryDatabase.getName("Straw"),
                    inventoryDatabase.getName("Wood"),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            }
            else -> arrayOf(
                Inventory(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        }

        val itemExplored = itemList[(itemList.indices).random()]
        val numberOfItem = (0..2).random()
        user.stamina--
        binding.findingLayout.visibility = View.VISIBLE
        if (itemExplored != null) {
            if (numberOfItem != 0) {
                val text = numberOfItem.toString() + itemExplored.name
                binding.finding.text = text
                itemExplored.quantity += numberOfItem
                inventoryDatabase.update(itemExplored)
                user.xp += itemExplored.xp
            } else {
                val nothing = "nothing"
                binding.finding.text = nothing
            }
        } else {
            val nothing = "nothing"
            binding.finding.text = nothing
        }

        return user
    }
}