package com.tjoeun.a201911_kotlinfinaltest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tjoeun.a201911_kotlinfinaltest.R
import com.tjoeun.a201911_kotlinfinaltest.datas.NoticeData

class NoticeAdapter(context:Context, res:Int, list: ArrayList<NoticeData>) : ArrayAdapter<NoticeData> (context, res, list) {

    var mContext = context
    var mList = list
    var inf = LayoutInflater.from(mContext)

    constructor(context: Context, list: ArrayList<NoticeData>) : this (context, R.layout.notice_list_item, list)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.notice_list_item, null)
        }

        var row = tempRow!!

        var noticeData = mList.get(position)

        var titleTxt = row.findViewById<TextView>(R.id.titleTxt)
        var contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        var createdAtTxt = row.findViewById<TextView>(R.id.createdAtTxt)

        titleTxt.text = noticeData.title
        contentTxt.text = noticeData.content
        createdAtTxt.text = noticeData.getFormattedCreatedAt()

        return row
    }
}