package org.maktab.taskmanager.view

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

class TaskListActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return TaskListFragment.newInstance(intent.getStringExtra(EXTRA_USERNAME).toString(),
            intent.getStringExtra(EXTRA_PASSWORD).toString())
    }

    companion object {
        const val EXTRA_USERNAME =
            "org.maktab.homework11_maktab37.controller.activity.extra_username"
        const val EXTRA_PASSWORD =
            "org.maktab.homework11_maktab37.controller.activity.extra_password"

        fun newIntent(context: Context?, username: String, password: String): Intent {

            val intent = Intent(context, TaskListActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, username)
            intent.putExtra(EXTRA_PASSWORD, password)
            return intent
        }
    }
}