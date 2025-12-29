package s72574.lab6.android_tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyViewPagerAdapter(fa: FragmentActivity) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 3 //tells ViewPager2 how many pages exist

    override fun createFragment(position: Int): Fragment { //create fragment for each page
        return when (position) {
            0 -> HomeFragment() //page 1
            1 -> StatusFragment() //page 2
            else -> SettingFragment() //page 3
        }
    }
}