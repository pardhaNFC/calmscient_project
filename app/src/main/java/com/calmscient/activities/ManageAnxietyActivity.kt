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
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calmscient.R
import com.calmscient.adapters.AnxietyIntroductionAdapter
import com.calmscient.adapters.CardItemDiffCallback
import com.calmscient.di.remote.CardItemDataClass
import com.calmscient.di.remote.ItemType

import com.calmscient.databinding.ActivityManageAnxietyBinding
import com.calmscient.fragments.DiscoveryFragment
import com.calmscient.utils.common.SavePreferences

class ManageAnxietyActivity : AppCompat() {
    lateinit var savePrefData: SavePreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityManageAnxietyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        savePrefData = SavePreferences(this)
        if(savePrefData.getAslLanguageState() == true){
            // Data for Introduction
            val introductionItems = cardAslItemsIntroduction()
            val introductionRecyclerView: RecyclerView = binding.recyclerViewIntroduction
            val introductionAdapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
            setupRecyclerView(introductionRecyclerView, introductionItems, introductionAdapter)

            // Data for lesson 1
            val lesson1Items = cardItemsAslLesson1()
            val lesson1RecyclerView: RecyclerView = binding.recyclerViewLesson1
            val lesson1Adapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
            setupRecyclerView(lesson1RecyclerView, lesson1Items, lesson1Adapter)
        }else {
            // Data for Introduction
            val introductionItems = cardItemsIntroduction()
            val introductionRecyclerView: RecyclerView = binding.recyclerViewIntroduction
            val introductionAdapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
            setupRecyclerView(introductionRecyclerView, introductionItems, introductionAdapter)

            // Data for lesson 1
            val lesson1Items = cardItemsLesson1()
            val lesson1RecyclerView: RecyclerView = binding.recyclerViewLesson1
            val lesson1Adapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
            setupRecyclerView(lesson1RecyclerView, lesson1Items, lesson1Adapter)
        }
       /* // Data for lesson 1
        val lesson1Items = cardItemsLesson1()
        val lesson1RecyclerView: RecyclerView = binding.recyclerViewLesson1
        val lesson1Adapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
        setupRecyclerView(lesson1RecyclerView, lesson1Items, lesson1Adapter)
*/
        // Data for lesson 2
        val lesson2Items = cardItemsLesson2()
        val lesson2RecyclerView: RecyclerView = binding.recyclerViewLesson2
        val lesson2Adapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
        setupRecyclerView(lesson2RecyclerView, lesson2Items, lesson2Adapter)

        // Data for lesson 3
        val lesson3Items = cardItemsLesson3()
        val lesson3RecyclerView: RecyclerView = binding.recyclerViewLesson3
        val lesson3Adapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
        setupRecyclerView(lesson3RecyclerView, lesson3Items, lesson3Adapter)

        // Data for lesson 4
        val lesson4Items = cardItemsLesson4()
        val lesson4RecyclerView: RecyclerView = binding.recyclerViewLesson4
        val lesson4Adapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
        setupRecyclerView(lesson4RecyclerView, lesson4Items, lesson4Adapter)

        // Data for lesson 5
        val lesson5Items = cardItemsLesson5()
        val lesson5RecyclerView: RecyclerView = binding.recyclerViewLesson5
        val lesson5Adapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
        setupRecyclerView(lesson5RecyclerView, lesson5Items, lesson5Adapter)

        // Data for lesson 6
        val lesson6Items = cardItemsLesson6()
        val lesson6RecyclerView: RecyclerView = binding.recyclerViewLesson6
        val lesson6Adapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
        setupRecyclerView(lesson6RecyclerView, lesson6Items, lesson6Adapter)

        // Data for Additional Resource
        val additionalResourceItems = cardItemsAdditionalResource()
        val additionalResourceRecyclerView: RecyclerView = binding.recyclerViewAdditionalResource
        val additionalResourceAdapter = AnxietyIntroductionAdapter(CardItemDiffCallback())
        setupRecyclerView(
            additionalResourceRecyclerView, additionalResourceItems, additionalResourceAdapter
        )

