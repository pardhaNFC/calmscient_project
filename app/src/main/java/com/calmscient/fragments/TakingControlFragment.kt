/*
 *
 *      Copyright (c) 2023- NFC Solutions, - All Rights Reserved
 *      All source code contained herein remains the property of NFC Solutions Incorporated
 *      and protected by trade secret or copyright law of USA.
 *      Dissemination, De-compilation, Modification and Distribution are strictly prohibited unless
 *      there is a prior written permission or license agreement from NFC Solutions.
 *
 *      Author : @Pardha Saradhi
 */

package com.calmscient.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import com.calmscient.R
import com.calmscient.databinding.FragmentTakingControlBinding
class TakingControlFragment : Fragment() {

    private lateinit var binding: FragmentTakingControlBinding
    private var alcoholLayoutVisible = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            loadFragment(DiscoveryFragment())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTakingControlBinding.inflate(inflater, container, false)
        binding.btnSummary.setOnClickListener {
            loadFragment(SummaryTakingControlFragment())
        }
        binding.btnDrinkTracker.setOnClickListener {
            loadFragment(DrinkTrackerFragment())
        }
        binding.btnEventTracker.setOnClickListener {
            loadFragment(EventsTrackerFragment())
        }
        binding.alcoholtext.setOnClickListener {
            binding.alcohollayout.visibility = View.VISIBLE
            binding.substancelayouttext.visibility = View.GONE
            updateViewBackgroundColor(binding.viewalcohol, R.color.example_7_button)
            updateViewBackgroundColor(binding.viewsubstance, R.color.viewbackgroundcolor)
        }
        binding.substancetext.setOnClickListener {
            binding.alcohollayout.visibility = View.GONE
            alcoholLayoutVisible = false
            binding.substancelayouttext.visibility = View.VISIBLE
            updateViewBackgroundColor(binding.viewalcohol, R.color.viewbackgroundcolor)
            updateViewBackgroundColor(binding.viewsubstance, R.color.example_7_button)

        }
        binding.needToTalkWithSomeOne.setOnClickListener {
            loadFragment(EmergencyResourceFragment())
        }
        binding.backIcon.setOnClickListener{
            loadFragment(DiscoveryFragment())
        }
        return binding.root
    }
    private fun updateViewBackgroundColor(view: View, colorResId: Int) {
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), colorResId))
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.addToBackStack(null) // This ensures that the previous fragment is added to the back stack
        transaction.commit()
    }
}