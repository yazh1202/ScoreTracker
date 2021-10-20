package com.yash.scoreTracker.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.MotionEvent.*
import android.widget.TextView
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yash.scoreTracker.R
import com.yash.scoreTracker.databinding.FragmentHomeBinding
private val DEBUG_TAG = "HOMEFRAGMENT"
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding?.apply {
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
            hViewModel = homeViewModel
            scoretwo.setOnLongClickListener {
                homeViewModel.resetScores()
                true
            }
        }
        return _binding!!.root

    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
