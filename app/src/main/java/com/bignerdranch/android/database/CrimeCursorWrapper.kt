package com.bignerdranch.android.database

import android.database.Cursor
import android.database.CursorWrapper
import com.bignerdranch.android.Crime
import java.util.*

class CrimeCursorWrapper(cursor: Cursor): CursorWrapper(cursor) {
    fun getCrime(): Crime {
        val uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.UUID))
        val title = getString(getColumnIndexOrThrow(CrimeDbSchema.CrimeTable.TITLE))
        val date = getLong(getColumnIndex(CrimeDbSchema.CrimeTable.DATE))
        val isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.SOLVED))

        val crime = Crime(UUID.fromString(uuidString))
        crime.title = title
        crime.date = Date(date)
        crime.solved = isSolved != 0

        return crime
    }
}