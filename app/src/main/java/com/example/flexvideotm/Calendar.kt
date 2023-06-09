package com.example.flexvideotm

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import android.widget.CalendarView

class Calendar : Fragment() {
    private val sharedPreferencesKey = "calendar_events"
    private lateinit var createEntryButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        createEntryButton = view.findViewById(R.id.createEntryButton)
        sharedPreferences = requireContext().getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)

        createEntryButton.setOnClickListener {
            showEntryDialog()
        }

        activity?.runOnUiThread {
            val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
            calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val formattedDate = String.format("%02d%02d%04d", dayOfMonth, month + 1, year)
                saveSelectedDate(formattedDate) }
            val entryTextView = view.findViewById<TextView>(R.id.TextView)
            val date = sharedPreferences.getString("selected_date", "") ?: ""
            val entry = loadEntries(date)
            entryTextView.text = entry
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarView2 = view.findViewById<CalendarView>(R.id.calendarView)
        calendarView2.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val formattedDate = String.format("%02d%02d%04d", dayOfMonth, month + 1, year)
            saveSelectedDate(formattedDate)
            val entryTextView = view.findViewById<TextView>(R.id.TextView)
            val date = sharedPreferences.getString("selected_date", "") ?: ""
            val entry = loadEntries(date)
            entryTextView.text = entry
            val mainActivity = requireActivity() as MainActivity
            val kalendarFragment = Calendar()
            mainActivity.replaceFragment(kalendarFragment)
        }
    }

    @SuppressLint("ResourceType")
    private fun showEntryDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.xml.dialog_add_entry, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Eintrag erstellen")
            .setPositiveButton("Speichern") { dialog, _ ->
                val dateEditText = dialogView.findViewById<EditText>(R.id.dateEditText)
                val eventEditText = dialogView.findViewById<EditText>(R.id.eventEditText)

                val date = dateEditText.text.toString()
                val event = eventEditText.text.toString()

                if (date.isNotEmpty() && event.isNotEmpty()) {
                    saveEntry(date, event)
                    Toast.makeText(requireContext(), "Eintrag gespeichert", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Ungültiges Datum oder Ereignis", Toast.LENGTH_SHORT).show()
                }

                dialog.dismiss()
            }
            .setNegativeButton("Abbrechen") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun isEntryExist(date: String): Boolean {
        val entry = sharedPreferences.getString(date, null)
        return entry != null
    }

    private fun loadEntries(date: String): String {
        val choosed_date = date
        val entry = if (isEntryExist(date)) {
            sharedPreferences.getString(choosed_date, "") ?: ""
        } else {
            "Kein Eintrag vorhanden"
        }
        val date_formatted = insertDot(choosed_date)
        return "$date_formatted : $entry";
    }

    private fun saveEntry(date: String, event: String) {
        val editor = sharedPreferences.edit()
        editor.putString(date, event)
        editor.apply()
    }

    private fun saveSelectedDate(date: String) {
        val editor = sharedPreferences.edit()
        editor.putString("selected_date", date)
        editor.apply()
    }

    private fun insertDot(date: String): String {
        val firstDotIndex = 2
        val secondDotIndex = 5

        if (date.length >= secondDotIndex) {
            val stringBuilder = StringBuilder(date)
            stringBuilder.insert(firstDotIndex, ".")
            stringBuilder.insert(secondDotIndex, ".")

            return stringBuilder.toString()
        } else {
            return date
        }
    }
    
    companion object {
        private const val TAG = "Calendar"

        @JvmStatic
        fun newInstance() =
            Calendar()
    }
}
