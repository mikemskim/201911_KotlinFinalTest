package com.tjoeun.a201911_kotlinfinaltest

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lotto.*

class LottoActivity : BaseActivity() {

    var mHandler = Handler()

    var isNowBuying = false

    var firstRankCount = 0
    var secondRankCount = 0
    var thirdRankCount = 0
    var fourthRankCount = 0
    var fifthRankCount = 0
    var wrongCount = 0

//     누적 사용금액
    var usedMoney = 0L
//    누적 당첨금액
    var luckyMoney = 0L

//    기존 당첨 번호 저장 변수
    var lottoNumArrayList = ArrayList<Int>()
    var bonusNum = 0

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

            usedMoney+=1000
            usedMoneyTxt.text = String.format("사용금액 : %,d원", usedMoney)
        }

        autoLottoBtn.setOnClickListener {
            if (!isNowBuying) {
                doLottoLoop()
                isNowBuying = true
                autoLottoBtn.text = "구매 중단"
            } else {
                stopLottoLoop()
                isNowBuying = false
                autoLottoBtn.text = "자동 구매 재개"
            }

//            while (true) {
//
//
//                if (usedMoney >= 100000000) {
//                    break
//                }
////                if (luckyMoney >= 2000000000) {
////                    break
////                }
//
//            }
        }
    }

//    당첨 결과를 체크 몇등인지 확인
//    6개 : 1등 => 20억원
//    5개 : 3등 => 150 만원
//    4개 : 4등 => 5만원
//    3개 : 5등 => 5천원
//    그 이하 : 꽝 => 0 원

    fun checkLottoRank() {
        var totalScore = 0

        for (myNum in myNumberArrayList) {

            for (thisWeekNum in lottoNumArrayList) {
                if (myNum == thisWeekNum) {
                    totalScore++
                }
            }
        }

        when(totalScore) {
            6 -> {
//                Toast.makeText(mContext, "1등 당첨", Toast.LENGTH_SHORT).show()
                luckyMoney += 2000000000
                firstRankCount++
            }
            5 -> {
                var isSecondRank = true

                if (!myNumberArrayList.contains(bonusNum)) {
                    isSecondRank = false
                }

//                Toast.makeText(mContext, "3등 당첨", Toast.LENGTH_SHORT).show()
                if (isSecondRank) {
                    luckyMoney += 65000000
                    secondRankCount++
                } else {
                    luckyMoney += 1500000
                    thirdRankCount++
                }

                luckyMoney += 1500000
                thirdRankCount++
            }
            4 -> {
//                Toast.makeText(mContext, "4등 당첨", Toast.LENGTH_SHORT).show()
                luckyMoney += 50000
                fourthRankCount++
            }
            3 -> {
//                Toast.makeText(mContext, "5등 당첨", Toast.LENGTH_SHORT).show()
                usedMoney -= 5000
                fifthRankCount++
            }
            else -> {
//                Toast.makeText(mContext, "꽝", Toast.LENGTH_SHORT).show()
                luckyMoney += 0
                wrongCount++
            }
        }

        luckyMoneyTxt.text = String.format("누적 당첨 금액 : %,d원", luckyMoney)
        firstRankCountTxt.text = String.format("1등 횟수 : %,d", firstRankCount)
        secondRankCountTxt.text = String.format("2등 횟수 : %,d", secondRankCount)
        thirdRankCountTxt.text = String.format("3등 횟수 : %,d", thirdRankCount)
        fourthRankCountTxt.text = String.format("4등 횟수 : %,d", fourthRankCount)
        fifthRankCountTxt.text = String.format("5등 횟수 : %,d", fifthRankCount)
        wrongCountTxt.text = String.format("꽝 횟수 : %,d", wrongCount)
    }


    var lottoRunnable = object : Runnable {
        override fun run() {
            if (usedMoney < 100000000) {
                setThisWeekLottoNum()
                checkLottoRank()
                usedMoney+=1000
                usedMoneyTxt.text = String.format("사용금액 : %,d원", usedMoney)
                doLottoLoop()
            } else {
                runOnUiThread {
                    Toast.makeText(mContext, "로또 구매를 종료합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun doLottoLoop() {
        // 할일 등록
        mHandler.post(lottoRunnable)
    }

    fun stopLottoLoop() {
        // 할일 제거
        mHandler.removeCallbacks(lottoRunnable)
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

//        보너스 번호를 추가로 뽑자, 중복을 피해야함. => 몇번이나 뽑아야 중복이 아닐지 알 수 없다.

        while (true) {
            var tempRandomNum = (Math.random() * 45 + 1).toInt()
            var isDupNum = true

            for (num in lottoNumArrayList) {
                if (num == tempRandomNum) {
                    isDupNum = false
                    break
                }
            }

            if (isDupNum) {
                bonusNum = tempRandomNum
                break
            }
        }


        for (i in 0..(lottoNumArrayList.size-1)) {
            var numTxt = thisWeekLottoNumTextViewArrayList.get(i)
            var number = lottoNumArrayList.get(i)

            numTxt.text = number.toString()
        }

        bonusNumTxt.text = bonusNum.toString()
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
