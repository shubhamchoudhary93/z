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
import com.green.zarryrpg.databinding.StartPageBinding

class StartPageFragment : Fragment() {

    private lateinit var binding: StartPageBinding
    private lateinit var data: SharedPreferences
    var user = User()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.start_page, container, false
        )

        binding.worldSelectLayout.visibility = View.GONE
        setDATA()
        setListeners()
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
        binding.welcomeLayout.setOnClickListener {
            binding.welcomeLayout.visibility = View.GONE
            binding.worldSelectLayout.visibility = View.VISIBLE
        }

        binding.muggleWorldButton.setOnClickListener {
            val actionDetail =
                StartPageFragmentDirections.actionStartPageFragmentToMuggleWorldPageFragment()
            actionDetail.muggle = true
            view?.findNavController()?.navigate(actionDetail)
        }

        binding.wizardWorldButton.setOnClickListener {
            val actionDetail =
                StartPageFragmentDirections.actionStartPageFragmentToMuggleWorldPageFragment()
            actionDetail.muggle = false
            view?.findNavController()?.navigate(actionDetail)
        }
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