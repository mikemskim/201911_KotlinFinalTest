package com.tjoeun.a201911_kotlinfinaltest

import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_lotto.*

class LottoActivity : BaseActivity() {

    var lottoNumArrayList = ArrayList<Int>()
    var thisWeekLottoNumTextViewArrayList = ArrayList<TextView>()

    var myNumberArrayList = ArrayList<Int>()
    var myTextViewArrayList = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        buyOneLottoBtn.setOnClickListener {
//          숫자를 랜덤으로 6개 생성
            setThisWeekLottoNum()

            checkLottoRank()
        }
    }

//    당첨 결과를 체크 몇등인지 확인
    fun checkLottoRank() {

//    6개 : 1등 => 20억원
//    5개 : 3등 => 150 만원
//    4개 : 4등 => 5만원
//    3개 : 5등 => 5천원
//    그 이하 : 꽝 => 0 원


    }

    fun setThisWeekLottoNum() {

//        당첨번호는 모두 날리고 다시 뽑자
        lottoNumArrayList.clear()

        for (lottoNumTxt in thisWeekLottoNumTextViewArrayList) {

            var randomNum = 0
            while (true) {
                randomNum = (Math.random() * 45 +1).toInt()

                var isDupOK = true
                for (num in lottoNumArrayList) {
                    if(num == randomNum) {
//                        중복되는 숫자를 발견
                        isDupOK = false
                        break
                    }
                }

                if (isDupOK) {

                    lottoNumArrayList.add(randomNum)
                    break
                }
            }


//            lottoNumTxt.text = randomNum.toString()
        }

//        당첨번호 6개를 작은 숫자부터 큰 숫자 순서대로 (정렬)
        lottoNumArrayList.sort()

        for (i in 0..(lottoNumArrayList.size-1)) {
            var numTxt = thisWeekLottoNumTextViewArrayList.get(i)
            var number = lottoNumArrayList.get(i)

            numTxt.text = number.toString()
        }
    }

    override fun setValues() {

        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt1)
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt2)
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt3)
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt4)
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt5)
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt6)

        myTextViewArrayList.add(myLottoNumTxt1)
        myTextViewArrayList.add(myLottoNumTxt2)
        myTextViewArrayList.add(myLottoNumTxt3)
        myTextViewArrayList.add(myLottoNumTxt4)
        myTextViewArrayList.add(myLottoNumTxt5)
        myTextViewArrayList.add(myLottoNumTxt6)

        for (myTv in myTextViewArrayList) {
            myNumberArrayList.add(myTv.text.toString().toInt())
        }
    }
}
