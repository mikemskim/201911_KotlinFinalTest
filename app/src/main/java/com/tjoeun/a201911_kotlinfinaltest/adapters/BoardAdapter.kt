package com.tjoeun.a201911_kotlinfinaltest.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tjoeun.a201911_kotlinfinaltest.R
import com.tjoeun.a201911_kotlinfinaltest.datas.BoardData
import com.tjoeun.a201911_kotlinfinaltest.datas.NoticeData

class BoardAdapter(context: Context, res:Int, list: ArrayList<BoardData>) : ArrayAdapter<BoardData>(context, res, list)  {

    var mContext = context
    var mList = list
    var inf = LayoutInflater.from(mContext)

    constructor(context: Context, list: ArrayList<BoardData>) : this (context, R.layout.board_list_item, list)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.board_list_item, null)
        }

        var row = tempRow!!

        var boardData = mList.get(position)

        var boardNameTxt = row.findViewById<TextView>(R.id.boardNameTxt)
        var boardTitleTxt = row.findViewById<TextView>(R.id.boardTitleTxt)
        var boardContentTxt = row.findViewById<TextView>(R.id.boardContentTxt)
        var boardCategoryTxt = row.findViewById<TextView>(R.id.boardCategoryTxt)

        boardNameTxt.text = boardData.name
        boardTitleTxt.text = boardData.title
        boardContentTxt.text = boardData.content
//        boardCategoryTxt.text = boardData.category.title.substring(0,1)
        boardCategoryTxt.text = boardData.category.title.first().toString()

        boardCategoryTxt.background.setColorFilter(Color.parseColor(boardData.category.color), PorterDuff.Mode.SRC_ATOP)

        return row
    }
}