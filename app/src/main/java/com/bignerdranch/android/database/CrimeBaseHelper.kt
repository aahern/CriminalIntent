package com.bignerdranch.android.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bignerdranch.android.database.CrimeDbSchema.CrimeTable.Cols

class CrimeBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    private companion object {
        const val VERSION = 1
        const val DATABASE_NAME = "crimeBase.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "create table " + CrimeDbSchema.CrimeTable.NAME
                    + "(" + "_id integer primary key autoincrement, " +
                    Cols.UUID + ", " +
                    Cols.TITLE + ", " +
                    Cols.DATE + ", " +
                    Cols.SOLVED + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}