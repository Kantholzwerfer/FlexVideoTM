package com.example.flexvideotm

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var geschlechtView: TextView
private lateinit var alterView: TextView
private lateinit var koerpergroesseView: TextView
private lateinit var gewichtView: TextView
private lateinit var wunschgewichtView: TextView
private lateinit var fettanteilView: TextView
private lateinit var wunschfettanteilView: TextView
private lateinit var geschlechtEdit: EditText
private lateinit var alterEdit: EditText
private lateinit var koerpergroesseEdit: EditText
private lateinit var gewichtEdit: EditText
private lateinit var wunschgewichtEdit: EditText
private lateinit var fettanteilEdit: EditText
private lateinit var wunschfettanteilEdit: EditText
private lateinit var buttonChangeProfile: Button
private lateinit var buttonSaveProfile: Button


/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geschlechtView = view.findViewById(R.id.textViewGeschlechtWert)
        alterView = view.findViewById(R.id.textViewAlterWert)
        koerpergroesseView = view.findViewById(R.id.textViewGroesseWert)
        gewichtView = view.findViewById(R.id.textViewGewichtWert)
        wunschgewichtView = view.findViewById(R.id.textViewWunschgewichtWert)
        fettanteilView = view.findViewById(R.id.textViewKoerperfettanteilWert)
        wunschfettanteilView = view.findViewById(R.id.textViewWunschKoerperfettanteilWert)
        geschlechtEdit = view.findViewById(R.id.editGeschlecht)
        alterEdit = view.findViewById(R.id.editAlter)
        koerpergroesseEdit = view.findViewById(R.id.editGroesse)
        gewichtEdit = view.findViewById(R.id.editGewicht)
        wunschgewichtEdit = view.findViewById(R.id.editWunschgewicht)
        fettanteilEdit = view.findViewById(R.id.editKoerperfettanteil)
        wunschfettanteilEdit = view.findViewById(R.id.editWunschkoerperfettanteil)
        buttonSaveProfile = view.findViewById(R.id.buttonSaveProfile)
        buttonChangeProfile = view.findViewById(R.id.buttonChangeProfile)

        buttonChangeProfile.setOnClickListener {
            buttonChangeProfile.visibility = View.INVISIBLE
            buttonChangeProfile.isClickable = false
            buttonSaveProfile.visibility = View.VISIBLE
            buttonSaveProfile.isClickable = true

            geschlechtView.isEnabled = true
            alterView.isEnabled = true
            koerpergroesseView.isEnabled = true
            gewichtView.isEnabled = true
            wunschgewichtView.isEnabled = true
            fettanteilView.isEnabled = true
            wunschfettanteilView.isEnabled = true
        }

        buttonSaveProfile.setOnClickListener {
            buttonChangeProfile.visibility = View.VISIBLE
            buttonChangeProfile.isClickable = true
            buttonSaveProfile.visibility = View.INVISIBLE
            buttonSaveProfile.isClickable = false

            geschlechtView.isEnabled = false
            alterView.isEnabled = false
            koerpergroesseView.isEnabled = false
            gewichtView.isEnabled = false
            wunschgewichtView.isEnabled = false
            fettanteilView.isEnabled = false
            wunschfettanteilView.isEnabled = false
            saveData()
            loadData()
        }

        loadData()

    }

    private fun saveData() {
        val insertedGeschlecht : String = geschlechtEdit.text.toString()
        val insertedAlter : String = alterEdit.text.toString()
        val insertedGroesse : String = koerpergroesseEdit.text.toString()
        val insertedGewicht : String = gewichtEdit.text.toString()
        val insertedWunschgewicht : String = wunschgewichtEdit.text.toString()
        val insertedFettanteil : String = fettanteilEdit.text.toString()
        val insertedWunschfettanteil : String = wunschfettanteilEdit.text.toString()
        //testView.text = insertedText

        val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString("GESCHLECHT_KEY", insertedGeschlecht)
            putString("ALTER_KEY", insertedAlter)
            putString("GROESSE_KEY", insertedGroesse)
            putString("GEWICHT_KEY", insertedGewicht)
            putString("WUNSCHGEWICHT_KEY", insertedWunschgewicht)
            putString("FETTANTEIL_KEY", insertedFettanteil)
            putString("WUNSCHFETTANTEIL_KEY", insertedWunschfettanteil)
        }.apply()
    }

    private fun loadData() {
        val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val savedGeschlecht : String? = sharedPreferences.getString("GESCHLECHT_KEY", "--")
        val savedAlter : String? = sharedPreferences.getString("ALTER_KEY", "--")
        val savedGroesse : String? = sharedPreferences.getString("GROESSE_KEY", "--")
        val savedGewicht : String? = sharedPreferences.getString("GEWICHT_KEY", "--")
        val savedWunschgewicht : String? = sharedPreferences.getString("WUNSCHGEWICHT_KEY", "--")
        val savedFettanteil : String? = sharedPreferences.getString("FETTANTEIL_KEY", "--")
        val savedWunschfettanteil : String? = sharedPreferences.getString("WUNSCHFETTANTEIL_KEY", "--")

        geschlechtView.text = savedGeschlecht
        alterView.text = savedAlter
        koerpergroesseView.text = savedGroesse
        gewichtView.text = savedGewicht
        wunschgewichtView.text = savedWunschgewicht
        fettanteilView.text = savedFettanteil
        wunschfettanteilView.text = savedWunschfettanteil

        geschlechtEdit.setText(savedGeschlecht)
        alterEdit.setText(savedAlter)
        koerpergroesseEdit.setText(savedGroesse)
        gewichtEdit.setText(savedGewicht)
        wunschgewichtEdit.setText(savedWunschgewicht)
        fettanteilEdit.setText(savedFettanteil)
        wunschfettanteilEdit.setText(savedWunschfettanteil)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}