package com.maum.note.core.common.util

import com.github.f4b6a3.uuid.UuidCreator
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

object Generate {
    fun randomUUIDv7() = UuidCreator.getTimeOrderedEpoch().toString()

    fun randomNickname(length: Int = 4): String {
        val adjective = adjectives.randomOrNull() ?: "수수한"
        val noun = nouns.randomOrNull() ?: "유저"
        val suffix = generateRandomAlphanumeric(length)
        return "$adjective$noun$suffix"
    }

    fun now(): Instant {
        return Clock.System.now()
    }

    private val adjectives = listOf(
        "너무진지한", "과몰입한", "현실도피하는", "반쯤포기한", "희망고문중인",
        "기묘한", "초조한", "행복회로도는", "기대이상인", "어쩌다보니",
        "막차탄", "인생2회차인", "지금달리는", "어제잠못잔", "무한스크롤하는",
        "회전초밥같은", "할말잃은", "심해잠수중인", "눈치보는", "시간도둑맞은",
        "갓생사는", "약속파토낸", "깊은생각중인", "슬슬현타온", "의욕불끈한",
        "언제나초심인", "현실직시중인", "정지화면같은", "기술부족한", "편의점가는"
    )

    private val nouns = listOf(
        "프리랜서", "감성러", "눈치쟁이", "야망가", "방구석철학자",
        "아르케중생", "심야먹보", "프로망상가", "자취생", "회의요정",
        "버튼연타러", "헬창꿈나무", "과몰입러", "미루기장인", "디버거",
        "마법사", "출근요정", "퇴근의요정", "지하철탑승자", "퇴사준비생",
        "언락러", "치킨러버", "스케줄파괴자", "슬리퍼전사", "새벽감성러",
        "관종인턴", "발표전날자기", "헛웃음장인", "알림장노예", "휴식예정자"
    )

    private fun generateRandomAlphanumeric(length: Int): String {
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
}
