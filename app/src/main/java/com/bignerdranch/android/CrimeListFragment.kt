package com.bignerdranch.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private lateinit var crimeAdapter: CrimeAdapter

    private class CrimeHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        val isSerious: Boolean
    ) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.list_item_crime, parent, false)
    ), View.OnClickListener {
        lateinit var crime: Crime
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        val policeButton: Button = itemView.findViewById(R.id.police)


        init {
            if (!isSerious) {
                itemView.setOnClickListener(this)
            }
            policeButton.setOnClickListener {
                Toast.makeText(
                    it.context,
                    "POLICE",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onClick(v: View?) {
            Toast.makeText(
                v?.context,
                crime.title + " clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }


        fun bind(crime: Crime) {
            this.crime = crime
            if (isSerious) {
                titleTextView.visibility = View.GONE
                dateTextView.visibility = View.GONE
                policeButton.visibility = View.VISIBLE
            } else {
                titleTextView.text = crime.title
                dateTextView.text = crime.date.toString()
            }
        }


    }

    private class CrimeAdapter(val crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            if (viewType == 1) {
                return CrimeHolder(layoutInflater, parent, true)
            }
            return CrimeHolder(layoutInflater, parent, false)
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemViewType(position: Int): Int {
            return if (crimes[position].requiresPolice) {
                1
            } else {
                0
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            R.layout.fragment_crime_list,
            container, false
        )
        crimeRecyclerView = v.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)

        updateUI()
        return v
    }

    private fun updateUI() {
        val crimes = CrimeLab.crimes

        crimeAdapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = crimeAdapter
    }
}