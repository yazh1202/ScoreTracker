package com.yash.scoreTracker.ui.about

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.yash.scoreTracker.databinding.FragmentAboutBinding
import java.awt.font.TextAttribute

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        binding.apply {
             gitLink.movementMethod = LinkMovementMethod.getInstance()
            gitLink.setLinkTextColor(Color.BLUE)
            gitLink.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        }
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}