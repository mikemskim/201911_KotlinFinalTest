package com.tjoeun.a201911_kotlinfinaltest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tjoeun.a201911_kotlinfinaltest.R
import com.tjoeun.a201911_kotlinfinaltest.adapters.NoticeAdapter
import com.tjoeun.a201911_kotlinfinaltest.datas.NoticeData
import com.tjoeun.a201911_kotlinfinaltest.utils.ServerUtil
import kotlinx.android.synthetic.main.notice_fragment.*
import org.json.JSONObject

class NoticeFragment: BaseFragment() {

    var noticeList = ArrayList<NoticeData>()

    var noticeAdapter:NoticeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notice_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {
    }

    override fun setValues() {
        noticeAdapter = NoticeAdapter(requireContext(), noticeList)
        noticeListView.adapter = noticeAdapter

        ServerUtil.getRequestNotice(requireContext(), object : ServerUtil.jsonResponseHandler {
            override fun onResponse(json: JSONObject) {
                var code = json.getInt("code")

                if (code == 200) {
                    var data = json.getJSONObject("data")
                    var notices = data.getJSONArray("notices")

                    for (i in 0..(notices.length()-1)) {
                        var notice = notices.getJSONObject(i)
                        noticeList.add(NoticeData.getNoticeDataFromJson(notice))
                    }

                    noticeAdapter?.notifyDataSetChanged()
                } else {
                    var msg = json.getString("message")
//                    requireActivity().runOnUiThread {
//                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT ).show()
//                    }
                }
            }
        })
    }
}