package com.tjoeun.a201911_kotlinfinaltest.datas

import org.json.JSONObject

class CategoryData {
    var id = 0
    var title:String = ""
    var color:String = ""

    companion object {
        fun getCategoryDataFromJson(json:JSONObject) :CategoryData{
            var category = CategoryData()

            category.id = json.getInt("id")
            category.title = json.getString("title")
            category.color = json.getString("color")

            return category
        }
    }
}