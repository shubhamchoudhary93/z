package com.green.zarryrpg.data

import android.content.Context

object DatabaseCreate {

    private lateinit var inventoryDatabase: InventoryDatabaseDao
    private lateinit var questDatabase: QuestDatabaseDao
    private lateinit var finishRequirementDatabase: FinishRequirementDatabaseDao
    private lateinit var questRewardDatabase: QuestRewardDatabaseDao

    fun createFirst(context: Context) {

        inventoryDatabase = InventoryDatabase.getInstance(context).inventoryDatabaseDao
        finishRequirementDatabase =
            FinishRequirementDatabase.getInstance(context).finishRequirementDatabaseDao
        questDatabase = QuestDatabase.getInstance(context).questDatabaseDao
        questRewardDatabase = QuestRewardDatabase.getInstance(context).questRewardDatabaseDao


        inventoryDatabase.insert(
            Inventory(
                0,
                "Sandwich",
                "Food",
                "Muggle",
                0,
                10,
                0,
                100,
                20000,
                0,
                10,
                "Sandwich",
                0,
                1,
                0
            )
        )
        inventoryDatabase.insert(
            Inventory(
                1,
                "Pancake",
                "Food",
                "Muggle",
                0,
                10,
                0,
                100,
                60000,
                0,
                10,
                "Pancake",
                0,
                1,
                0
            )
        )

        inventoryDatabase.insert(
            Inventory(
                1000,
                "Bread",
                "Raw",
                "Muggle",
                1,
                0,
                0,
                10,
                0,
                0,
                10,
                "Bread",
                1,
                0,
                0
            )
        )
        inventoryDatabase.insert(
            Inventory(
                1001,
                "Butter",
                "Raw",
                "Muggle",
                1,
                0,
                0,
                10,
                0,
                0,
                10,
                "Butter",
                1,
                0,
                0
            )
        )
        inventoryDatabase.insert(
            Inventory(
                1002,
                "Flour",
                "Raw",
                "Muggle",
                1,
                0,
                0,
                10,
                0,
                0,
                10,
                "Flour",
                1,
                0,
                0
            )
        )

        finishRequirementDatabase.insert(FinishRequirement(0, "Sandwich", "Bread", 1))
        finishRequirementDatabase.insert(FinishRequirement(0, "Sandwich", "Butter", 1))
        finishRequirementDatabase.insert(FinishRequirement(0, "Pancake", "Flour", 1))
        finishRequirementDatabase.insert(FinishRequirement(0, "Pancake", "Butter", 1))

        questDatabase.insert(Quest(0, "Quest 1", "Need Sandwich", "Sandwich", 5, "", 0, 0))
        questDatabase.insert(Quest(0, "Quest 2", "Need Material", "Butter", 10, "", 1, 0))
        questDatabase.insert(Quest(0, "Quest 2", "Need Material", "Flour", 10, "", 1, 0))
        questDatabase.insert(Quest(0, "Quest 3", "Need Pancake", "Pancake", 5, "", 0, 0))

        questRewardDatabase.insert(QuestReward(0, "Quest 1", "Money", 100))
        questRewardDatabase.insert(QuestReward(0, "Quest 2", "Sandwich", 10))
        questRewardDatabase.insert(QuestReward(0, "Quest 3", "Money", 400))
        questRewardDatabase.insert(QuestReward(0, "Quest 3", "Gold", 10))
    }
}
