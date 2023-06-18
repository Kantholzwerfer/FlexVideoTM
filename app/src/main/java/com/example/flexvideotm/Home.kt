package com.example.flexvideotm
//package com.gtappdevelopers.kotlingfgproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
@SuppressLint("ParcelCreator")
class Home() : Fragment(), Parcelable {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    constructor(parcel: Parcel) : this() {
        param1 = parcel.readString()
        param2 = parcel.readString()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                var arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    class HomeGraphics() : AppCompatActivity(), Parcelable {

        //on below line we are creating
        // variables for our graph view
        lateinit var lineGraphView: GraphView

        constructor(parcel: Parcel) : this() {

        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.fragment_home)

            // on below line we are initializing
            // our variable with their ids.
            lineGraphView = findViewById(R.id.idGraphView)

            // on below line we are adding data to our graph view.
            val seriesWeight: LineGraphSeries<DataPoint> = LineGraphSeries(
                arrayOf(
                    // on below line we are adding
                    // each point on our x and y axis.
                    DataPoint(0.0, 75.0),
                    DataPoint(1.0, 75.3),
                    DataPoint(2.0, 74.8),
                    DataPoint(3.0, 74.3),
                    DataPoint(4.0, 74.9),
                    DataPoint(5.0, 74.0),
                    DataPoint(6.0, 74.2),
                    DataPoint(7.0, 73.9),
                    DataPoint(8.0, 73.5)
                )
            )

            // on below line we are adding data to our graph view.
            val seriesGoal: LineGraphSeries<DataPoint> = LineGraphSeries(
                arrayOf(
                    // on below line we are adding
                    // each point on our x and y axis.
                    DataPoint(0.0, 73.0),
                    DataPoint(1.0, 73.0),
                    DataPoint(2.0, 73.0),
                    DataPoint(3.0, 73.0),
                    DataPoint(4.0, 73.0),
                    DataPoint(5.0, 73.0),
                    DataPoint(6.0, 73.0),
                    DataPoint(7.0, 73.0),
                    DataPoint(8.0, 73.0)
                )
            )

            // on below line adding animation
//            lineGraphView.animate()

            // on below line we are setting scrollable
            // for point graph view
            lineGraphView.viewport.isScrollable = true

            // on below line we are setting scalable.
//            lineGraphView.viewport.isScalable = true

            // on below line we are setting scalable y
            lineGraphView.viewport.setScalableY(true)

            // on below line we are setting scrollable y
            lineGraphView.viewport.setScrollableY(true)

            // on below line we are setting color for series.
            seriesWeight.color = R.color.teal_200
            seriesGoal.color = R.color.black

            // on below line we are adding
            // data series to our graph view.
            lineGraphView.addSeries(seriesWeight)
            lineGraphView.addSeries(seriesGoal)
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {

        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Home> {
            override fun createFromParcel(parcel: Parcel): Home {
                return Home(parcel)
            }

            override fun newArray(size: Int): Array<Home?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(param1)
        parcel.writeString(param2)
    }

    override fun describeContents(): Int {
        return 0
    }

    }

