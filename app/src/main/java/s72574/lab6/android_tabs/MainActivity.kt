package s72574.lab6.android_tabs

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //find layout components in layout
        val viewPager = findViewById<ViewPager2>(R.id.viewPager) //swipeable pages
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout) //tabs at the top

        viewPager.adapter = MyViewPagerAdapter(this) //set adapter for viewpager

        // link tabs and pages
        TabLayoutMediator(tabLayout, viewPager) { tab, pos -> //set tab based on position
            tab.text = listOf("Home", "Status", "Settings")[pos]
        }.attach()
    }
}