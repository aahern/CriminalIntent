package com.bignerdranch.android

import java.util.*

class Crime() {
    val uuid = UUID.randomUUID()
    var date = Date()
    var title: String? = null
    var solved: Boolean = false
}