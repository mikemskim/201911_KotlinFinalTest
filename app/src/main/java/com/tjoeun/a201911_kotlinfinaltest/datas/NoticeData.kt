package com.tjoeun.a201911_kotlinfinaltest.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class NoticeData {

    var id:Int = 0
    var title:String =""
    var content:String =""
    var createdAt = Calendar.getInstance()

    val serverTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var listDisplayTimeFormat = SimpleDateFormat("yyyy년 M월 d일\nh시 m분")

    fun getFormattedCreatedAt() : String {
        return listDisplayTimeFormat.format(this.createdAt.time)
    }

    companion object {
//        val serverTimeParseFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        fun getNoticeDataFromJson(json:JSONObject) : NoticeData {
            var noticeData = NoticeData()

            noticeData.id = json.getInt("id")
            noticeData.title = json.getString("title")
            noticeData.content = json.getString("content")
            noticeData.createdAt.time = noticeData.serverTimeFormat.parse(json.getString("created_at"))

            return noticeData
        }
    }

}