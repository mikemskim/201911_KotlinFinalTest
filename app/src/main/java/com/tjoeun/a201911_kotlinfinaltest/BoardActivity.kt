package com.tjoeun.a201911_kotlinfinaltest

import android.os.Bundle
import android.widget.Toast
import com.tjoeun.a201911_kotlinfinaltest.adapters.BoardViewPagerAdapter
import com.tjoeun.a201911_kotlinfinaltest.adapters.NoticeAdapter
import com.tjoeun.a201911_kotlinfinaltest.datas.NoticeData
import com.tjoeun.a201911_kotlinfinaltest.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.notice_fragment.*
import org.json.JSONObject

class BoardActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        boardViewPager.adapter = BoardViewPagerAdapter(supportFragmentManager)
        boardTabLayout.setupWithViewPager(boardViewPager)
    }
}
