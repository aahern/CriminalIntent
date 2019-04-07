package com.bignerdranch.android

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.widget.TimePicker

class TimePickerFragment : DialogFragment() {
    lateinit var timePicker: TimePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val hour: Int = arguments?.getSerializable(ARG_HOUR) as Int
        val minute: Int = arguments?.getSerializable(ARG_MINUTE) as Int

        val v = LayoutInflater.from(activity)
            .inflate(R.layout.dialog_time, null)

        timePicker = v.findViewById(R.id.dialog_time_picker)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour = hour
            timePicker.minute = minute
        } else {
            timePicker.currentHour = hour
            timePicker.currentMinute = minute
        }

        return AlertDialog.Builder(activity)
            .setView(v)
            .setTitle(R.string.time_picker_title)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    sendResult(
                        Activity.RESULT_OK,
                        timePicker.hour,
                        timePicker.minute
                    )
                } else {
                    sendResult(
                        Activity.RESULT_OK,
                        timePicker.currentHour,
                        timePicker.currentMinute
                    )
                }
            }.create()
    }

    fun sendResult(resultCode: Int, hour: Int, minute: Int) {
        if (targetFragment == null) {
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_HOUR, hour)
        intent.putExtra(EXTRA_MINUTE, minute)

        targetFragment?.onActivityResult(targetRequestCode, resultCode, intent)
    }

    companion object {
        const val EXTRA_HOUR: String = "com.bignerdranch.android.criminalintent.hour"
        const val EXTRA_MINUTE: String = "com.bignerdranch.android.criminalintent.minute"
        const val ARG_HOUR: String = "hour"
        const val ARG_MINUTE: String = "minute"

        fun newInstance(hour: Int, minute: Int): TimePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_HOUR, hour)
            args.putSerializable(ARG_MINUTE, minute)

            val fragment = TimePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}