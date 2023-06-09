package com.example.flexvideotm
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.red
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

//public lateinit var seriesWeight : LineGraphSeries<DataPoint>

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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val mainActivity = requireActivity() as MainActivity


        setupGraphWeight(seriesWeight,seriesWeightGoal)

        setupGraphFat(seriesFat,seriesFatGoal)

        //Graph für BMI
        val seriesBMIUnter = LineGraphSeries<DataPoint>(
            arrayOf(
                DataPoint(0.0, 18.5),
                DataPoint(1.0, 18.5),
                DataPoint(2.0, 18.5),
                DataPoint(3.0, 18.5),
                DataPoint(4.0, 18.5),
                DataPoint(5.0, 18.5),
                DataPoint(6.0, 18.5),
                DataPoint(7.0, 18.5),
                DataPoint(11.0, 18.5)
            )
        )
        val seriesBMIUeber = LineGraphSeries<DataPoint>(
            arrayOf(
                DataPoint(0.0, 25.0),
                DataPoint(1.0, 25.0),
                DataPoint(2.0, 25.0),
                DataPoint(3.0, 25.0),
                DataPoint(4.0, 25.0),
                DataPoint(5.0, 25.0),
                DataPoint(6.0, 25.0),
                DataPoint(7.0, 25.0),
                DataPoint(11.0, 25.0)
            )
        )
        val seriesBMIAsipo = LineGraphSeries<DataPoint>(
            arrayOf(
                DataPoint(0.0, 30.0),
                DataPoint(1.0, 30.0),
                DataPoint(2.0, 30.0),
                DataPoint(3.0, 30.0),
                DataPoint(4.0, 30.0),
                DataPoint(5.0, 30.0),
                DataPoint(6.0, 30.0),
                DataPoint(7.0, 30.0),
                DataPoint(11.0, 30.0)
            )
        )

        setupGraphBMI(seriesBMIUnter,seriesBMIUeber,seriesBMIAsipo,seriesBMI)
    }


    fun setupGraphWeight(seriesWeight: LineGraphSeries<DataPoint>,seriesWeightGoal: LineGraphSeries<DataPoint>) {
        val lineGraphView: GraphView = requireView().findViewById(R.id.idGraphView)

        val gridLabelRenderer = lineGraphView.gridLabelRenderer
        gridLabelRenderer.setHorizontalAxisTitle("Kalenderwoche")
        gridLabelRenderer.setVerticalAxisTitle("Gewicht in KG")

        seriesWeight.color = resources.getColor(R.color.white)
        seriesWeightGoal.color = resources.getColor(R.color.black)

        lineGraphView.addSeries(seriesWeight)
        lineGraphView.addSeries(seriesWeightGoal)

        lineGraphView.viewport.isScrollable = true
        lineGraphView.viewport.isScalable = true
        lineGraphView.viewport.setScalableY(true)
        lineGraphView.viewport.setScrollableY(true)
        lineGraphView.viewport.isXAxisBoundsManual = true


        lineGraphView.viewport.setMinX(0.0)
        lineGraphView.viewport.setMaxX(8.0)
        lineGraphView.viewport.setMinY(70.0)
        lineGraphView.viewport.setMaxY(100.0)
        lineGraphView.viewport.setYAxisBoundsManual(true)

    }

    fun setupGraphFat(seriesFat: LineGraphSeries<DataPoint>,seriesFatGoal: LineGraphSeries<DataPoint>) {
        val lineGraphView: GraphView = requireView().findViewById(R.id.idGraphView2)

        val gridLabelRenderer = lineGraphView.gridLabelRenderer
        gridLabelRenderer.setHorizontalAxisTitle("Kalenderwoche")
        gridLabelRenderer.setVerticalAxisTitle("Körperfett in %")

        seriesFat.color = resources.getColor(R.color.white)
        seriesFatGoal.color = resources.getColor(R.color.black)

        lineGraphView.addSeries(seriesFat)
        lineGraphView.addSeries(seriesFatGoal)

        lineGraphView.viewport.isScrollable = true
        lineGraphView.viewport.isScalable = true
        lineGraphView.viewport.setScalableY(true)
        lineGraphView.viewport.setScrollableY(true)
        lineGraphView.viewport.isXAxisBoundsManual = true


        lineGraphView.viewport.setMinX(0.0)
        lineGraphView.viewport.setMaxX(8.0)
        lineGraphView.viewport.setMinY(8.0)
        lineGraphView.viewport.setMaxY(40.0)
        lineGraphView.viewport.setYAxisBoundsManual(true)
    }

    fun setupGraphBMI(seriesUnter: LineGraphSeries<DataPoint>,seriesUeber: LineGraphSeries<DataPoint>,seriesAsipo: LineGraphSeries<DataPoint>,seriesBMI: LineGraphSeries<DataPoint>) {
        val lineGraphView: GraphView = requireView().findViewById(R.id.idGraphView3)

        val gridLabelRenderer = lineGraphView.gridLabelRenderer
        gridLabelRenderer.setHorizontalAxisTitle("Kalenderwoche")
        gridLabelRenderer.setVerticalAxisTitle("BMI")

        seriesUnter.color = android.graphics.Color.RED
        seriesUeber.color = android.graphics.Color.YELLOW
        seriesAsipo.color = android.graphics.Color.RED
        seriesBMI.color = android.graphics.Color.WHITE

        //seriesUnter.color = resources.getColor(R.color.red)
        //seriesFatGoal.color = resources.getColor(R.color.black)

        lineGraphView.addSeries(seriesUnter)
        lineGraphView.addSeries(seriesUeber)
        lineGraphView.addSeries(seriesAsipo)
        lineGraphView.addSeries(seriesBMI)

        lineGraphView.viewport.isScrollable = true
        lineGraphView.viewport.isScalable = true
        lineGraphView.viewport.setScalableY(true)
        lineGraphView.viewport.setScrollableY(true)
        lineGraphView.viewport.isXAxisBoundsManual = true


        lineGraphView.viewport.setMinX(0.0)
        lineGraphView.viewport.setMaxX(8.0)
        lineGraphView.viewport.setMinY(10.0)
        lineGraphView.viewport.setMaxY(40.0)
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





