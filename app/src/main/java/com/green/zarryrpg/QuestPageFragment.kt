package com.green.zarryrpg

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.green.zarryrpg.data.DatabaseCreate
import com.green.zarryrpg.data.QuestDatabase
import com.green.zarryrpg.data.QuestDatabaseDao
import com.green.zarryrpg.databinding.QuestPageBinding

class QuestPageFragment : Fragment() {

    private lateinit var binding: QuestPageBinding
    private lateinit var questDatabase: QuestDatabaseDao
    private lateinit var data: SharedPreferences
    var user = User()
    private var muggleBool = true

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
            R.layout.quest_page, container, false
        )

        questDatabase = QuestDatabase.getInstance(requireContext()).questDatabaseDao

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

        muggleBool = QuestPageFragmentArgs.fromBundle(requireArguments()).muggle
        val list = if (muggleBool) {
            questDatabase.getAvailable("Muggle")
        } else {
            questDatabase.getAvailable("Wizard")
        }

        val adapter = QuestPageAdaptor(QuestPageAdaptor.QuestListener {
            val actionDetail =
                QuestPageFragmentDirections.actionMuggleQuestPageFragmentToMuggleQuestDetailsPageFragment()
            actionDetail.id = it
            view?.findNavController()?.navigate(actionDetail)
        })

        binding.list.adapter = adapter

        adapter.submitList(list)
        val title = if (muggleBool) {
            "Muggle Quest"
        } else {
            "Wizard Quest"
        }
        binding.head.title.text = title
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