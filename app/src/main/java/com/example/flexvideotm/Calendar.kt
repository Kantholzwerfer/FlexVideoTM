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
import java.util.Calendar
import androidx.fragment.app.FragmentManager

class Calendar : Fragment() {
    private val sharedPreferencesKey = "calendar_events"
    private lateinit var createEntryButton: Button
    private lateinit var sharedPreferences: SharedPreferences

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
            reloadFragment()
        }

        return view
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
        return "$date : $entry";
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

    private fun getDateFromCalendarView(calendarView: CalendarView): String {
        val selectedDateInMillis = calendarView.date
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDateInMillis

        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month =
            calendar.get(Calendar.MONTH) + 1 // Note: Months in the Calendar API are zero-based (0-11)
        val year = calendar.get(Calendar.YEAR)

        return String.format("%02d%02d%04d", dayOfMonth, month, year)
    }

    private fun reloadFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.detach(this)
        transaction.attach(this)
        transaction.commit()
    }

    companion object {
        private const val TAG = "Calendar"

        @JvmStatic
        fun newInstance() =
            Calendar()
    }
}
