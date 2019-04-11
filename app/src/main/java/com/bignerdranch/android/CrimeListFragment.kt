package com.bignerdranch.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var crimeAdapter: CrimeAdapter? = null
    var subtitleVisible: Boolean = false

    private class CrimeHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.list_item_crime, parent, false)
    ), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            v?.let {
                val intent = CrimePagerActivity.newIntent(it.context, crime.uuid)
                it.context.startActivity(intent)
            }
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

    private class CrimeAdapter(val crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
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

        savedInstanceState?.let {
            subtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE)
        }

        updateUI()
        return v
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, subtitleVisible)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_crime_list, menu)

        val subtitleItem = menu?.findItem(R.id.show_subtitle)
        if (subtitleVisible == true) {
            subtitleItem?.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem?.setTitle(R.string.show_subtitle)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.new_crime -> {
                val crime = Crime()
                CrimeLab.crimes.add(crime)
                context?.let {
                    val intent = CrimePagerActivity.newIntent(context!!, crime.uuid)
                    startActivity(intent)
                }
                return true
            }
            R.id.show_subtitle -> {
                subtitleVisible = subtitleVisible?.not()
                activity?.invalidateOptionsMenu()
                updateSubtitle()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI() {
        val crimes = CrimeLab.crimes

        if (crimeAdapter == null) {
            crimeAdapter = CrimeAdapter(crimes)
            crimeRecyclerView.adapter = crimeAdapter
        } else {
            crimeAdapter?.notifyDataSetChanged()
        }

        updateSubtitle()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateSubtitle() {
        val crimeCount = CrimeLab.crimes.size
        var subtitle: String? = getString(R.string.subtitle_format, crimeCount)

        if (!subtitleVisible) {
            subtitle = null
        }

        val activity = activity as AppCompatActivity

        activity.supportActionBar?.subtitle = subtitle
    }

    companion object {
        const val SAVED_SUBTITLE_VISIBLE: String = "subtitle"
    }
}