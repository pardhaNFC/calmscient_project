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

package com.calmscient.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.calmscient.Interface.BottomSheetListener
import com.calmscient.R
import com.calmscient.di.remote.JournalEntryDataClass
import com.calmscient.fragments.BottomSheetAddFragment
import com.calmscient.fragments.ReminderBottomSheet
import com.calmscient.utils.AnimationUtils
import com.google.android.material.bottomsheet.BottomSheetDialog

class JournalEntryAdapter(private val cardDataList: MutableList<JournalEntryDataClass>, private val bottomSheetListener: BottomSheetListener,) :
    RecyclerView.Adapter<JournalEntryAdapter.CardViewHolder>() {

    private var expandedCardPosition: Int = -1
    private lateinit var imageViewThreeDotsIcon: ImageView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.journal_entry_item_card, parent, false)

        imageViewThreeDotsIcon = view.findViewById(R.id.imageViewThreeDots)
        return CardViewHolder(view)
    }

   override fun onBindViewHolder(
       holder: CardViewHolder,
       @SuppressLint("RecyclerView") position: Int
   ) {
       val cardData = cardDataList[position]
       holder.date.text = cardData.date
       holder.description.text = cardData.description
   }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tv_date)
        val description: TextView = itemView.findViewById(R.id.tv_Description)
        val threeDotsIcon: ImageView = itemView.findViewById(R.id.imageViewThreeDots)
        private val expandLayout: RelativeLayout = itemView.findViewById(R.id.expandLayout)
        init {
            threeDotsIcon.setOnClickListener { view ->
                showPopupMenu(view, adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("JournalEntryAdapter", "Item count: ${cardDataList.size}")
        return cardDataList.size
    }

    private fun showPopupMenu(view: View, position: Int) {
        val context = view.context
        val popupMenu = PopupMenu(context, view)
        val menuInflater = popupMenu.menuInflater
        popupMenu.inflate(R.menu.journal_entry_edit_delete_popup_menu)
        //menuInflater.inflate(R.menu.journal_entry_edit_delete_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_delete -> {
                    // Create a custom confirmation dialog
                    val dialogView = LayoutInflater.from(context)
                        .inflate(R.layout.journal_entry_delete_dialog, null)
                    val dialogBuilder =
                        android.app.AlertDialog.Builder(context, R.style.CustomDialog)
                            .setView(dialogView)
                    val dialog = dialogBuilder.create()
                    dialog.show()
                    // Initialize views in the custom dialog layout
                    val buttonYes = dialog.findViewById<AppCompatButton>(R.id.button_yes)
                    val buttonNo = dialog.findViewById<AppCompatButton>(R.id.button_no)
                    buttonYes.setOnClickListener {
                        // Handle the "Yes" button click (perform deletion)
                        // You can perform delete-related actions here
                        // Once deletion is complete, dismiss the dialog
                        removeEntry(position)
                        Toast.makeText(context, "Deleted Successfully...", Toast.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()
                    }
                    buttonNo.setOnClickListener {
                        // Handle the "No" button click (cancel deletion)
                        // Simply dismiss the dialog
                        Toast.makeText(context, "You clicked No", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    // Show the custom dialog
                    dialog.show()
                    true // Return true to indicate that the menu item click is handled
                }

                R.id.menu_edit -> {
                    bottomSheetListener.onShowEditBottomSheet(view, position, cardDataList[position].description)
                    //showBottomSheet(view, position, cardDataList[position].description)
                    true
                }
                R.id.menu_add -> {
                    bottomSheetListener.onShowBottomSheet()
                    //addBottomSheet(view)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
    }

    fun removeEntry(position: Int) {
        cardDataList.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun addBottomSheet(view: View) {
        val context = view.context
        val bottomSheetView =
            LayoutInflater.from(context).inflate(R.layout.bottom_sheet_journal_add, null)
        val dialog = BottomSheetDialog(context)
        // Initialize views in the bottom sheet layout
        //   val textViewEditJournal = bottomSheetView.findViewById<TextView>(R.id.et_daily_journel)
        val closeIcon = bottomSheetView.findViewById<ImageView>(R.id.closeButton)
        val editTextJournalEntry = bottomSheetView.findViewById<EditText>(R.id.et_daily_journel)
        val addButton = bottomSheetView.findViewById<AppCompatButton>(R.id.addButton)
        closeIcon.setOnClickListener {
            dialog.dismiss()
        }
        // Set a click listener for the "Update" button
        addButton.setOnClickListener {
            // Dismiss the bottom sheet
            dialog.dismiss()
        }
        dialog.setContentView(bottomSheetView)
        dialog.show()
    }
    private fun showBottomSheet(view: View, position: Int, description: String) {
        val context = view.context
        val bottomSheetView =
            LayoutInflater.from(context).inflate(R.layout.journal_entry_edit_bottomsheet, null)
        val dialog = BottomSheetDialog(context)

        // Initialize views in the bottom sheet layout
        //   val textViewEditJournal = bottomSheetView.findViewById<TextView>(R.id.et_daily_journel)
        val closeIcon = bottomSheetView.findViewById<ImageView>(R.id.closeButton)
        val editTextJournalEntry = bottomSheetView.findViewById<EditText>(R.id.et_daily_journel)
        val updateButton = bottomSheetView.findViewById<AppCompatButton>(R.id.updateButton)

        editTextJournalEntry.setText(description)
        // Set a click listener for the close icon to dismiss the bottom sheet
        closeIcon.setOnClickListener {
            dialog.dismiss()
        }
        // Set a click listener for the "Update" button
        updateButton.setOnClickListener {
            // Handle the update action here
            val updatedEntry = editTextJournalEntry.text.toString()
            // Update the data in your cardDataList or perform any necessary updates
            if (position >= 0 && position < cardDataList.size) {
                cardDataList[position].description = updatedEntry
                notifyItemChanged(position) // Notify the adapter of the data change for this card only
            }
            // Dismiss the bottom sheet
            dialog.dismiss()
            showCustomDialog(context)
        }
        dialog.setContentView(bottomSheetView)
        dialog.show()
    }

    private fun showCustomDialog(context: Context) {
        // Create and configure the custom dialog
        val dialogView = LayoutInflater.from(context).inflate(R.layout.journal_update_dialog, null)
        val dialog = android.app.AlertDialog.Builder(context, R.style.CustomDialog)
            .setView(dialogView)
            .create()

        // Initialize views in the custom dialog layout
        val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
        val iconImageView = dialogView.findViewById<ImageView>(R.id.dialog_icon)
        val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_message)

        // Customize the dialog's title, icon, and message as needed

        // Show the custom dialog
        dialog.show()

        val handler = Handler()
        handler.postDelayed({
            dialog.dismiss()
        }, 3000)
    }
}