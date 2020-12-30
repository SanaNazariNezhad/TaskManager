package org.maktab.taskmanager.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.maktab.taskmanager.R

private const val ARG_Username = "username";
private const val ARG_Password = "password";

class TaskListFragment : Fragment() {
    private var username: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_Username)
            password = it.getString(ARG_Password)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String, password: String) =
            TaskListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_Username, username)
                    putString(ARG_Password, password)
                }
            }
    }
}