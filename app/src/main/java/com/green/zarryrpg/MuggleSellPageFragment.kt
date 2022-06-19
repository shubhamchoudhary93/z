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
import com.green.zarryrpg.data.Inventory
import com.green.zarryrpg.data.InventoryDatabase
import com.green.zarryrpg.data.InventoryDatabaseDao
import com.green.zarryrpg.databinding.MuggleSellPageBinding

class MuggleSellPageFragment : Fragment() {

    private lateinit var binding: MuggleSellPageBinding
    private lateinit var inventoryDatabase: InventoryDatabaseDao
    private lateinit var data: SharedPreferences
    private var user = User()
    private var inventorySelected: Inventory = Inventory()
    private var max = 0
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
            R.layout.muggle_sell_page, container, false
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

        binding.sellButtonLayout.visibility = View.GONE
        inventoryDatabase = InventoryDatabase.getInstance(requireContext()).inventoryDatabaseDao
        fetchAdaptor()
        setListeners()
        setScreenData()
        val text = "Market: Sell"
        binding.head.title.text = text
        return binding.root
    }

    private fun fetchAdaptor() {
        val list = inventoryDatabase.getAllSell("Muggle")

        val adapter = MuggleSellPageAdaptor(MuggleSellPageAdaptor.InventoryListener {
            binding.sellButtonLayout.visibility = View.VISIBLE
            inventorySelected = inventoryDatabase.get(it)!!
            binding.inventorySelected.text = inventorySelected.name
            max = inventorySelected.quantity
            binding.maxInventory.text = max.toString()
        })

        binding.list.adapter = adapter

        adapter.submitList(list)
    }

    private fun setListeners() {
        binding.sellButton.setOnClickListener {
            val q = binding.quantitySell.text.toString().toInt()
            if (q <= max) {
                val sellAmount = q * inventorySelected.sell
                inventorySelected.quantity -= q
                inventoryDatabase.update(inventorySelected)
                user.money += sellAmount
                binding.sellButtonLayout.visibility = View.GONE
                fetchAdaptor()
                setScreenData()
            } else Toast.makeText(context, "Max quantity can be $max.", Toast.LENGTH_SHORT).show()
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