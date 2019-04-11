package com.bignerdranch.android

import java.util.*

object CrimeLab {
    val crimes: MutableList<Crime> = mutableListOf()

    fun getCrime(id: UUID): Crime? {
        crimes.forEach {
            if (it.uuid == id){
                return it
            }
        }
        return null
    }

    fun addCrime(c: Crime){
        crimes.add(c)
    }
}