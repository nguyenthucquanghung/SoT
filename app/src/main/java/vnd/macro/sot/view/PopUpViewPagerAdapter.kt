package vnd.macro.sot.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PopUpViewPagerAdapter internal constructor(fm: FragmentManager, selectedText: String) : FragmentPagerAdapter(fm) {

    private val COUNT = 2
    private val st = selectedText
    override fun getItem(position: Int): Fragment {
        return if (position == 0)
            SearchFragment.newInstance(st)
        else
            SelectFragment.newInstance(st)
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 1) "SELECT" else "SEARCH"
    }
}
