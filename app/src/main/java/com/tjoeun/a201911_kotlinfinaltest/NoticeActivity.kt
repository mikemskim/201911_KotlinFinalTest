package com.tjoeun.a201911_kotlinfinaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tjoeun.a201911_kotlinfinaltest.adapters.NoticeAdapter
import com.tjoeun.a201911_kotlinfinaltest.datas.NoticeData
import com.tjoeun.a201911_kotlinfinaltest.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_notice.*
import org.json.JSONObject

class NoticeActivity : BaseActivity() {

    var noticeList = ArrayList<NoticeData>()

    var noticeAdapter:NoticeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        noticeAdapter = NoticeAdapter(mContext, noticeList)
        noticeListView.adapter = noticeAdapter

        ServerUtil.getRequestNotice(mContext, object : ServerUtil.jsonResponseHandler {
            override fun onResponse(json: JSONObject) {
                var code = json.getInt("code")

                runOnUiThread {
                    if (code == 200) {
                        var data = json.getJSONObject("data")
                        var notices = data.getJSONArray("notices")

                        for (i in 0..(notices.length()-1)) {
                            var notice = notices.getJSONObject(i)
                            noticeList.add(NoticeData(notice.getInt("id"), notice.getString("title"), notice.getString("content"), notice.getString("created_at")))
                        }

                        noticeAdapter?.notifyDataSetChanged()
                    } else {
                        var msg = json.getString("message")
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT ).show()
                    }
                }
            }

        })
    }
}
