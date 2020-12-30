package org.maktab.taskmanager.view.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.maktab.taskmanager.R
import org.maktab.taskmanager.view.fragment.LoginFragment

class LoginActivity : SingleFragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }

    override fun createFragment(): Fragment {
        return LoginFragment.newInstance();
    }
}