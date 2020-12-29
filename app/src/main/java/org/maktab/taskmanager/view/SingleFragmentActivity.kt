package org.maktab.taskmanager.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.maktab.taskmanager.R

abstract class SingleFragmentActivity : AppCompatActivity() {

    abstract fun createFragment(): Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val fragmentManager = supportFragmentManager

        //check if fragment exists in container (configuration changes save the fragments)
        val fragment = fragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, createFragment())
                .commit()
        }
    }
}