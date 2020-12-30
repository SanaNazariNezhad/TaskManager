package org.maktab.taskmanager.view.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import org.maktab.taskmanager.R
import java.util.*

private const val ARG_DATE_PICKER = "datePicker"
const val EXTRA_USER_SELECTED_DATE = "org.maktab.taskmanager.userSelectedDate"

class DatePickerFragment : DialogFragment() {
    private lateinit var taskDate: Date
    private lateinit var mCalendar: Calendar
    private lateinit var mDatePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskDate = it.getSerializable(ARG_DATE_PICKER) as Date
        }
        mCalendar = Calendar.getInstance()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(activity)
        val view = inflater.inflate(R.layout.fragment_date_picker, null)
        findViews(view)
        initViews()
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            .setTitle(R.string.date_picker_title)
            .setIcon(R.drawable.ic_planner)
            .setView(view)
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    extractDateFromDatePicker()
                    sendResult(mCalendar)
                })
            .setNegativeButton(android.R.string.cancel, null)
        return builder.create()
    }

    private fun findViews(view: View?) {
        if (view != null) {
            mDatePicker = view.findViewById(R.id.date_picker_task)
        }
    }

    private fun initViews() {
        initDatePicker()
    }

    private fun initDatePicker() {
        // i have a date and i want to set it in date picker.
        mCalendar.time = taskDate
        val year: Int = mCalendar.get(Calendar.YEAR)
        val monthOfYear: Int = mCalendar.get(Calendar.MONTH)
        val dayOfMonth: Int = mCalendar.get(Calendar.DAY_OF_MONTH)
        mDatePicker.init(year, monthOfYear, dayOfMonth, null)
    }

    private fun extractDateFromDatePicker() {
        val year: Int = mDatePicker.year
        val month: Int = mDatePicker.month
        val dayOfMonth: Int = mDatePicker.dayOfMonth
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    }

    private fun sendResult(userSelectedDate: Calendar) {
        val fragment = targetFragment
        val requestCode = targetRequestCode
        val resultCode = Activity.RESULT_OK
        val intent = Intent()
        intent.putExtra(EXTRA_USER_SELECTED_DATE, userSelectedDate)
        fragment!!.onActivityResult(requestCode, resultCode, intent)
    }


    companion object {
        @JvmStatic
        fun newInstance(taskDate: Date) =
            DatePickerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_DATE_PICKER, taskDate)
                }
            }
    }
}