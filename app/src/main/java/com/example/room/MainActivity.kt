package com.example.room

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

// https://github.com/googlecodelabs/android-persistence
// https://codelabs.developers.google.com/codelabs/android-persistence/#0
class MainActivity : AppCompatActivity() {
    private var mShowUserViewModel: CustomResultViewModel? = null

    private var mBooksTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mBooksTextView = findViewById(R.id.books_tv)

        mShowUserViewModel = ViewModelProviders.of(this).get(CustomResultViewModel::class.java)

        populateDb()

        subscribeUiLoans()
    }

    private fun populateDb() {
        mShowUserViewModel!!.createDb()
    }

    private fun subscribeUiLoans() {
        mShowUserViewModel!!.loansResult?.observe(this, Observer { result -> mBooksTextView!!.text = result })
    }

    fun onRefreshBtClicked(view: View) {
        populateDb()
        subscribeUiLoans()
    }

}
