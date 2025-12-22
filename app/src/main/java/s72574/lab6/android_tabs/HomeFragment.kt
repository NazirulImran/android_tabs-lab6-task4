package s72574.lab6.android_tabs

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var tvTitle: TextView
    private lateinit var tvUser: TextView
    private lateinit var tvTheme: TextView
    private lateinit var tvFont: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle = view.findViewById(R.id.tvHomeTitle)
        tvUser = view.findViewById(R.id.tvHomeUser)
        tvTheme = view.findViewById(R.id.tvHomeTheme)
        tvFont = view.findViewById(R.id.tvHomeFont)
    }

    override fun onResume() {
        super.onResume()
        applyPrefs()
        // FIXED: Added underscore to LENGTH_SHORT
        Toast.makeText(requireContext(), "Status opened", Toast.LENGTH_SHORT).show()
    }

    private fun applyPrefs() {
        // FIXED: Added underscore to MODE_PRIVATE
        val sp = requireActivity().getSharedPreferences(
            Prefs.FILE_NAME,
            Context.MODE_PRIVATE
        )

        val username = sp.getString(Prefs.KEY_USERNAME, "Guest") ?: "Guest"

        // FIXED: Added underscores to keys
        val theme = sp.getString(Prefs.KEY_THEME, "Blue") ?: "Blue"
        val fontSize = sp.getInt(Prefs.KEY_FONT_SIZE, 18)

        tvUser.text = "User: $username"
        tvTheme.text = "Theme: $theme"
        tvFont.text = "Font Size: ${fontSize}sp"

        // Apply font size
        val size = fontSize.toFloat()
        tvTitle.textSize = size
        tvUser.textSize = size
        tvTheme.textSize = size
        tvFont.textSize = size

        // Apply simple theme
        val root = requireView()
        when (theme) {
            "Dark" -> {
                root.setBackgroundColor(0xFF121212.toInt())
                setTextColor(0xFFFFFFFF.toInt())
            }
            "Light" -> {
                root.setBackgroundColor(0xFFFFFFFF.toInt())
                setTextColor(0xFF000000.toInt())
            }
            else -> { // Blue
                root.setBackgroundColor(0xFFE3F2FD.toInt())
                setTextColor(0xFF0D47A1.toInt())
            }
        }
    }

    private fun setTextColor(color: Int) {
        tvTitle.setTextColor(color)
        tvUser.setTextColor(color)
        tvTheme.setTextColor(color)
        tvFont.setTextColor(color)
    }
}