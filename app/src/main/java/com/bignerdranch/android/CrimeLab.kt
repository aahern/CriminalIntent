package com.bignerdranch.android

import java.util.*

object CrimeLab {
    val crimes: MutableList<Crime> = mutableListOf()
    init {
        for (i in 0..99){
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.solved = i%2==0
            crimes.add(crime)
            crime.requiresPolice = i % 5 == 0
        }
    }

    fun getCrime(id: UUID): Crime? {
        crimes.forEach {
            return if (it.uuid == id) {
                it
            } else {
                null
            }
        }
        return null
    }
}