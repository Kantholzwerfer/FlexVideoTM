package com.example.flexvideotm

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.fragment.app.Fragment
import com.example.flexvideotm.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

public class MainActivity : AppCompatActivity() {


///    private lateinit var auth: FirebaseAuth
//    private lateinit var button: Button
//    private lateinit var textView: TextView
 //   private lateinit var user: FirebaseUser
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

  //      auth = FirebaseAuth.getInstance();
   //     button = findViewById(R.id.logout)
   //     textView = findViewById(R.id.user_details)
   //     user = auth.currentUser!!
    //    if(user == null){
    //        Intent intent = new Intent(applicationContext, Login.class);
     //               startActivity(Intent)
      //      finish()
     //   }

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(Home())
                R.id.profile -> replaceFragment(Profile())
                R.id.calendar -> replaceFragment(Calendar())
                R.id.exercises -> replaceFragment(Exercises())
                R.id.settings -> replaceFragment(Settings())

                else ->{


                }

            }
            true
        }
//        setContent{
//            var selectedImageUri by remember {
//                mutableStateOf<Uri?>(null)
//            }
//            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.PickVisualMedia(),
//                onResult = { uri -> selectedImageUri = uri }
//            )
//        }
    }

    public fun replaceFragment(fragment : Fragment){
        val  fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }


}