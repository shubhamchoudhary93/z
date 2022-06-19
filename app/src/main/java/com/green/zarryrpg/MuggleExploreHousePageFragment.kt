package com.green.zarryrpg

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.green.zarryrpg.data.DatabaseCreate
import com.green.zarryrpg.data.InventoryDatabase
import com.green.zarryrpg.data.InventoryDatabaseDao
import com.green.zarryrpg.databinding.MuggleExploreHousePageBinding

class MuggleExploreHousePageFragment : Fragment() {

    private lateinit var binding: MuggleExploreHousePageBinding
    private lateinit var data: SharedPreferences
    private var user = User()
    private lateinit var inventoryDatabase: InventoryDatabaseDao
    lateinit var mainHandler: Handler
    private var first = true

    private val updateStamina = object : Runnable {
        override fun run() {
            if (!first) {
                val staminaNew = user.stamina + 1
                if (staminaNew > 100)
                    user.stamina = 100
                else
                    user.stamina = staminaNew
            }
            first = false
            setScreenData()
            mainHandler.postDelayed(this, 60000)
        }
    }

    private fun setScreenData() {
        user = UserFunctions.calculateLevel(user)
        binding.head.name.text = user.name
        binding.head.money.text = user.money.toString()
        binding.head.gold.text = user.gold.toString()
        binding.head.level.text = user.level.toString()
        binding.head.food.text = user.stamina.toString()
        val xpText = user.xp.toString() + "/" + user.nextXp.toString()
        binding.head.xp.text = xpText
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.muggle_explore_house_page, container, false
        )
        data = requireActivity().getSharedPreferences("ZarryRPGData", Context.MODE_PRIVATE)
        user = if (data.contains("User")) {
            UserFunctions.fetchUser(data)
        } else {
            user.also {
                user.lastOnline = System.currentTimeMillis()
                user.lastOnlineStamina = System.currentTimeMillis()
                UserFunctions.saveUser(user, data)
                DatabaseCreate.createFirst(requireContext())
            }
        }

        mainHandler = Handler(Looper.getMainLooper())

        inventoryDatabase = InventoryDatabase.getInstance(requireContext()).inventoryDatabaseDao
        binding.findingLayout.visibility = View.GONE

        val text1 = "Bread\n Butter\n Flour"
        binding.canFind.text = text1
        setListeners()
        setScreenData()
        val text = "Explore: House"
        binding.head.title.text = text
        return binding.root
    }

    private fun setListeners() {
        binding.exploreButton.setOnClickListener {
            if (user.stamina >= 1) {
                val itemList = arrayOf(
                    inventoryDatabase.getName("Bread"),
                    inventoryDatabase.getName("Butter"),
                    inventoryDatabase.getName("Flour"),
                    null,
                    null,
                    null,
                    null,
                    null
                )

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
            } else {
                Toast.makeText(context, "No more food", Toast.LENGTH_SHORT).show()
                binding.findingLayout.visibility = View.GONE
            }
            setScreenData()
        }
    }

    override fun onPause() {
        super.onPause()
        user.lastOnline = System.currentTimeMillis()
        user.lastOnlineStamina = System.currentTimeMillis()
        UserFunctions.saveUser(user, data)
        mainHandler.removeCallbacks(updateStamina)
    }

    override fun onResume() {
        super.onResume()
        user = UserFunctions.fetchUser(data)
        val currentTime = System.currentTimeMillis()
        val staminaAdd: Int = ((currentTime - user.lastOnlineStamina) / 60000).toInt()
        val staminaNew = user.stamina + staminaAdd
        if (staminaNew > 100)
            user.stamina = 100
        else
            user.stamina = staminaNew
        mainHandler.post(updateStamina)
        setScreenData()
    }

}