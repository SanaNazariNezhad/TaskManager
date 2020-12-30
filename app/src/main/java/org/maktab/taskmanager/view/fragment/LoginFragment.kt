package org.maktab.taskmanager.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.maktab.taskmanager.R
import org.maktab.taskmanager.data.model.User
import org.maktab.taskmanager.data.repository.UserDBRepository
import org.maktab.taskmanager.databinding.FragmentLoginBinding
import org.maktab.taskmanager.view.activity.SignUpActivity
import org.maktab.taskmanager.view.activity.TaskListActivity
import java.util.*




class LoginFragment : Fragment() {

    private var user: String? = null
    private var pass: String? = null
    val REQUEST_CODE_SIGN_UP = 0
    val BUNDLE_KEY_USERNAME = "UserBundle"
    val BUNDLE_KEY_PASSWORD = "passBundle"
    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var mUserRepository: UserDBRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mUserRepository = UserDBRepository.getInstance(activity!!)!!
            if (savedInstanceState != null) {
                user = savedInstanceState.getString(BUNDLE_KEY_USERNAME)
                pass = savedInstanceState.getString(BUNDLE_KEY_PASSWORD)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        fragmentLoginBinding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login, container, false
        )



        listeners()
        return fragmentLoginBinding.root;
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun listeners() {
        fragmentLoginBinding.btnLoginLogin.setOnClickListener(View.OnClickListener {
            fragmentLoginBinding.usernameFormLogin.setErrorEnabled(false)
            fragmentLoginBinding.passwordFormLogin.setErrorEnabled(false)
            if (validateInput()) {

                val intent: Intent = TaskListActivity.newIntent(
                    activity,
                    fragmentLoginBinding.usernameLogin.getText().toString(),
                    fragmentLoginBinding.passwordLogin.getText().toString()
                )
                startActivity(intent)
            }
        })
        fragmentLoginBinding.btnSignUpLogin.setOnClickListener(View.OnClickListener {
            val intent: Intent = SignUpActivity.newIntent(
                activity,
                fragmentLoginBinding.usernameLogin.text.toString(),
                fragmentLoginBinding.passwordLogin.text.toString()
            )
            startActivityForResult(intent, REQUEST_CODE_SIGN_UP)
        })
        /*fragmentLoginBinding.btnAdminLogin.setOnClickListener(View.OnClickListener {
            if (checkAdmin()) {
                val intent: Intent = AdminListActivity.newIntent(activity)
                startActivity(intent)
            }
        })*/
    }

    private fun checkAdmin(): Boolean {
        return if (fragmentLoginBinding.usernameLogin.text.toString()
                .equals("Admin", ignoreCase = true) &&
            fragmentLoginBinding.passwordLogin.text.toString() == "1234"
        ) {
            true
        } else {
            callToast(R.string.toast_admin)
            false
        }
    }

    private fun validateInput(): Boolean {
        val user: User = mUserRepository.getUser(
            Objects.requireNonNull(fragmentLoginBinding.usernameLogin.text).toString(),
            fragmentLoginBinding.passwordLogin.text.toString()
        )
        if (fragmentLoginBinding.usernameLogin.text.toString().trim { it <= ' ' }.isEmpty()
            && fragmentLoginBinding.passwordLogin.text.toString().trim { it <= ' ' }.isEmpty()
        ) {
            fragmentLoginBinding.usernameFormLogin.setErrorEnabled(true)
            fragmentLoginBinding.usernameFormLogin.setError("Field cannot be empty!")
            fragmentLoginBinding.passwordFormLogin.setErrorEnabled(true)
            fragmentLoginBinding.passwordFormLogin.setError("Field cannot be empty!")
            return false
        } else if (fragmentLoginBinding.usernameLogin.text.toString().trim { it <= ' ' }.isEmpty()) {
            fragmentLoginBinding.usernameFormLogin.setErrorEnabled(true)
            fragmentLoginBinding.usernameFormLogin.setError("Field cannot be empty!")
            return false
        } else if (fragmentLoginBinding.passwordLogin.text.toString().trim { it <= ' ' }.isEmpty()) {
            fragmentLoginBinding.passwordFormLogin.setErrorEnabled(true)
            fragmentLoginBinding.passwordFormLogin.setError("Field cannot be empty!")
            return false
        }
        val inputUsername: String? = user.getUsername()
        val inputPassword: String? = user.getPassword()
        if (fragmentLoginBinding.usernameLogin.text.toString() != inputUsername ||
            fragmentLoginBinding.passwordLogin.text.toString() != inputPassword
        ) {
            callToast(R.string.toast_login)
            return false
        }
        fragmentLoginBinding.usernameFormLogin.setErrorEnabled(false)
        fragmentLoginBinding.passwordFormLogin.setErrorEnabled(false)
        return true
    }

    private fun callToast(stringId: Int) {
        val toast = Toast.makeText(activity, stringId, Toast.LENGTH_SHORT)
        toast.show()
    }
}