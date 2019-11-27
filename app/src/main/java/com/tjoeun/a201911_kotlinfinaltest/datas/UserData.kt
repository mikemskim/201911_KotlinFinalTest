package com.tjoeun.a201911_kotlinfinaltest.datas

import org.json.JSONObject

class UserData {

    var loginId = ""
    var name = ""
    var phone = ""

//    var category:Category? = null

    companion object {
        fun getUserFromJson(userJson: JSONObject) : UserData {
            var userObject = UserData()

//            왼쪽 : 우리가 만든 클래스 변수
//            오른쪽 : 서버에서 내려주는 Json 활용
            userObject.loginId = userJson.getString("login_id")
            userObject.name = userJson.getString("name")
            userObject.phone = userJson.getString("phone")
//            userObject.category = Category.getCategoryFromJson(userJson.getJSONObject("category"))

            return userObject
        }
    }
}