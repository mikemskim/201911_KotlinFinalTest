package com.tjoeun.a201911_kotlinfinaltest.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class BoardData {
    var id:Int = 0
    var title:String =""
    var content:String = ""
    var name:String = ""
    var createdAt = Calendar.getInstance()

    var category:CategoryData = CategoryData()

    val serverTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var listDisplayTimeFormat = SimpleDateFormat("yyyy년 M월 d일\nh시 m분")

    fun getFormattedCreatedAt() : String {
        return listDisplayTimeFormat.format(this.createdAt.time)
    }

    companion object {
        fun getBoardDataFromJson(json: JSONObject) : BoardData {
            var boardData = BoardData()

            boardData.id = json.getInt("id")
            boardData.title = json.getString("title")
            boardData.content = json.getString("content")
            boardData.createdAt.time = boardData.serverTimeFormat.parse(json.getString("created_at"))

            var writer = json.getJSONObject("writer")
            boardData.name = writer.getString("name")

            var categoryJson = writer.getJSONObject("category")
            boardData.category = CategoryData.getCategoryDataFromJson(categoryJson)

            return boardData
        }
    }
}