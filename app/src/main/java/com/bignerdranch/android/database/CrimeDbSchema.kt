package com.bignerdranch.android.database

class CrimeDbSchema {
    class CrimeTable {
        companion object Cols {
            const val NAME: String = "crimes"
            const val UUID: String = "uuid"
            const val TITLE: String = "title"
            const val DATE: String = "date"
            const val SOLVED: String = "solved"
        }
    }
}