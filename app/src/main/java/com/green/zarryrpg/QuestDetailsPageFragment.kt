package com.green.zarryrpg

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.green.zarryrpg.data.*
import com.green.zarryrpg.databinding.QuestDetailsPageBinding

class QuestDetailsPageFragment : Fragment() {

    private lateinit var binding: QuestDetailsPageBinding
    private lateinit var questDatabase: QuestDatabaseDao
    private lateinit var inventoryDatabase: InventoryDatabaseDao
    private lateinit var questRewardDatabase: QuestRewardDatabaseDao
    private lateinit var data: SharedPreferences
    var user = User()

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
            R.layout.quest_details_page, container, false
        )

        questDatabase = QuestDatabase.getInstance(requireContext()).questDatabaseDao
        questRewardDatabase =
            QuestRewardDatabase.getInstance(requireContext()).questRewardDatabaseDao
        inventoryDatabase = InventoryDatabase.getInstance(requireContext()).inventoryDatabaseDao

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

        setScreenData()

        val id = QuestDetailsPageFragmentArgs.fromBundle(requireArguments()).id
        val quest = questDatabase.get(id)
        binding.name.text = quest?.name
        binding.detail.text = quest?.detail

        val requirementList = quest?.name?.let {
            questDatabase.getByName(
                it
            )
        }

        var text = ""
        if (requirementList != null) {
            for (i in requirementList.indices) {
                text += requirementList[i].inventory + " "
                val inventoryValue = inventoryDatabase.getName(requirementList[i].inventory)
                if (inventoryValue != null) {
                    if (requirementList[i].quantity > inventoryValue.quantity)
                        text += "<font color=#FF0000>" + requirementList[i].quantity.toString() + "</font>" + "<br>"
                    else text += requirementList[i].quantity.toString() + "<br>"
                }
            }
        }

        binding.requirements.text = Html.fromHtml(text)

        val rewardList = quest?.name?.let {
            questRewardDatabase.getByName(
                it
            )
        }

        text = ""
        if (rewardList != null) {
            for (i in rewardList.indices) {
                text += rewardList[i].inventory + " "
                text += rewardList[i].quantity.toString() + "<br>"
            }
        }

        binding.rewards.text = Html.fromHtml(text)
        val title = "Quest Details"
        binding.head.title.text = title

        binding.collect.setOnClickListener {
            if (quest != null) {
                if(quest.completed != 1) {
                    if (requirementList != null) {
                        var trueCheck = 0
                        for (i in requirementList.indices) {
                            val inventory = requirementList[i].inventory
                            val quantity = requirementList[i].quantity

                            val inventoryValue = inventoryDatabase.getName(inventory)
                            if (inventoryValue != null) {
                                if (inventoryValue.quantity >= quantity) trueCheck += 1
                            }
                        }
                        if (trueCheck == requirementList.size) {
                            for (i in requirementList.indices) {
                                val inventory = requirementList[i].inventory
                                val quantity = requirementList[i].quantity

                                val inventoryValue = inventoryDatabase.getName(inventory)
                                if (inventoryValue != null) {
                                    inventoryValue.quantity -= quantity
                                    inventoryDatabase.update(inventoryValue)
                                }
                            }
                            if (rewardList != null) {
                                for (i in rewardList.indices) {
                                    val inventory = rewardList[i].inventory
                                    val quantity = rewardList[i].quantity

                                    when (inventory) {
                                        "Money" -> {
                                            user.money += quantity
                                        }
                                        "Gold" -> {
                                            user.gold += quantity
                                        }
                                        else -> {
                                            val inventoryValue =
                                                inventoryDatabase.getName(inventory)
                                            if (inventoryValue != null) {
                                                inventoryValue.quantity += quantity
                                                inventoryDatabase.update(inventoryValue)
                                            }
                                        }
                                    }
                                }
                            }

                            quest.completed = 1
                            questDatabase.update(quest)

                            view?.findNavController()
                                ?.navigate(R.id.action_muggleQuestDetailsPageFragment_to_muggleQuestPageFragment)
                        } else {
                            Toast.makeText(context, "Requirements not met", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else Toast.makeText(context, "Reward already collected", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }


    override fun onPause() {
        super.onPause()
        user.lastOnline = System.currentTimeMillis()
        UserFunctions.saveUser(user, data)
    }

    override fun onResume() {
        super.onResume()
        user = UserFunctions.fetchUser(data)
    }

}