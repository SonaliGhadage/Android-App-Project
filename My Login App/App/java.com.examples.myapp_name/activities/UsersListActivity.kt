package com.example.myloginapplication.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.example.myloginapplication.R
import com.example.myloginapplication.adapters.UsersRecyclerAdapter
import com.example.myloginapplication.model.User
import com.example.myloginapplication.sql.DatabaseHelper


class UsersListActivity : AppCompatActivity(), View.OnClickListener {

    private val activity = this@UsersListActivity
    private lateinit var textViewName: AppCompatTextView
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var listUsers: MutableList<User>
    private lateinit var usersRecyclerAdapter: UsersRecyclerAdapter
    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var buttonLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
        supportActionBar!!.title = ""
        initViews()
        initObjects()
        initListeners()
    }

    /**
     * This method is to initialize views
     */
    private fun initViews() {
        textViewName = findViewById<AppCompatTextView>(R.id.textViewName)
        recyclerViewUsers = findViewById<RecyclerView>(R.id.recyclerViewUsers)
        buttonLogout = findViewById<Button>(R.id.btnLogout)
    }

    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {
        listUsers = ArrayList()
        usersRecyclerAdapter = UsersRecyclerAdapter(listUsers)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager = mLayoutManager
        recyclerViewUsers.itemAnimator = DefaultItemAnimator()
        recyclerViewUsers.setHasFixedSize(true)
        recyclerViewUsers.adapter = usersRecyclerAdapter
        databaseHelper = DatabaseHelper(activity)

        val emailFromIntent = intent.getStringExtra("EMAIL")
        textViewName.text = emailFromIntent

        var getDataFromSQLite = GetDataFromSQLite()
        getDataFromSQLite.execute()
    }

    private fun initListeners() {

        buttonLogout!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogout ->
            {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

        /**
     * This class is to fetch all user records from SQLite
     */
    inner class GetDataFromSQLite : AsyncTask<Void, Void, List<User>>() {

        override fun doInBackground(vararg p0: Void?): List<User> {
            return databaseHelper.getAllUser()
        }

        override fun onPostExecute(result: List<User>) {
            super.onPostExecute(result)
            listUsers.clear()
            listUsers.addAll(result!!)
        }

    }
}