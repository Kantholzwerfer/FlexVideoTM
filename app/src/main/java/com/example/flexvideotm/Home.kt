package com.example.flexvideotm
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    fun addDataPointToSeries(series: LineGraphSeries<DataPoint>, x: Double, y: Double) {
        series.appendData(DataPoint(x, y), true, 100)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seriesWeight = LineGraphSeries<DataPoint>(
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

        setupGraph(seriesWeight)


        val lineGraphView: GraphView = requireView().findViewById(R.id.idGraphView)


        //val seriesWeight = LineGraphSeries<DataPoint>(arrayOf(DataPoint(0.0, 75.0), DataPoint(1.0, 75.3)))
        val x = 15.0
        val y = 71.8

        addDataPointToSeries(seriesWeight, x, y)
    }


    fun setupGraph(seriesWeight: LineGraphSeries<DataPoint>) {
        val lineGraphView: GraphView = requireView().findViewById(R.id.idGraphView)


        val seriesGoal = LineGraphSeries<DataPoint>(
            arrayOf(
                DataPoint(0.0, 73.0),
                DataPoint(1.0, 73.0),
                DataPoint(2.0, 73.0),
                DataPoint(3.0, 73.0),
                DataPoint(4.0, 73.0),
                DataPoint(5.0, 73.0),
                DataPoint(6.0, 73.0),
                DataPoint(7.0, 73.0),
                DataPoint(11.0, 70.0)
            )
        )

        seriesWeight.color = resources.getColor(R.color.white)
        seriesGoal.color = resources.getColor(R.color.black)

        lineGraphView.addSeries(seriesWeight)
        lineGraphView.addSeries(seriesGoal)

        lineGraphView.viewport.isScrollable = true
        lineGraphView.viewport.isScalable = true
        lineGraphView.viewport.setScalableY(true)
        lineGraphView.viewport.setScrollableY(true)
        lineGraphView.viewport.isXAxisBoundsManual = true


        lineGraphView.viewport.setMinX(0.0)
        lineGraphView.viewport.setMaxX(8.0)
        lineGraphView.viewport.setMinY(70.0)
        lineGraphView.viewport.setMaxY(80.0)
        lineGraphView.viewport.setYAxisBoundsManual(true)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(param1)
        parcel.writeString(param2)
    }

    override fun describeContents(): Int {
        return 0
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
}




