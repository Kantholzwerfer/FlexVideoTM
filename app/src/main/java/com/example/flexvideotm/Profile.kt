package com.example.flexvideotm

import com.example.flexvideotm.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth

//import com.github.drjacky.imagepicker.ImagePicker


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
private lateinit var scrollMaster: ScrollView
//private lateinit var homeFragment: Home
private lateinit var imageView: ImageView
private lateinit var buttonChangeProfilePic: Button
private lateinit var nameView: TextView
private lateinit var nameEdit: EditText
private lateinit var emailView: TextView
private var nummer: Double = 0.0


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

        imageView = view.findViewById(R.id.imageView)
        val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val savedImageUriString = sharedPreferences.getString("image_uri", null)
        val savedImageUri = savedImageUriString?.let { Uri.parse(it) }
        if (savedImageUri != null)
        {
            imageView.setImageURI(savedImageUri)
        }

        emailView = view.findViewById(R.id.textView2)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            emailView.text = user.email.toString()
        }


        buttonChangeProfilePic = view.findViewById(R.id.button4)
        buttonChangeProfilePic.setOnClickListener {

            ImagePicker.with(this)
                .crop()
                //.cropOval()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

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
        scrollMaster = view.findViewById(R.id.scrollMaster)
        nameView = view.findViewById(R.id.textView)
        nameEdit = view.findViewById(R.id.editName)

        buttonSaveProfile = view.findViewById(R.id.buttonSaveProfile)
        buttonChangeProfile = view.findViewById(R.id.buttonChangeProfile)

        buttonChangeProfile.setOnClickListener {
            buttonChangeProfile.visibility = View.INVISIBLE
            buttonChangeProfile.isClickable = false
            buttonSaveProfile.visibility = View.VISIBLE
            buttonSaveProfile.isClickable = true

            geschlechtView.visibility = View.INVISIBLE
            alterView.visibility = View.INVISIBLE
            koerpergroesseView.visibility = View.INVISIBLE
            gewichtView.visibility = View.INVISIBLE
            wunschgewichtView.visibility = View.INVISIBLE
            fettanteilView.visibility = View.INVISIBLE
            wunschfettanteilView.visibility = View.INVISIBLE
            nameView.visibility = View.INVISIBLE

            geschlechtEdit.visibility = View.VISIBLE
            alterEdit.visibility = View.VISIBLE
            koerpergroesseEdit.visibility = View.VISIBLE
            gewichtEdit.visibility = View.VISIBLE
            wunschgewichtEdit.visibility = View.VISIBLE
            fettanteilEdit.visibility = View.VISIBLE
            wunschfettanteilEdit.visibility = View.VISIBLE
            nameEdit.visibility = View.VISIBLE

            geschlechtEdit.isEnabled = true
            alterEdit.isEnabled = true
            koerpergroesseEdit.isEnabled = true
            gewichtEdit.isEnabled = true
            wunschgewichtEdit.isEnabled = true
            fettanteilEdit.isEnabled = true
            wunschfettanteilEdit.isEnabled = true
            nameEdit.isEnabled = true

            geschlechtEdit.clearFocus()
            alterEdit.clearFocus()
            koerpergroesseEdit.clearFocus()
            gewichtEdit.clearFocus()
            wunschgewichtEdit.clearFocus()
            fettanteilEdit.clearFocus()
            wunschfettanteilEdit.clearFocus()
            nameEdit.clearFocus()
        }

        buttonSaveProfile.setOnClickListener {
            buttonChangeProfile.visibility = View.VISIBLE
            buttonChangeProfile.isClickable = true
            buttonSaveProfile.visibility = View.INVISIBLE
            buttonSaveProfile.isClickable = false

            geschlechtView.visibility = View.VISIBLE
            alterView.visibility = View.VISIBLE
            koerpergroesseView.visibility = View.VISIBLE
            gewichtView.visibility = View.VISIBLE
            wunschgewichtView.visibility = View.VISIBLE
            fettanteilView.visibility = View.VISIBLE
            wunschfettanteilView.visibility = View.VISIBLE
            nameView.visibility = View.VISIBLE

            geschlechtEdit.visibility = View.INVISIBLE
            alterEdit.visibility = View.INVISIBLE
            koerpergroesseEdit.visibility = View.INVISIBLE
            gewichtEdit.visibility = View.INVISIBLE
            wunschgewichtEdit.visibility = View.INVISIBLE
            fettanteilEdit.visibility = View.INVISIBLE
            wunschfettanteilEdit.visibility = View.INVISIBLE
            nameEdit.visibility = View.INVISIBLE

            geschlechtEdit.isEnabled = false
            alterEdit.isEnabled = false
            koerpergroesseEdit.isEnabled = false
            gewichtEdit.isEnabled = false
            wunschgewichtEdit.isEnabled = false
            fettanteilEdit.isEnabled = false
            wunschfettanteilEdit.isEnabled = false
            nameEdit.isEnabled = false

            geschlechtEdit.clearFocus()
            alterEdit.clearFocus()
            koerpergroesseEdit.clearFocus()
            gewichtEdit.clearFocus()
            wunschgewichtEdit.clearFocus()
            fettanteilEdit.clearFocus()
            wunschfettanteilEdit.clearFocus()
            nameEdit.clearFocus()

            saveData()
            //loadData()
            //scrollMaster.scrollTo(0,0)
        }

        loadData()
        scrollMaster.scrollTo(0,0)
    }

    private fun dataEntrys()
    {
        val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val savedGeschlecht : String? = sharedPreferences.getString("GESCHLECHT_KEY", "0")
        val savedAlter : String? = sharedPreferences.getString("ALTER_KEY", "0")
        val savedGroesse : String? = sharedPreferences.getString("GROESSE_KEY", "0")
        val savedGewicht : String? = sharedPreferences.getString("GEWICHT_KEY", "0")
        val savedWunschgewicht : String? = sharedPreferences.getString("WUNSCHGEWICHT_KEY", "0")
        val savedFettanteil : String? = sharedPreferences.getString("FETTANTEIL_KEY", "0")
        val savedWunschfettanteil : String? = sharedPreferences.getString("WUNSCHFETTANTEIL_KEY", "0")
        val forlaufendeNummer : String? = sharedPreferences.getString("FORTLAUFENDENUMMER_KEY", "0")

        if (forlaufendeNummer != null) {
            nummer = forlaufendeNummer.toDouble()
        }

        val groesse = ((savedGroesse?.toDouble() ?: 1.0) / 100) * ((savedGroesse?.toDouble() ?: 1.0) / 100)
        val BMI = (savedGewicht?.toDouble() ?: 1.0) / (groesse)

        val mainActivity = requireActivity() as MainActivity
        if (savedGewicht != null) {
            mainActivity.addDataPointToSeries(seriesWeight, nummer, savedGewicht.toDouble())
        }
        if (savedWunschgewicht != null) {
            mainActivity.addDataPointToSeries(seriesWeightGoal, nummer, savedWunschgewicht.toDouble())
        }
        if (savedFettanteil != null) {
            mainActivity.addDataPointToSeries(seriesFat, nummer, savedFettanteil.toDouble())
        }
        if (savedWunschfettanteil != null) {
            mainActivity.addDataPointToSeries(seriesFatGoal, nummer, savedWunschfettanteil.toDouble())
        }
        mainActivity.addDataPointToSeries(seriesBMI, nummer, BMI)

        nummer += 1.0

        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString("FORTLAUFENDENUMMER_KEY", nummer.toString())

        }.apply()
    }

    private fun saveData() {
        val insertedGeschlecht : String = geschlechtEdit.text.toString()
        val insertedAlter : String = alterEdit.text.toString()
        val insertedGroesse : String = koerpergroesseEdit.text.toString()
        val insertedGewicht : String = gewichtEdit.text.toString()
        val insertedWunschgewicht : String = wunschgewichtEdit.text.toString()
        val insertedFettanteil : String = fettanteilEdit.text.toString()
        val insertedWunschfettanteil : String = wunschfettanteilEdit.text.toString()
        val insertedName : String = nameEdit.text.toString()

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
            putString("NAME_KEY", insertedName)
        }.apply()

        dataEntrys()

        val mainActivity = requireActivity() as MainActivity
        val profileFragment = Profile() // Replace with the desired fragment
        mainActivity.replaceFragment(profileFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
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
        val savedName : String? = sharedPreferences.getString("NAME_KEY", "--")

        geschlechtView.text = savedGeschlecht
        alterView.text = savedAlter
        koerpergroesseView.text = savedGroesse
        gewichtView.text = savedGewicht
        wunschgewichtView.text = savedWunschgewicht
        fettanteilView.text = savedFettanteil
        wunschfettanteilView.text = savedWunschfettanteil
        nameView.text = savedName

        geschlechtEdit.setText(savedGeschlecht)
        alterEdit.setText(savedAlter)
        koerpergroesseEdit.setText(savedGroesse)
        gewichtEdit.setText(savedGewicht)
        wunschgewichtEdit.setText(savedWunschgewicht)
        fettanteilEdit.setText(savedFettanteil)
        wunschfettanteilEdit.setText(savedWunschfettanteil)
        nameEdit.setText(savedName)

        geschlechtEdit.clearFocus()
        alterEdit.clearFocus()
        koerpergroesseEdit.clearFocus()
        gewichtEdit.clearFocus()
        wunschgewichtEdit.clearFocus()
        fettanteilEdit.clearFocus()
        wunschfettanteilEdit.clearFocus()
        nameEdit.clearFocus()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageView.setImageURI(data?.data)

        val imageUri = data?.data

        val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("image_uri", imageUri.toString())
        editor.apply()
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