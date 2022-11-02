package com.machineries_pk.mrk.activities.Module2.ui.notifications

import android.R
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.machineries_pk.mrk.activities.Module2.HomeActivity
import com.machineries_pk.mrk.activities.Utils.ProgressBarAnimation
import com.machineries_pk.mrk.databinding.FragmentNotificationsBinding
import io.paperdb.Paper


class NotificationsFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var animation_progress_bar: ProgressBarAnimation? = null


    private val LOG_TAG = "STATS"

    private var stepSensor: Sensor? = null
    private var stepCount = 0
    private var MagnitudePrevious = 0.0
    private var sensorManager: SensorManager? = null

    var running = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        var textView: TextView = binding.totalact
        (activity as HomeActivity?)?.notificationsViewModel?.text?.observe(viewLifecycleOwner) {
            textView.text = it
        }





        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        binding.stepstv.text = "$stepCount\nSteps"

//        clearCounterButton.setOnClickListener(View.OnClickListener {
//            stepCount = 0
//            stepCountTextView.setText(stepCount.toString())
//        })



        binding.anualData.text = "${Paper.book().read("year_carbon", "0.0")} Tonne"
        binding.level.text = "Level ${Paper.book().read(" pro_ranking", 1)} : ${
            Paper.book().read("pro_level", "None")
        }"



var co_save = 0.0
var km_save = 0
        var tree_save = 0

        binding.euroBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                co_save = progress * 1.47
                km_save = progress * 5

                tree_save = (co_save/560).toInt()


                binding.totalKm.text = "${km_save}km"
                binding.totalKg.text = "${co_save}kg"
                binding.totalSpent.text = "Spend ${progress}€"

                binding.totalTree.text = "${tree_save} Tree"
                binding.totaltree.text = "${tree_save}"

            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }
    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepsSensor == null) {
            Toast.makeText(activity, "No Step Counter Sensor !", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
              if (event.values[0] > 2) {
                stepCount++
            }



            animation_progress_bar = ProgressBarAnimation(
                activity, binding.circularProgressbar, binding.stepstv, 0f, stepCount.toFloat()
            )
            animation_progress_bar?.duration = 50
            binding.circularProgressbar.animation = animation_progress_bar

            if (running) {
                binding.stepstv.text = "" + stepCount.toString()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}