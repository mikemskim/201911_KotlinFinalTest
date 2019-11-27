package com.tjoeun.a201911_kotlinfinaltest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tjoeun.a201911_kotlinfinaltest.R
import com.tjoeun.a201911_kotlinfinaltest.adapters.BoardAdapter
import com.tjoeun.a201911_kotlinfinaltest.datas.BoardData
import com.tjoeun.a201911_kotlinfinaltest.datas.NoticeData
import com.tjoeun.a201911_kotlinfinaltest.utils.ServerUtil
import kotlinx.android.synthetic.main.board_fragment.*
import org.json.JSONObject

class BoardFragment : BaseFragment() {

    var boardList = ArrayList<BoardData>()

    var boardAdapter:BoardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.board_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        boardAdapter = BoardAdapter(mContext!!, boardList)
        boardListView.adapter = boardAdapter

        ServerUtil.getRequestBlackList(mContext!!, object : ServerUtil.jsonResponseHandler {
            override fun onResponse(json: JSONObject) {
                var code = json.getInt("code")

                if (code == 200) {
                    var data = json.getJSONObject("data")
                    var black_lists = data.getJSONArray("black_lists")

                    for (i in 0..(black_lists.length()-1)) {
                        var black_list = black_lists.getJSONObject(i)
                        boardList.add(BoardData.getBoardDataFromJson(black_list))
                    }

                    boardAdapter?.notifyDataSetChanged()
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