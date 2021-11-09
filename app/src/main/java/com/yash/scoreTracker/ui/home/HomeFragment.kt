package com.yash.scoreTracker.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.yash.scoreTracker.databinding.FragmentHomeBinding

private val DEBUG_TAG = "HOMEFRAGMENT"
private lateinit var homeViewModel: HomeViewModel

class HomeFragment : Fragment() {
    private val vm2: HomeViewModel by activityViewModels()
    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var mDetectorTwo: GestureDetectorCompat
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDetector = GestureDetectorCompat(requireContext(), MyGestureListenerOne())
        mDetectorTwo = GestureDetectorCompat(requireContext(), MyGestureListenerTwo())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = vm2
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding?.apply {
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
            hViewModel = homeViewModel
            homeViewModel.message.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled().let {
                 Snackbar.make(binding.root,it.toString(),Snackbar.LENGTH_LONG).setAction("Reset",View.OnClickListener {
                     vm2.resetScores()
                     Snackbar.make(binding.root,"Scores Reset",Snackbar.LENGTH_SHORT).show()
                 }).setActionTextColor(Color.RED).show()
                }
            })
            scoreone.setOnTouchListener { v, event ->
                mDetector.onTouchEvent(event)
                return@setOnTouchListener true
            }
            scoretwo.setOnTouchListener { v, event ->
                mDetectorTwo.onTouchEvent(event)
                return@setOnTouchListener true
            }
        }
        return _binding!!.root

    }

    class MyGestureListenerOne : GestureDetector.SimpleOnGestureListener() {
        val DTAG = "GESTURELISTENER"
        override fun onDown(e: MotionEvent?): Boolean {
            if (e != null) {
                Log.d(DTAG, "Down Press Occurred at ${e.x}  ${e.y}")
            }
            return true
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            if (e != null) {
                Log.d(DTAG, "Single Press Occurred at ${e.x}  ${e.y}")
                homeViewModel.scoreoneInc()

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

    class MyGestureListenerTwo : GestureDetector.SimpleOnGestureListener() {
        val DTAG = "GESTURELISTENER"

        override fun onDown(e: MotionEvent?): Boolean {
            if (e != null) {
                Log.d(DTAG, "Down Press Occurred at ${e.x}  ${e.y}")
            }
            return true
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            if (e != null) {
                Log.d(DTAG, "Single Press Occurred at ${e.x}  ${e.y}")
                homeViewModel.scoretwoInc()
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
                homeViewModel.scoretwoDec()
            } else {
                Log.d(DTAG, "He Went Right X:${e2!!.x} Y: ${e2.y}")
                homeViewModel.scoretwoInc()
            }
            return true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
