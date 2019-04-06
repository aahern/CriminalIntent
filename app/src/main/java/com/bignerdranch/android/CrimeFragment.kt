package com.bignerdranch.android

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
    lateinit var titleField: EditText
    lateinit var dateButton: Button
    lateinit var solvedCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId: UUID = activity?.intent?.getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID) as UUID
        crime = CrimeLab.getCrime(crimeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_crime, container, false)
        solvedCheckBox = v.findViewById(R.id.crime_solved) as CheckBox
        solvedCheckBox.isChecked = crime?.solved ?: false

        solvedCheckBox.setOnCheckedChangeListener { _, isChecked -> crime?.solved = isChecked }

        dateButton = v.findViewById(R.id.crime_date) as Button
        dateButton.text = crime?.date.toString()
        dateButton.isEnabled = false

        titleField = v.findViewById(R.id.crime_title) as EditText
        titleField.setText(crime?.title)

        titleField.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crime?.title = s.toString()
            }

        })
        return v
    }
}

