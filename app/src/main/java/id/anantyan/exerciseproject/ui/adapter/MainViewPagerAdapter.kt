package id.anantyan.exerciseproject.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(
    fm: FragmentManager,
    lifeCycle: Lifecycle,
    private val listFragment: List<Fragment>
) : FragmentStateAdapter(fm, lifeCycle) {

    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}