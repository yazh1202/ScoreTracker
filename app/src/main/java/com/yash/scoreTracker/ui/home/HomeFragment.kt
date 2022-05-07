package com.yash.scoreTracker.ui.home

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.yash.scoreTracker.MainActivity
import com.yash.scoreTracker.R
import com.yash.scoreTracker.databinding.FragmentHomeBinding
import com.yash.scoreTracker.viewmodels.HomeViewModel

private val DEBUG_TAG = "HOMEFRAGMENT"
private lateinit var homeViewModel: HomeViewModel
private const val CHANNEL_ID = "THISCHANNEL"

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
            //To detect if the game is over or not
            homeViewModel.message.observe(viewLifecycleOwner) { it ->
                it.getContentIfNotHandled().let {
                    Snackbar.make(binding.root, it.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Reset") {
                            vm2.resetScores()
                            Snackbar.make(binding.root, "Scores Reset", Snackbar.LENGTH_SHORT)
                                .show()
                        }.setActionTextColor(Color.RED).show()
                }
            }
            scoreone.setOnTouchListener {_, event ->
                mDetector.onTouchEvent(event)
                return@setOnTouchListener true
            }
            scoretwo.setOnTouchListener { _, event ->
                mDetectorTwo.onTouchEvent(event)
                return@setOnTouchListener true
            }
        }
        return _binding!!.root
    }

    //Gesture listener for the First Score
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

    //Gesture listener for the second score
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

    //Contains code to make notifications
    private fun getNotifBuilder(): NotificationCompat.Builder {
        val notificationId = 2
        // Create an explicit intent for an Activity in your app
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        createNotificationChannel()
        return NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_home_icon)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(
                requireContext(),
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}