        binding.icGlossary.setOnClickListener {
            startActivity(Intent(this, GlossaryActivity::class.java))
        }
        binding.backIcon.setOnClickListener {
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(DiscoveryFragment::class.java.simpleName)
        if (fragment != null && fragment.isVisible) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
    private fun loadFragment(fragment: Fragment) {
        // Toast.makeText(requireContext(), "Back Button is calling", Toast.LENGTH_SHORT).show()
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun cardItemsIntroduction(): List<CardItemDataClass> {
        val card1 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.intro_1),
            description = getString(R.string.page_2_1),
            isCompleted = true,
            heading = null,
            summary = null,
            dialogText = null
        )

        val card2 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null, // Replace with actual audio resource ID
            videoResourceId = null,
            contentIcons = listOf(R.drawable.intro_2),
            description = getString(R.string.title_toolbar_pace),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )
        val card3 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null, // Replace with actual audio resource ID
            videoResourceId = null,
            contentIcons = listOf(R.drawable.intro_3),
            description = getString(R.string.let_make_plan),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        // Add more CardItemDataClass instances as needed for section 1
        return listOf(card1, card2, card3)
    }
    private fun cardAslItemsIntroduction(): List<CardItemDataClass> {
        val card1 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.VIDEO),
            audioResourceId = null,
            videoResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/What+is+Anxiety_+Blue+Background(ASL).m4v",
            contentIcons = listOf(R.drawable.ic_intro_asl),
            description = getString(R.string.page_2_1),
            isCompleted = true,
            heading = getString(R.string.page_2_1),
            summary = null,
            dialogText = null
        )

        val card2 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null, // Replace with actual audio resource ID
            videoResourceId = null,
            contentIcons = listOf(R.drawable.intro_2),
            description = getString(R.string.title_toolbar_pace),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )
        val card3 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null, // Replace with actual audio resource ID
            videoResourceId = null,
            contentIcons = listOf(R.drawable.intro_3),
            description = getString(R.string.let_make_plan),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        // Add more CardItemDataClass instances as needed for section 1
        return listOf(card1, card2, card3)
    }
    private fun cardItemsAslLesson1(): List<CardItemDataClass> {
        val card1 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.VIDEO),
            audioResourceId = null,
            videoResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/The+Neuropsychology+of+Anxiety(ASL).m4v",
            contentIcons = listOf(R.drawable.lesson_1_1),
            description = getString(R.string.neuropsychology),
            isCompleted = false,
            heading = getString(R.string.the_neuropsychology),
            summary = getString(R.string.lesson1_video_summary),
            dialogText = getString(R.string.lesson1_video1_description),
        )

        val card2 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.AUDIO),
            audioResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/Lesson+1-2+Meet+Nora%2C+Austin+and+Melanie.wav",
            videoResourceId = null,
            contentIcons = listOf(R.drawable.audio_lesson_1_2),
            description = getString(R.string.meet_nora_austin),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )
        // Add more CardItemDataClass instances as needed for Lesson1
        return listOf(card1, card2)
    }
    private fun cardItemsLesson1(): List<CardItemDataClass> {
        val card1 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.VIDEO),
            audioResourceId = null,
            videoResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/L1-1-Neuropsychology+of+Anxiety+(1).mp4",
            contentIcons = listOf(R.drawable.lesson_1_1),
            description = getString(R.string.neuropsychology),
            isCompleted = false,
            heading = getString(R.string.the_neuropsychology),
            summary = getString(R.string.lesson1_video_summary),
            dialogText = getString(R.string.lesson1_video1_description),
        )

        val card2 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.AUDIO),
            audioResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/Lesson+1-2+Meet+Nora%2C+Austin+and+Melanie.wav",
            videoResourceId = null,
            contentIcons = listOf(R.drawable.audio_lesson_1_2),
            description = getString(R.string.meet_nora_austin),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )
        // Add more CardItemDataClass instances as needed for Lesson1
        return listOf(card1, card2)
    }

    private fun cardItemsLesson2(): List<CardItemDataClass> {
        val card1 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.lesson_2_1),
            description = getString(R.string.title_toolbar_recognize),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        val card2 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.QUIZ),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.quiz_lesson_2_2),
            description = getString(R.string.quiz_title),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        // Add more CardItemDataClass instances as needed for lesson 2
        return listOf(card1, card2)
    }

    private fun cardItemsLesson3(): List<CardItemDataClass> {
        val card1 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.lesson_3_1),
            description = getString(R.string.anxiety_hide),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        val card2 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.QUIZ),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.quiz_lesson_2_2),
            description = getString(R.string.quiz_title),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        val card3 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.AUDIO),
            audioResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/Lesson+3+Moral+deficiency+or+anxiety+with+music+English.wav",
            videoResourceId = null,
            contentIcons = listOf(R.drawable.audio_lesson_1_2),
            description = getString(R.string.moral_deficiency),
            isCompleted = false,
            heading = getString(R.string.moral_deficiency),
            summary = null,
            dialogText = null
        )

        val card4 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.QUIZ),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.quiz_lesson_2_2),
            description = getString(R.string.quiz_title),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        // Add more CardItemDataClass instances as needed for lesson3
        return listOf(card1, card2, card3, card4)
    }

    private fun cardItemsLesson4(): List<CardItemDataClass> {
        val card1 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.VIDEO),
            audioResourceId = null,
            videoResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/Lesson+4-1+Implementing+body+calming+skills.mp4",
            contentIcons = listOf(R.drawable.lesson_4_1),
            description = getString(R.string.make_plan_card6_text2),
            isCompleted = false,
            heading = getString(R.string.make_plan_card6_text2),
            summary = getString(R.string.lesson1_video_summary),
            dialogText = getString(R.string.lesson4_video1_description)
        )

        val card2 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.lesson_4_2),
            description = getString(R.string.calming_body),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        // Add more CardItemDataClass instances as needed for lesson4
        return listOf(card1, card2)
    }

    private fun cardItemsLesson5(): List<CardItemDataClass> {
        val card1 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.AUDIO),
            audioResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/Lesson+5-1+Anxiety+and+worry+with+music+English.wav",
            videoResourceId = null,
            contentIcons = listOf(R.drawable.audio_lesson_1_2),
            description = getString(R.string.anxiety_worry),
            isCompleted = false,
            heading = getString(R.string.anxiety_worry),
            summary = null,
            dialogText = null
        )

        val card2 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.lesson_5_2),
            description = getString(R.string.postpone_worry),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        // Add more CardItemDataClass instances as needed for lesson5
        return listOf(card1, card2)
    }

    private fun cardItemsLesson6(): List<CardItemDataClass> {
        val card1 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.AUDIO),
            audioResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/Lesson+6-1+Calming+your+anxious+mind+with+music+English.wav",
            videoResourceId = null,
            contentIcons = listOf(R.drawable.audio_lesson_1_2),
            description = getString(R.string.anxious_mind),
            isCompleted = false,
            heading = getString(R.string.anxious_mind),
            summary = null,
            dialogText = null
        )

        val card2 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.lesson_6_2),
            description = getString(R.string.biased_think),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        val card3 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.AUDIO),
            audioResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/Lesson+6-3+North+wind+and+sun+with+music+English.wav",
            videoResourceId = null,
            contentIcons = listOf(R.drawable.lesson_6_3),
            description = getString(R.string.making_connection),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        val card4 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.lesson6_layer2),
            description = getString(R.string.restructure_biased),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        // Add more CardItemDataClass instances as needed for lesson6
        return listOf(card1, card2, card3, card4)
    }

    private fun cardItemsAdditionalResource(): List<CardItemDataClass> {
        val card1 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.VIDEO),
            audioResourceId = null,
            videoResourceId = "https://calmscient-videos.s3.ap-south-1.amazonaws.com/1+Anxiety+and+exercise.mp4",
            contentIcons = listOf(R.drawable.additional_1),
            description = getString(R.string.anxiety_exercise),
            isCompleted = false,
            heading = getString(R.string.anxiety_exercise),
            summary = getString(R.string.additional_anxiety_exercise_summary),
            dialogText = null
        )

        val card2 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.additional_2),
            description = getString(R.string.anxiety_sleep),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        val card3 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.additional_3),
            description = getString(R.string.anxiety_alcohol),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        val card4 = CardItemDataClass(
            availableContentTypes = listOf(ItemType.LESSON),
            audioResourceId = null,
            videoResourceId = null,
            contentIcons = listOf(R.drawable.additional_4),
            description = getString(R.string.managing_stress),
            isCompleted = false,
            heading = null,
            summary = null,
            dialogText = null
        )

        // Add more CardItemDataClass instances as needed for Additional Resource
        return listOf(card1, card2, card3, card4)
    }


    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        cardItems: List<CardItemDataClass>,
        adapter: AnxietyIntroductionAdapter
    ) {
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        adapter.submitList(cardItems)
    }

}