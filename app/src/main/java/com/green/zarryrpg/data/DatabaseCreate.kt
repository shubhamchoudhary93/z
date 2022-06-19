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

        inventoryDatabase.insert(
            Inventory(
                2,
                "Broom",
                "Food",
                "Wizard",
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
                3,
                "Hat",
                "Food",
                "Wizard",
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
                1003,
                "Wood",
                "Raw",
                "Wizard",
                1,
                0,
                0,
                10,
                0,
                0,
                10,
                "Wood",
                1,
                0,
                0
            )
        )
        inventoryDatabase.insert(
            Inventory(
                1004,
                "Straw",
                "Raw",
                "Wizard",
                1,
                0,
                0,
                10,
                0,
                0,
                10,
                "Straw",
                1,
                0,
                0
            )
        )
        inventoryDatabase.insert(
            Inventory(
                1005,
                "Cloth",
                "Raw",
                "Wizard",
                1,
                0,
                0,
                10,
                0,
                0,
                10,
                "Cloth",
                1,
                0,
                0
            )
        )

        finishRequirementDatabase.insert(FinishRequirement(0, "Sandwich", "Bread", 1))
        finishRequirementDatabase.insert(FinishRequirement(0, "Sandwich", "Butter", 1))
        finishRequirementDatabase.insert(FinishRequirement(0, "Pancake", "Flour", 1))
        finishRequirementDatabase.insert(FinishRequirement(0, "Pancake", "Butter", 1))

        finishRequirementDatabase.insert(FinishRequirement(0, "Broom", "Wood", 1))
        finishRequirementDatabase.insert(FinishRequirement(0, "Broom", "Straw", 1))
        finishRequirementDatabase.insert(FinishRequirement(0, "Hat", "Cloth", 1))
        finishRequirementDatabase.insert(FinishRequirement(0, "Hat", "Straw", 1))


        questDatabase.insert(Quest(0, "Quest 1 Wizard", "Need Broom", "Broom", 5, "", 0, 0, "Wizard"))
        questDatabase.insert(Quest(0, "Quest 2 Wizard", "Need Broom", "Straw", 10, "", 1, 0, "Wizard"))
        questDatabase.insert(Quest(0, "Quest 2 Wizard", "Need Hat", "Hat", 10, "", 1, 0, "Wizard"))
        questDatabase.insert(Quest(0, "Quest 3 Wizard", "Need Straw", "Straw", 5, "", 0, 0, "Wizard"))

        questRewardDatabase.insert(QuestReward(0, "Quest 1 Wizard", "Money", 100))
        questRewardDatabase.insert(QuestReward(0, "Quest 2 Wizard", "Straw", 10))
        questRewardDatabase.insert(QuestReward(0, "Quest 3 Wizard", "Money", 400))
        questRewardDatabase.insert(QuestReward(0, "Quest 3 Wizard", "Gold", 10))

        questDatabase.insert(Quest(0, "Quest 1 Muggle", "Need Sandwich", "Sandwich", 5, "", 0, 0, "Muggle"))
        questDatabase.insert(Quest(0, "Quest 2 Muggle", "Need Material", "Butter", 10, "", 1, 0, "Muggle"))
        questDatabase.insert(Quest(0, "Quest 2 Muggle", "Need Material", "Flour", 10, "", 1, 0, "Muggle"))
        questDatabase.insert(Quest(0, "Quest 3 Muggle", "Need Pancake", "Pancake", 5, "", 0, 0, "Muggle"))

        questRewardDatabase.insert(QuestReward(0, "Quest 1 Muggle", "Money", 100))
        questRewardDatabase.insert(QuestReward(0, "Quest 2 Muggle", "Sandwich", 10))
        questRewardDatabase.insert(QuestReward(0, "Quest 3 Muggle", "Money", 400))
        questRewardDatabase.insert(QuestReward(0, "Quest 3 Muggle", "Gold", 10))
    }
}
