package com.bignerdranch.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.util.*

class CrimeFragment : Fragment() {
    var crime: Crime? = null
    private var position: Int? = null

    lateinit var titleField: EditText
    lateinit var dateButton: Button
    lateinit var solvedCheckBox: CheckBox

    companion object {
        const val ARG_CRIME_ID: String = "crime_id"
        const val ARG_CRIME_POSITION: String = "crime_position"
        const val RESULT_CRIME_POSITION: String =
            "com.bignerdranch.criminalintent.result_crime.position"

        fun newInstance(crimeId: UUID, crimePosition: Int): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)
            args.putSerializable(ARG_CRIME_POSITION, crimePosition)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        position = arguments?.getSerializable(ARG_CRIME_POSITION) as Int
        crime = CrimeLab.getCrime(crimeId)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_crime, container, false)
        solvedCheckBox = v?.findViewById(R.id.crime_solved) as CheckBox
        solvedCheckBox.isChecked = crime?.solved ?: false

        solvedCheckBox.setOnCheckedChangeListener { _, isChecked -> crime?.solved = isChecked }

        dateButton = v.findViewById(R.id.crime_date) as Button
        dateButton.text = crime?.date.toString()
        dateButton.isEnabled = false

        titleField = v.findViewById(R.id.crime_title) as EditText
        titleField.setText(crime?.title)

        titleField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crime?.title = s.toString()
            }

        })
        position?.let {
            returnResult(it)
        }

        return v
    }

    private fun returnResult(position: Int) {
        activity?.setResult(Activity.RESULT_OK, Intent().putExtra(RESULT_CRIME_POSITION, position))
    }
}

