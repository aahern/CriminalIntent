package com.bignerdranch.android

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.widget.DatePicker
import java.util.*

class DatePickerFragment : DialogFragment() {
    lateinit var datePicker: DatePicker


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date: Date = arguments?.getSerializable(ARG_DATE) as Date

        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val v = LayoutInflater.from(activity)
            .inflate(R.layout.dialog_date, null)

        datePicker = v.findViewById(R.id.dialog_date_picker)
        datePicker.init(year, month, day, null)


        return AlertDialog.Builder(activity)
            .setView(v)
            .setTitle(R.string.date_picker_title)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                sendResult(
                    Activity.RESULT_OK,
                    GregorianCalendar(datePicker.year, datePicker.month, datePicker.dayOfMonth).time
                )
            }
            .create()
    }

    fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)

        targetFragment?.onActivityResult(targetRequestCode, resultCode, intent)
    }

    companion object {
        const val EXTRA_DATE: String = "com.bignerdranch.android.criminalintent.date"
        const val ARG_DATE: String = "date"

        fun newInstance(date: Date?): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)

            val fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}