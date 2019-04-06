package com.bignerdranch.android

import android.content.Intent
import android.support.v4.app.Fragment

class CrimeListActivity: SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return CrimeListFragment()
    }
}