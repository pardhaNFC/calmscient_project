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

package com.calmscient.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.calmscient.R
import com.calmscient.databinding.ActivitySettingsBinding
import com.calmscient.utils.getColorCompat

class SettingsActivity : AppCompatActivity(), View.OnClickListener {
    private var currentClickedLayoutId: Int = R.id.English // Initialize with the ID of English layout
    private var isFirstTime = true // Flag to determine if it's the first time onResume is called
    lateinit var  binding : ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val englishParentLayout = findViewById<View>(R.id.English)
        val spanishParentLayout = findViewById<View>(R.id.Spanish)
        val aslParentLayout = findViewById<View>(R.id.ASL)

        // Set click listeners for the parent layouts
        englishParentLayout.setOnClickListener(this)
        spanishParentLayout.setOnClickListener(this)
        aslParentLayout.setOnClickListener(this)

        binding.backIcon.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFirstTime) {
            // Set the background of the English layout to the clicked state
            val englishLayout = findViewById<View>(R.id.English)
            englishLayout.setBackgroundResource(R.drawable.rectangle_clicked)
            binding.tvEnglish.setTextColor(this.getColorCompat(R.color.white))
            binding.tvSpanish.setTextColor(this.getColorCompat(R.color.black))
            binding.tvAsl.setTextColor(this.getColorCompat(R.color.black))
            isFirstTime = false
        }
    }

    override fun onClick(v: View?) {
        // Handle click events here
        when (v?.id) {
            R.id.English -> {
                // Code to handle click on English layout
                updateBackground(currentClickedLayoutId, v.id)
                binding.tvEnglish.setTextColor(this.getColorCompat(R.color.white))
                binding.tvSpanish.setTextColor(this.getColorCompat(R.color.black))
                binding.tvAsl.setTextColor(this.getColorCompat(R.color.black))
            }
            R.id.Spanish -> {
                // Code to handle click on Spanish layout
                updateBackground(currentClickedLayoutId, v.id)
                binding.tvSpanish.setTextColor(this.getColorCompat(R.color.white))
                binding.tvEnglish.setTextColor(this.getColorCompat(R.color.black))
                binding.tvAsl.setTextColor(this.getColorCompat(R.color.black))
            }
            R.id.ASL -> {
                // Code to handle click on ASL layout
                updateBackground(currentClickedLayoutId, v.id)
                binding.tvAsl.setTextColor(this.getColorCompat(R.color.white))
                binding.tvEnglish.setTextColor(this.getColorCompat(R.color.black))
                binding.tvSpanish.setTextColor(this.getColorCompat(R.color.black))
            }
        }
    }

    private fun updateBackground(previousId: Int, currentId: Int) {
        val previousLayout = findViewById<View>(previousId)
        val previousTextView = previousLayout.findViewById<TextView>(R.id.tv_english)

        previousLayout.setBackgroundResource(R.drawable.rectangle_normal)
        //previousTextView.setTextColor(ContextCompat.getColor(this,R.color.black))

        val currentLayout = findViewById<View>(currentId)
        currentLayout.setBackgroundResource(R.drawable.rectangle_clicked)

        currentClickedLayoutId = currentId
    }
}