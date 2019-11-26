package com.tjoeun.a201911_kotlinfinaltest.utils

import android.content.Context

class ContextUtil {

    companion object {
        val prefName = "PracticePrefference"

        val USER_ID = "USER_ID"
        val SAVE_ID = "SAVE_ID"
        val PASSWORD = "PASSWORD"
        val USER_TOKEN = "USER_TOKEN"

        fun setUserId(context: Context, userId:String) {
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(USER_ID, userId).apply()
        }

        fun getUserId(context: Context) : String {
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(USER_ID, "")!!
        }

        fun setSaveId(context: Context, saveId:Boolean) {
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(SAVE_ID, saveId).apply()
        }

        fun getSaveId(context: Context) : Boolean {
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(SAVE_ID, false)!!
        }

        fun setPassword(context: Context, password:String) {
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(PASSWORD, password).apply()
        }

        fun getPassword(context: Context) : String {
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(PASSWORD, "")!!
        }

        fun setToken(context: Context, token:String) {
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(USER_TOKEN, token).apply()
        }

        fun getToken(context: Context) : String {
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(USER_TOKEN, "")!!
        }
    }
}