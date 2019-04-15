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
    lateinit var titleField: EditText
    lateinit var dateButton: Button
    lateinit var solvedCheckBox: CheckBox

    companion object {
        const val ARG_CRIME_ID: String = "crime_id"
        const val DIALOG_DATE: String = "DialogDate"

        const val REQUEST_DATE: Int = 0

        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crime = CrimeLab.getInstance(this.context!!.applicationContext).getCrime(crimeId)
    }

    override fun onPause() {
        super.onPause()

        CrimeLab.getInstance(context?.applicationContext!!).updateCrime(crime!!)
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
        updateDate()
        dateButton.setOnClickListener {
            val manager = fragmentManager

            val dialog = DatePickerFragment.newInstance(crime?.date)

            dialog.setTargetFragment(this@CrimeFragment, REQUEST_DATE)

            dialog.show(manager, DIALOG_DATE)
        }

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
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_DATE) {
            val date = data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            crime?.date = date
            updateDate()
        }
    }

    private fun updateDate() {
        dateButton.text = crime?.date.toString()
    }
}

