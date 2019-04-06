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
        }
    }

    fun getCrime(id: UUID): Crime? {
        crimes.forEach {
            if (it.uuid == id){
                return it
            }
        }
        return null
    }
}