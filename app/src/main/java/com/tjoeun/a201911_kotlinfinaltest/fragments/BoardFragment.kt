package com.tjoeun.a201911_kotlinfinaltest.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tjoeun.a201911_kotlinfinaltest.EditBlackListActivity
import com.tjoeun.a201911_kotlinfinaltest.R
import com.tjoeun.a201911_kotlinfinaltest.adapters.BoardAdapter
import com.tjoeun.a201911_kotlinfinaltest.datas.BoardData
import com.tjoeun.a201911_kotlinfinaltest.datas.NoticeData
import com.tjoeun.a201911_kotlinfinaltest.utils.ServerUtil
import kotlinx.android.synthetic.main.board_fragment.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BoardFragment : BaseFragment() {

    var dateFilterStartDate: Calendar? = null

    var boardList = ArrayList<BoardData>()
    var filteredBoardList = ArrayList<BoardData>()

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

    override fun onResume() {
        super.onResume()

        getBlacklistFromServer()

    }

    override fun setupEvents() {

//        날짜 필터 선택을 누르면 => 몇일부터 필터를 하고 싶은지 DatePicker로 선택
//        선택한 결과를 텍스트뷰에 반영
//        dateFilterStartDate가 null이면 초기화 년/월/일 세팅
//        날짜는 2019.09.06 ~ 양식으로 반영


        dateFilterBtn.setOnClickListener {
            var datePickerDialog = DatePickerDialog(mContext!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                if (dateFilterStartDate == null) {
                    dateFilterStartDate = Calendar.getInstance()
                }

                // 시작일시 변수에 선택값 반영
                dateFilterStartDate?.set(year, month, dayOfMonth)

                // 버튼에 2019-09-08 형식으로 출력
                var sdf = SimpleDateFormat("yyyy-MM-dd ~")

                dateFilterTxt.text = sdf.format(dateFilterStartDate!!.time)
                filterBlackLists()
            }, 2019, Calendar.NOVEMBER, 1)

            datePickerDialog.setOnCancelListener {
                dateFilterTxt.text = "없음"
            }

            datePickerDialog.show()

        }



        writeBlackListBtn.setOnClickListener {
            var intent = Intent(mContext!!, EditBlackListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun setValues() {
        boardAdapter = BoardAdapter(mContext!!, filteredBoardList)
        boardListView.adapter = boardAdapter

//        getBlacklistFromServer()
    }

    fun filterBlackLists() {

        filteredBoardList.clear()

        for (bl in boardList) {
            if (dateFilterStartDate == null) {
//               날짜 필터가 설정되지 않았다면 무조건 화면에 보여지라고 필터된 목록에 추가
                filteredBoardList.add(bl)
            } else {
//                날짜 필터가 설정되어 있다면, 게시글의 작성일자 >= 날짜 필터 면 보이도록 목록에 추가
                if (bl.createdAt.timeInMillis >= dateFilterStartDate!!.timeInMillis) {
                    filteredBoardList.add(bl)
                }
            }
        }

        boardAdapter?.notifyDataSetChanged()
    }

    fun getBlacklistFromServer() {
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

                    activity!!.runOnUiThread {
                        boardAdapter?.notifyDataSetChanged()
                        filterBlackLists()
                    }

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