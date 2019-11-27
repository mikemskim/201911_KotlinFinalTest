package com.tjoeun.a201911_kotlinfinaltest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tjoeun.a201911_kotlinfinaltest.utils.ContextUtil
import com.tjoeun.a201911_kotlinfinaltest.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        saveIdChkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                ContextUtil.setUserId(mContext, idTxt.text.toString())
                ContextUtil.setPassword(mContext, passwordTxt.text.toString())
                ContextUtil.setSaveId(mContext, isChecked)

            }
        }

        loginBtn.setOnClickListener {
            ServerUtil.postRequestLogin(mContext,idTxt.text.toString(),passwordTxt.text.toString(), object : ServerUtil.jsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    var code = json.getInt("code")
                    Log.d("TEST", json.toString())

                    runOnUiThread {
                        if (code == 200) {
                            var data = json.getJSONObject("data")
                            var user = data.getJSONObject("user")
                            ContextUtil.setToken(mContext, data.getString("token"))
                            var msg = "${user.getString("name")}님 환영합니다."
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                            var intent = Intent(mContext, BoardActivity::class.java)
                            startActivity(intent)
                        } else {
                            var msg = json.getString("message")
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            })
        }
    }

    override fun setValues() {

        if (ContextUtil.getSaveId(mContext)) {
            idTxt.setText(ContextUtil.getUserId(mContext))
            passwordTxt.setText(ContextUtil.getPassword(mContext))
            saveIdChkBox.isChecked = ContextUtil.getSaveId(mContext)
        }
    }
}
