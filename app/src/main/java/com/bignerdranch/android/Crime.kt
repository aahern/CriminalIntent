package com.bignerdranch.android

import java.util.*

class Crime {
    var uuid: UUID = UUID.randomUUID()
    var date = Date()
    var title: String? = null
    var solved: Boolean = false

    constructor()
    constructor(id: UUID) {
        uuid = id
        date = Date()
    }
}