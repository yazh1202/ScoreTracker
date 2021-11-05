package com.yash.scoreTracker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.yash.scoreTracker.R
import com.yash.scoreTracker.databinding.FragmentSettingsBinding
import com.yash.scoreTracker.ui.home.HomeViewModel

class SettingsFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentSettingsBinding? = null
    val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        _binding!!.apply {
            enterbutton.setOnClickListener {
                if (!(nametwo.text.isNullOrEmpty() || nameone.text.isNullOrEmpty()||MaxScore.text.isNullOrEmpty())) {
                    homeViewModel.saveNames(
                        name1 = nameone.text.toString(),
                        name2 = nametwo.text.toString()
                    )
                    homeViewModel.setMaxScore(MaxScore =MaxScore.text.toString().toInt())
                  Toast.makeText(requireContext(),"Saved",Toast.LENGTH_SHORT).show()
                   val navController =  this@SettingsFragment.findNavController()
                    navController.navigate(R.id.action_nav_settings_to_nav_home)
                } else {
                   Toast.makeText(requireContext(),"This is Empty",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}