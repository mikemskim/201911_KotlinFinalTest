package com.tjoeun.a201911_kotlinfinaltest.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tjoeun.a201911_kotlinfinaltest.fragments.BoardFragment
import com.tjoeun.a201911_kotlinfinaltest.fragments.NoticeFragment

class BoardViewPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                BoardFragment()
            }
            else -> {
                NoticeFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> {
                "게시판"
            }
            else -> {
                "공지사항"
            }
        }
    }
}