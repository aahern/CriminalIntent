package com.bignerdranch.android

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.bignerdranch.android.database.CrimeBaseHelper
import com.bignerdranch.android.database.CrimeCursorWrapper
import com.bignerdranch.android.database.CrimeDbSchema
import java.util.*

class CrimeLab private constructor(val context: Context) {
    val db: SQLiteDatabase = CrimeBaseHelper(context).writableDatabase

    fun getCrimes(): List<Crime>{
        val crimes = mutableListOf<Crime>()

        val cursor = queryCrimes(null, null)

        cursor.use { cursor ->
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                crimes.add(cursor.getCrime())
                cursor.moveToNext()
            }
        }

        return crimes
    }

    fun getCrime(id: UUID): Crime? {
        val cursor = queryCrimes(
            CrimeDbSchema.CrimeTable.UUID + " = ?",
            arrayOf(id.toString())
        )


        cursor.use { cursor ->
            if (cursor.count == 0){
                return null
            }
            cursor.moveToFirst()
            return cursor.getCrime()
        }
    }

    fun addCrime(c: Crime) {
        val values = getContentValues(c)

        db.insert(CrimeDbSchema.CrimeTable.NAME, null, values)
    }

    fun updateCrime(crime: Crime) {
        val uuidString = crime.uuid.toString()
        val values = getContentValues(crime)

        db.update(
            CrimeDbSchema.CrimeTable.NAME, values,
            CrimeDbSchema.CrimeTable.UUID + " = ?",
            arrayOf(uuidString)
        )
    }

    fun queryCrimes(whereClause: String?, whereArgs: Array<String>?): CrimeCursorWrapper {
        val cursor = db.query(
            CrimeDbSchema.CrimeTable.NAME,
            null,
            whereClause,
            whereArgs,
            null,
            null,
            null
        )
        return CrimeCursorWrapper(cursor)
    }

    companion object : SingletonHolder<CrimeLab, Context>(::CrimeLab) {
        fun getContentValues(crime: Crime): ContentValues {
            val values = ContentValues()
            values.put(CrimeDbSchema.CrimeTable.UUID, crime.uuid.toString())
            values.put(CrimeDbSchema.CrimeTable.TITLE, crime.title)
            values.put(CrimeDbSchema.CrimeTable.DATE, crime.date.time)
            values.put(CrimeDbSchema.CrimeTable.SOLVED, if (crime.solved) 1 else 0)

            return values
        }
    }
}
