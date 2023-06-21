package com.example.flexvideotm

import Exercises
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
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

public lateinit var seriesWeight : LineGraphSeries<DataPoint>

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

        // Graph für Gewicht
        seriesWeight = LineGraphSeries<DataPoint>(
            arrayOf(
                DataPoint(0.0, 75.0),
                DataPoint(1.0, 75.3),
                DataPoint(2.0, 74.8),
                DataPoint(3.0, 74.3),
                DataPoint(4.0, 74.9),
                DataPoint(5.0, 74.0),
                DataPoint(6.0, 74.2),
                DataPoint(7.0, 73.9),
                DataPoint(10.0, 73.5)
            )
        )

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

    public fun addDataPointToSeries(series: LineGraphSeries<DataPoint>, x: Double, y: Double) {
        series.appendData(DataPoint(x, y), true, 100)
    }

    public fun replaceFragment(fragment : Fragment){
        val  fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }


}