package com.bignerdranch.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import java.util.*

class CrimePagerActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    lateinit var firstButton: Button
    lateinit var lastButton: Button
    lateinit var crimes: List<Crime>

    companion object {
        const val EXTRA_CRIME_ID: String = "com.bignerdranch.android.criminalintent.crime_id"

        fun newIntent(packageContext: Context, crimeId: UUID): Intent {
            val intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID

        viewPager = findViewById(R.id.crime_view_pager)
        firstButton = findViewById(R.id.first_button)
        lastButton = findViewById(R.id.last_button)

        lastButton.setOnClickListener { viewPager.currentItem = crimes.lastIndex }
        firstButton.setOnClickListener { viewPager.currentItem = 0 }

        crimes = CrimeLab.crimes
        val fragmentManager = supportFragmentManager

        viewPager.adapter = object : FragmentStatePagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment {
                val crime = crimes[position]
                return CrimeFragment.newInstance(crime.uuid)
            }

            override fun getCount(): Int {
                return crimes.size
            }
        }

        for (i in 0..crimes.size) {
            if (crimes[i].uuid == crimeId) {
                viewPager.currentItem = i
                break
            }
        }

        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                when (viewPager.currentItem) {
                    0 -> {
                        firstButton.isEnabled = false
                        lastButton.isEnabled = true
                    }
                    crimes.lastIndex -> {
                        firstButton.isEnabled = true
                        lastButton.isEnabled = false
                    }
                    else -> {
                        firstButton.isEnabled = true
                        lastButton.isEnabled = true
                    }
                }
            }

            override fun onPageSelected(p0: Int) {

            }

        })
    }
}