package com.developer.allefsousa.toptp.config

import com.developer.allefsousa.toptp.enum.HmacAlgorithm

open class HmacOneTimePasswordConfig(private val codeDigits: Int, val hmacAlgorithm: HmacAlgorithm) {
    init {
        require(codeDigits >= 0) { "Number of code digits must be positive." }
    }
}