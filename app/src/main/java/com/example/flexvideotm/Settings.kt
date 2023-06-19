package com.example.flexvideotm

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class Settings : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val privacySettingsButton = view.findViewById<Button>(R.id.privacySettingsButton)
        privacySettingsButton.setOnClickListener {
            showPrivacyPolicyDialog()
        }

        val accountSettingsButton = view.findViewById<Button>(R.id.accountSettingsButton)
        accountSettingsButton.setOnClickListener {
            showAccountSettingsDialog()
        }

        val darkModeButton = view.findViewById<Button>(R.id.darkModeButton)
        darkModeButton.setOnClickListener {
            val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            val isDarkModeEnabled = currentNightMode == Configuration.UI_MODE_NIGHT_YES

            if (isDarkModeEnabled) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkModeButton.text = "Dark Mode aktivieren"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkModeButton.text = "Light Mode aktivieren"
            }
        }
        
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val initialButtonText = if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            "Light Mode aktivieren"
        } else {
            "Dark Mode aktivieren"
        }
        darkModeButton.text = initialButtonText

        return view
    }

    private fun showPrivacyPolicyDialog() {
        val privacyPolicyText = "Hier kommt der vorgefertigte Text der Datenschutzerklärung."

        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Datenschutzerklärung")
            .setMessage(privacyPolicyText)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun showAccountSettingsDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Accounteinstellungen")

        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.xml.dialog_account_settings, null)
        dialogBuilder.setView(dialogView)

        val usernameEditText = dialogView.findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = dialogView.findViewById<EditText>(R.id.passwordEditText)

        dialogBuilder.setPositiveButton("Bestätigen") { dialog, _ ->
            val newUsername = usernameEditText.text.toString()
            val newPassword = passwordEditText.text.toString()

            // Code, um die neuen Benutzerdaten zu verarbeiten

            Toast.makeText(requireContext(), "Benutzername: $newUsername, Passwort: $newPassword", Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }

        dialogBuilder.setNegativeButton("Abbrechen") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    companion object {

        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
