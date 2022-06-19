package com.green.zarryrpg

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.green.zarryrpg.data.*
import com.green.zarryrpg.databinding.MuggleCookPageBinding

class MuggleCookPageFragment : Fragment() {

    private lateinit var binding: MuggleCookPageBinding
    private lateinit var data: SharedPreferences
    var user = User()
    private lateinit var inventoryDatabase: InventoryDatabaseDao
    private lateinit var finishRequirementDatabase: FinishRequirementDatabaseDao
    lateinit var mainHandler: Handler
    private var itemList: MutableList<Inventory> = mutableListOf()
    private var cookSelected = 0
    private lateinit var oldColors: ColorStateList
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
    private val updateScreenTask = object : Runnable {
        override fun run() {
            for (i in 0..1) {
                if (user.cook[i].status == 1) {
                    if (System.currentTimeMillis() >= user.cook[i].stopTime) {
                        user.cook[i].status = 2
                    }
                }
            }
            setScreenData()
            mainHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.muggle_cook_page, container, false
        )
        setDATA()
        mainHandler = Handler(Looper.getMainLooper())

        inventoryDatabase = InventoryDatabase.getInstance(requireContext()).inventoryDatabaseDao
        finishRequirementDatabase =
            FinishRequirementDatabase.getInstance(requireContext()).finishRequirementDatabaseDao

        binding.cookPage.visibility = View.GONE
        setListeners()
        setScreenData()
        oldColors = binding.itemCost.textColors
        setScreenData()
        val text = "Cook"
        binding.head.title.text = text
        return binding.root
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

        when (user.cook[0].status) {
            0 -> binding.cookOne.text = ""
            1 -> binding.cookOne.text = "busy"
            else -> binding.cookOne.text = "ready"

        }

        when (user.cook[1].status) {
            0 -> binding.cookTwo.text = ""
            1 -> binding.cookTwo.text = "busy"
            else -> binding.cookTwo.text = "ready"

        }
    }

    private fun setDATA() {
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
    }

    private fun setListeners() {
        binding.cookOne.setOnClickListener {
            cookSelected = 0
            binding.cookPage.visibility = View.VISIBLE
            when (user.cook[0].status) {
                0 -> {
                    binding.cookPage.visibility = View.VISIBLE
                    itemList = inventoryDatabase.getAvailableByWorldAndType("Muggle", "Food")

                    val names: MutableList<String> = mutableListOf()
                    var text: String
                    for (j in itemList.indices) {
                        text = itemList[j].name + " (" + itemList[j].quantity + ")"
                        names.add(text)
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(
                            this.requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            names
                        )
                    binding.itemDropdown.adapter = adapter
                }
                1 -> {
                    binding.cookPage.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "not ready - ${(user.cook[0].stopTime - System.currentTimeMillis()) / 1000} seconds left",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    user.cook[0].status = 0
                    val itemSelectedName =
                        inventoryDatabase.getCorresponding(user.cook[0].item)
                    val itemSelected = inventoryDatabase.getName(itemSelectedName)
                    println(itemSelected?.name)
                    if (itemSelected != null) {
                        itemSelected.quantity++
                        user.xp += itemSelected.xp
                        inventoryDatabase.update(itemSelected)
                    }
                    user.cook[0].item = ""
                    user.cook[0].stopTime = 0L
                    binding.cookPage.visibility = View.GONE
                }
            }
        }
        binding.cookTwo.setOnClickListener {
            cookSelected = 1
            binding.cookPage.visibility = View.VISIBLE
            when (user.cook[1].status) {
                0 -> {
                    binding.cookPage.visibility = View.VISIBLE
                    itemList = inventoryDatabase.getAvailableByWorldAndType("Muggle", "Food")

                    val names: MutableList<String> = mutableListOf()
                    var text: String
                    for (j in itemList.indices) {
                        text = itemList[j].name + " (" + itemList[j].quantity + ")"
                        names.add(text)
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(
                            this.requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            names
                        )
                    binding.itemDropdown.adapter = adapter
                }
                1 -> {
                    binding.cookPage.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "not ready - ${(user.cook[1].stopTime - System.currentTimeMillis()) / 1000} seconds left",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    user.cook[1].status = 0
                    val itemSelectedName =
                        inventoryDatabase.getCorresponding(user.cook[1].item)
                    val itemSelected = inventoryDatabase.getName(itemSelectedName)
                    println(itemSelected?.name)
                    if (itemSelected != null) {
                        itemSelected.quantity++
                        user.xp += itemSelected.xp
                        inventoryDatabase.update(itemSelected)
                    }
                    user.cook[1].item = ""
                    user.cook[1].stopTime = 0L
                    binding.cookPage.visibility = View.GONE
                }
            }
        }

        binding.itemDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                binding.cookButton.isEnabled = true
                val itemSelected = itemList[position]
                binding.itemCost.text = itemSelected.cost.toString()
                binding.itemTime.text = (itemSelected.time / 1000).toString()
                if (itemSelected.cost > user.money) {
                    binding.itemCost.setTextColor(Color.parseColor("#FF0000"))
                } else {
                    binding.itemCost.setTextColor(oldColors)
                }

                val requirementList = finishRequirementDatabase.getByRequirementID(
                    itemSelected.name
                )

                val inventoryList = mutableListOf<Inventory>()

                for (i in requirementList.indices) {
                    inventoryDatabase.getName(requirementList[i].inventory)
                        ?.let { it1 -> inventoryList.add(it1) }
                }
                var text = ""
                for (i in requirementList.indices) {
                    text =
                        text + inventoryList[i].name + " " + requirementList[i].quantity + "\n"
                }

                binding.requirements.text = text
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                binding.cookButton.isEnabled = false
            }
        }
        binding.cookButton.setOnClickListener {
            if (itemList.size >= 1) {
                val itemSelected = itemList[binding.itemDropdown.selectedItemPosition]
                if (itemSelected.cost <= user.money) {
                    val requirementList = finishRequirementDatabase.getByRequirementID(
                        itemSelected.name
                    )

                    val inventoryList = mutableListOf<Inventory>()

                    for (i in requirementList.indices) {
                        inventoryDatabase.getName(requirementList[i].inventory)
                            ?.let { it1 -> inventoryList.add(it1) }
                    }

                    var canCook = 0
                    for (i in requirementList.indices) {
                        if (requirementList[i].quantity <= inventoryList[i].quantity) canCook += 1
                    }

                    if (canCook == requirementList.size) {
                        user.money -= itemSelected.cost
                        for (i in requirementList.indices) {
                            inventoryList[i].quantity =
                                inventoryList[i].quantity - requirementList[i].quantity
                            inventoryDatabase.update(inventoryList[i])
                        }

                        if (cookSelected == 0)
                            binding.cookOne.text = "busy"
                        else binding.cookTwo.text = "busy"

                        user.cook[cookSelected].item = itemSelected.name
                        user.cook[cookSelected].status = 1
                        user.cook[cookSelected].stopTime = System.currentTimeMillis() + itemSelected.time
                        binding.cookPage.visibility = View.GONE
                    } else Toast.makeText(context, "Requirements not met", Toast.LENGTH_SHORT)
                        .show()

                } else Toast.makeText(context, "Not enough money", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        user.lastOnline = System.currentTimeMillis()
        user.lastOnlineStamina = System.currentTimeMillis()
        UserFunctions.saveUser(user, data)
        mainHandler.removeCallbacks(updateStamina)
        mainHandler.removeCallbacks(updateScreenTask)
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
        mainHandler.post(updateScreenTask)
        setScreenData()
    }
}