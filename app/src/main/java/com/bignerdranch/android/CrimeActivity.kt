package com.bignerdranch.android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import java.util.*

class CrimeActivity : SingleFragmentActivity() {
    companion object {
        private const val EXTRA_CRIME_ID: String = "com.bignerdranch.android.criminalintent.crime_id"
        private const val EXTRA_CRIME_POSITION = "com.bignerdranch.android.criminalintent.crime_position"

        fun newIntent(packageContext: Context, crimeId: UUID, position: Int): Intent{
            val intent = Intent(packageContext, CrimeActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            intent.putExtra(EXTRA_CRIME_POSITION, position)
            return intent
        }
    }

    override fun createFragment(): Fragment {
        val crimeID: UUID = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        val crimePosition: Int = intent.getSerializableExtra(EXTRA_CRIME_POSITION) as Int
        return CrimeFragment.newInstance(crimeID, crimePosition)
    }


}

inline fun <reified T: Activity> Activity.myStartActivityForResult(requestCode: Int) {
    val intent = Intent(this, T::class.java)
    startActivityForResult(intent, requestCode)
}

