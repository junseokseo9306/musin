package com.example.musinsa.common

import com.example.musinsa.model.CoroutineExceptionType
import java.net.SocketException
import java.net.UnknownHostException

object CustomException {
    fun check(throwable: Throwable) = when (throwable) {
        is SocketException -> CoroutineExceptionType(
            throwable = throwable,
            errorMessage = "소켓이 생성되지 않았습니다."
        )
        is UnknownHostException -> CoroutineExceptionType(
            throwable = throwable,
            errorMessage = "IP주소가 올바르지 않습니다"
        )
        else -> CoroutineExceptionType(
            throwable = throwable,
            errorMessage = "알수없는 오류입니다"
        )
    }
}