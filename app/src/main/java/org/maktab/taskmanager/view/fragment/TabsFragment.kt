package org.maktab.taskmanager.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import org.maktab.taskmanager.R
import org.maktab.taskmanager.data.model.Task
import org.maktab.taskmanager.data.model.User
import org.maktab.taskmanager.data.repository.IRepository
import org.maktab.taskmanager.data.repository.IUserRepository
import org.maktab.taskmanager.data.repository.TaskDBRepository
import org.maktab.taskmanager.data.repository.UserDBRepository
import org.maktab.taskmanager.databinding.FragmentTabsBinding
import org.maktab.taskmanager.databinding.TaskRowListBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_Username = "username";
private const val ARG_Password = "password";
private const val ARG_STATE = "state";

class TabsFragment : Fragment() {
    private var username: String? = null
    private var password: String? = null
    private var state: String? = null
    private lateinit var fragmentTabsBinding: FragmentTabsBinding
    private var mAdapter: TabsAdapter? = null
    private var mRepository: IRepository? = null
    private var mIUserRepository: IUserRepository? = null
    private var mTasks: List<Task>? = null
    private var isVisible: Boolean? = false
    private var mUser: User? = null
    var mActivity: FragmentActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_Username)
            password = it.getString(ARG_Password)
            state = it.getString(ARG_STATE)
        }
        mActivity = activity as FragmentActivity?
        mRepository = activity?.let { TaskDBRepository.getInstance(it) }
        mIUserRepository = activity?.let { UserDBRepository.getInstance(it) }
        mUser = mIUserRepository?.getUser(
            Objects.requireNonNull(username).toString(),
            password.toString()
        )
    }

    override fun onPause() {
        super.onPause()
        updateUI()
        fragmentTabsBinding.fam.collapse()
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint && !isVisible!!) {
            //your code
            updateUI()
        }
        isVisible = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isVisible!!) {
            val handler = Handler()
            handler.postDelayed({ //your code
                updateUI()
            }, 500)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentTabsBinding = DataBindingUtil.inflate<FragmentTabsBinding>(
            inflater,
            R.layout.fragment_tabs, container, false
        )

        checkEmptyLayout()
        initViews()
        listeners()

        return fragmentTabsBinding.root
    }

    private fun initViews() {
        fragmentTabsBinding.recycler.layoutManager = LinearLayoutManager(activity)
        updateUI()
    }

    private fun listeners() {
        fragmentTabsBinding.fabInsert.setOnClickListener(View.OnClickListener {
            /*val insertTaskFragment: InsertTaskFragment =
                InsertTaskFragment.newInstance(mUsername, mPassword)
            insertTaskFragment.setTargetFragment(
                this@TabsFragment,
                TabsFragment.REQUEST_CODE_INSERT_TASK
            )
            insertTaskFragment.show(
                activity!!.supportFragmentManager,
                TabsFragment.FRAGMENT_TAG_INSERT_TASK
            )*/
        })
        fragmentTabsBinding.fabDelete.setOnClickListener(View.OnClickListener {
            /*val deleteAllFragment: DeleteAllFragment = DeleteAllFragment.newInstance()
            deleteAllFragment.setTargetFragment(
                this@TabsFragment,
                TabsFragment.REQUEST_CODE_DELETE_ALL_TASK
            )
            deleteAllFragment.show(
                activity!!.supportFragmentManager,
                TabsFragment.FRAGMENT_TAG_DELETE_ALL_TASK
            )*/
        })
        fragmentTabsBinding.fabLogOut.setOnClickListener(View.OnClickListener { activity!!.finish() })
    }

    private fun updateUI() {
        checkEmptyLayout()
        updateAdapter()
    }

    private fun updateAdapter() {
        if (mAdapter == null) {
            mAdapter = TabsAdapter(mTasks, mActivity!!)
            fragmentTabsBinding.recycler!!.adapter = mAdapter
        } else {
            mAdapter!!.setTasks(mTasks)
            mAdapter!!.notifyDataSetChanged()
        }
    }

    private fun checkEmptyLayout() {
        when {
            state.equals("todo", ignoreCase = true) ->
                mTasks = mRepository!!.getTodoTask(mUser!!.getPrimaryId())
            state.equals("doing", ignoreCase = true) ->
                mTasks = mRepository!!.getDoingTask(mUser!!.getPrimaryId())
            state.equals("done", ignoreCase = true) ->
                mTasks = mRepository!!.getDoneTask(mUser!!.getPrimaryId())
        }

        if (mTasks!!.isEmpty())
            fragmentTabsBinding.layoutEmpty.visibility = View.VISIBLE
        else fragmentTabsBinding.layoutEmpty.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String, password: String, state: String) =
            TabsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_Username, username)
                    putString(ARG_Password, password)
                    putString(ARG_STATE, state)
                }
            }
    }

    private class TabsHolder(binding: TaskRowListBinding) : RecyclerView.ViewHolder(binding.root) {

        private var mTask: Task? = null
        private var mBinding: TaskRowListBinding = binding

        fun bindTaskTabs(task: Task) {
            mTask = task
            mBinding.txtviewTitle.text = task.getTitle()
            val date = createDateFormat(task)
            mBinding.txtviewDate.text = date
            val color = Color.parseColor("#ff80aa")
            val string = task.getTitle()!!.substring(0, 1)
            val drawable: TextDrawable = TextDrawable.builder()
                .buildRound(string, color)
            mBinding.imageProfile.setImageDrawable(drawable)
        }

        private fun createDateFormat(task: Task): String {
            var totalDate = ""
            val dateFormat = dateFormat
            val date = dateFormat.format(task.getDate())
            val timeFormat = timeFormat
            val time = timeFormat.format(task.getDate())
            totalDate = "$date  $time"
            return totalDate
        }

        private val dateFormat: DateFormat
            private get() = SimpleDateFormat("MMM dd,yyyy")
        private val timeFormat: DateFormat
            private get() = SimpleDateFormat("h:mm a")

        init {
            /*itemView.setOnClickListener {
                val editTaskFragment: EditTaskFragment =
                    EditTaskFragment.newInstance(mTask!!.getId(), true)
                editTaskFragment.setTargetFragment(
                    this@TabsFragment,
                    TabsFragment.REQUEST_CODE_EDIT_TASK
                )
                editTaskFragment.show(
                    getActivity().getSupportFragmentManager(),
                    TabsFragment.FRAGMENT_TAG_EDIT_TASK
                )
            }*/
        }
    }

    private class TabsAdapter(var tasks: List<Task>?, var activity: FragmentActivity) :
        RecyclerView.Adapter<TabsHolder>() {

        private lateinit var binding: TaskRowListBinding
        private var mTasks: List<Task>? = tasks

        override fun getItemCount(): Int {
            return mTasks?.size!!
        }

        @JvmName("setTasks1")
        fun setTasks(tasksList: List<Task>?) {
            mTasks = tasksList
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabsHolder {
            val layoutInflater = LayoutInflater.from(activity)
            binding = DataBindingUtil.inflate<TaskRowListBinding>(
                layoutInflater,
                R.layout.task_row_list, parent, false
            )
            return TabsHolder(binding)
        }

        override fun onBindViewHolder(holder: TabsHolder, position: Int) {
            val task = tasks?.get(position)
            if (task != null) {
                holder.bindTaskTabs(task)
            }
        }
    }
}