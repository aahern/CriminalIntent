package com.bignerdranch.android

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var crimeAdapter: CrimeAdapter? = null

    companion object {
        const val REQUEST_CRIME: Int = 1
    }

    inner class CrimeHolder(
        inflater: LayoutInflater,
        private val parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.list_item_crime, parent, false)
    ), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent = CrimeActivity.newIntent(parent.context, crime.uuid, adapterPosition)
            startActivityForResult(intent, REQUEST_CRIME)
        }

        lateinit var crime: Crime
        val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = crime.title
            dateTextView.text = crime.date.toString()
            solvedImageView.visibility = if (crime.solved) View.VISIBLE else View.GONE
        }
    }


    private inner class CrimeAdapter(val crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return CrimeHolder(layoutInflater, parent)
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CRIME && data != null) {
            val pos = data.getIntExtra(CrimeFragment.RESULT_CRIME_POSITION, 0)
            updateUI(pos)
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

    private fun updateUI(position: Int) {
        crimeAdapter?.notifyItemChanged(position)
    }

    private fun updateUI() {
        val crimes = CrimeLab.crimes

        if (crimeAdapter == null) {
            crimeAdapter = CrimeAdapter(crimes)
            crimeRecyclerView.adapter = crimeAdapter
        } else {
            crimeAdapter?.notifyDataSetChanged()
        }
    }
}

