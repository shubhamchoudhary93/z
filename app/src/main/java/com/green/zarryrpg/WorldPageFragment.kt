package com.green.zarryrpg

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.green.zarryrpg.data.DatabaseCreate
import com.green.zarryrpg.databinding.WorldPageBinding

class WorldPageFragment : Fragment() {

    private lateinit var binding: WorldPageBinding
    private lateinit var data: SharedPreferences
    var user = User()
    lateinit var mainHandler: Handler
    private var first = true
    private var muggleBool = true

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
            R.layout.world_page, container, false
        )
        mainHandler = Handler(Looper.getMainLooper())

        muggleBool = WorldPageFragmentArgs.fromBundle(requireArguments()).muggle

        val text = if (muggleBool) {
            "Muggle World"
        } else {
            "Wizard World"
        }

        setDATA()
        setListeners()
        setScreenData()

        binding.head.title.text = text
        return binding.root
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
        binding.cookButton.setOnClickListener {
            val actionDetail =
                WorldPageFragmentDirections.actionMuggleWorldPageFragmentToMuggleCookPageFragment()
            actionDetail.muggle = muggleBool
            view?.findNavController()?.navigate(actionDetail)
        }
        binding.exploreButton.setOnClickListener {
            val actionDetail =
                WorldPageFragmentDirections.actionMuggleWorldPageFragmentToMuggleExplorePageFragment()
            actionDetail.muggle = muggleBool
            view?.findNavController()?.navigate(actionDetail)
        }

        binding.inventoryButton.setOnClickListener {
            val actionDetail =
                WorldPageFragmentDirections.actionMuggleWorldPageFragmentToMuggleInventoryPageFragment()
            actionDetail.muggle = muggleBool
            view?.findNavController()?.navigate(actionDetail)
        }

        binding.marketButton.setOnClickListener {
            val actionDetail =
                WorldPageFragmentDirections.actionMuggleWorldPageFragmentToMuggleMarketPageFragment()
            actionDetail.muggle = muggleBool
            view?.findNavController()?.navigate(actionDetail)
        }

        binding.questButton.setOnClickListener {
            val actionDetail =
                WorldPageFragmentDirections.actionMuggleWorldPageFragmentToMuggleQuestPageFragment()
            actionDetail.muggle = muggleBool
            view?.findNavController()?.navigate(actionDetail)
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