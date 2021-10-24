package com.yash.scoreTracker.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yash.scoreTracker.databinding.FragmentHomeBinding

private val DEBUG_TAG = "HOMEFRAGMENT"
private lateinit var homeViewModel: HomeViewModel
private var currView: String = ""

class HomeFragment : Fragment() {
    private lateinit var mDetector: GestureDetectorCompat
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDetector = GestureDetectorCompat(requireContext(), MyGestureListener())
    }

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
            scoreone.setOnTouchListener { v, event ->
                mDetector.onTouchEvent(event)
                return@setOnTouchListener true
            }
            scoretwo.setOnTouchListener { v, event ->
                mDetector.onTouchEvent(event)
                return@setOnTouchListener true
            }
        }
        return _binding!!.root

    }

    class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        val DTAG = "GESTURELISTENER"
        // TODO: 24/10/21 ViewModel must react to both the views

        override fun onDown(e: MotionEvent?): Boolean {
            if (e != null) {
                Log.d(DTAG, "Down Press Occurred at ${e.x}  ${e.y}")
            }
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            if (e != null) {
                Log.d(DTAG, "Single Press Occurred at ${e.x}  ${e.y}")
                homeViewModel.scoreoneInc()
            }
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            if (e != null) {
                Log.d(DTAG, "Double Press Occurred at ${e.x}  ${e.y}")
                homeViewModel.resetScores()
            }
            return true
        }

        override fun onLongPress(e: MotionEvent?) {
            homeViewModel.resetScores()
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.d(DTAG, "$velocityX $velocityY")
            if (velocityX < 0.0) {
                Log.d(DTAG, "He Went Left X:${e1!!.x} Y: ${e1.y}")
                homeViewModel.scoreoneDec()
            } else {
                Log.d(DTAG, "He Went Right X:${e2!!.x} Y: ${e2.y}")
                homeViewModel.scoreoneInc()
            }
            return true
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
