package s72574.lab6.android_tabs

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


class StatusFragment : Fragment(R.layout.fragment_status), SensorEventListener {

    //lateinit = the elements/variable will be initialized later (only work with var)
    //in fragment, layout available after onCreateView / onViewCreated()
    //val = value can't be change
    //var = value can be change
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var tvLux: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Find TextView inside xml file, assigns to progressBar and tvLux, connect kotlin with xml
        progressBar = view.findViewById(R.id.lightProgress)
        tvLux = view.findViewById(R.id.tvLuxValue)

        //Sensor Manager = Accessing all hardware sensors
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //Request the device's default ambient light sensor, TYPE_LIGHT measures environment brightness in lux
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        //if there are no light sensor in the device, it will return null message
        if (lightSensor == null) {
            tvLux.text = "Light sensor not available"
            progressBar.progress = 0
        }
    }

    //updates UI, register the light sensor listener, display status message when Fragment become active
    //used when user switch between tabs in the apps
    override fun onResume() {
        super.onResume()
        applyPrefsToUi()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        Toast.makeText(requireContext(), "Status opened", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lux = event.values[0].toInt()
            tvLux.text = "$lux Lux" //read the environment brightness
            progressBar.progress = lux.coerceIn(0, 300) // progress bar in one line, with min value 0, max value 300
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun applyPrefsToUi() {

        //Retrieve saved data, set default value if no data saved
        val sp = requireActivity().getSharedPreferences(Prefs.FILE_NAME, Context.MODE_PRIVATE)
        val theme = sp.getString(Prefs.KEY_THEME, "Blue") ?: "Blue"
        val fontSize = sp.getInt(Prefs.KEY_FONT_SIZE, 18)

        //set text size based on font size selected by user
        tvLux.textSize = fontSize.toFloat()

        val root = requireView()

        //set theme based on theme selected by user
        when (theme) {
            "Dark" -> {
                root.setBackgroundColor(0xFF121212.toInt())
                tvLux.setTextColor(0xFFFFFFFF.toInt())
            }
            "Light" -> {
                root.setBackgroundColor(0xFFFFFFFF.toInt())
                tvLux.setTextColor(0xFF000000.toInt())
            }
            else -> { // Blue
                root.setBackgroundColor(0xFFF1F8E9.toInt())
                tvLux.setTextColor(0xFF1B5E20.toInt())
            }
        }
    }
}