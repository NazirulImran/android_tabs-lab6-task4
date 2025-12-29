package s72574.lab6.android_tabs

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private val themes = listOf("Blue", "Light", "Dark")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //val = value can't be change
        //var = value can be change
        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val spTheme = view.findViewById<Spinner>(R.id.spTheme)
        val seekFont = view.findViewById<SeekBar>(R.id.seekFont)
        val tvFontLabel = view.findViewById<TextView>(R.id.tvFontLabel)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        // Spinner setup
        spTheme.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            themes
        )

        // listen to seekbar changes and updates the font size label dynamically while enforcing min value (12)
        seekFont.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress:
            Int, fromUser: Boolean) {
                val size = if (progress < 12) 12 else progress
                tvFontLabel.text = "Font Size: $size"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {} //detect when user start dragging seekbar
            override fun onStopTrackingTouch(seekBar: SeekBar?) {} //detect when user stop dragging seekbar
        })

        // Load prefs choose by user into form
        loadPrefsToForm(etUsername, spTheme, seekFont, tvFontLabel)

        // Save button
        btnSave.setOnClickListener {
            savePrefs(etUsername, spTheme, seekFont)
            Toast.makeText(requireContext(), "Saved!",
                Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(requireContext(), "Settings opened",
            Toast.LENGTH_SHORT).show()
    }

    //loads saved user preferences from SharedPreferences and fills them into setting form UI
    private fun loadPrefsToForm(
        etUsername: EditText,
        spTheme: Spinner,
        seekFont: SeekBar,
        tvFontLabel: TextView
    ) {
        val sp =
            requireActivity().getSharedPreferences(Prefs.FILE_NAME,
                Context.MODE_PRIVATE)

        val username = sp.getString(Prefs.KEY_USERNAME, "") ?: ""
        val theme = sp.getString(Prefs.KEY_THEME, "Blue") ?: "Blue"
        val fontSize = sp.getInt(Prefs.KEY_FONT_SIZE, 18)

        etUsername.setText(username) //apply values to UI components
        //set spinner selection
        spTheme.setSelection(themes.indexOf(theme).coerceAtLeast(0))
        //set font size
        seekFont.progress = fontSize
        tvFontLabel.text = "Font Size: $fontSize" //update label
    }

    //saves user input to SharedPreferences
    private fun savePrefs(
        etUsername: EditText,
        spTheme: Spinner,
        seekFont: SeekBar
    ) {
        val username = etUsername.text.toString().trim().ifEmpty { //trim/removes leading/trailing spaces
            "Guest" } //prevent empty usernames
        val theme = spTheme.selectedItem.toString() //read selected theme
        val size = if (seekFont.progress < 12) 12 else //read current seakbar value, enforce min size = 12
            seekFont.progress

        val sp =
            //open app's preference storage file
            requireActivity().getSharedPreferences(Prefs.FILE_NAME,
                Context.MODE_PRIVATE)
        //saves values to SharedPreferences
        sp.edit()
            .putString(Prefs.KEY_USERNAME, username)
            .putString(Prefs.KEY_THEME, theme)
            .putInt(Prefs.KEY_FONT_SIZE, size)
            .apply()
    }
}