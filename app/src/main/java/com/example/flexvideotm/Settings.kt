package com.example.flexvideotm

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.flexvideotm.databinding.FragmentSettingsBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class Settings : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
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
        auth = Firebase.auth
        val ausloggbtn : Button = view.findViewById<Button>(R.id.ausloggen)
        val intent = Intent(this@Settings.requireContext(), Login::class.java)
        ausloggbtn.setOnClickListener{
            startActivity(intent)
        }


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

        // Set initial button text based on current state
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
        val privacyPolicyText = readPrivacyPolicyText()

        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Datenschutzerklärung")
            .setMessage(privacyPolicyText)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun readPrivacyPolicyText(): String {
        val inputStream: InputStream? = resources.openRawResource(R.raw.privacy_policy)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?

        try {
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
                stringBuilder.append('\n')
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return stringBuilder.toString()
    }

    @SuppressLint("ResourceType")
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
            val credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234")


            auth!!.createUserWithEmailAndPassword(newUsername!!, newPassword!!)
                .addOnCompleteListener(requireActivity()) { task ->  // <<< CHANGE WAS MADE HERE !
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        println("createUserWithEmail:success")
                        val user = auth?.currentUser
                    } else {
                        println("Authentication failed.")
                    }
                }

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
