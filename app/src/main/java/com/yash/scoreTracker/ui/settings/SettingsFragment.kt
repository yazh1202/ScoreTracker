package com.yash.scoreTracker.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yash.scoreTracker.R
import com.yash.scoreTracker.databinding.FragmentSettingsBinding
import com.yash.scoreTracker.viewmodels.HomeViewModel


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
                    val i = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    i.hideSoftInputFromWindow(it.windowToken,0)
                } else {
                   Toast.makeText(requireContext(),"Empty Values not allowed",Toast.LENGTH_SHORT).show()
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