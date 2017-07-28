package com.mudio.movies.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mudio.movies.ActivityCreated
import kotlinx.android.synthetic.main.main_activity.*
import com.mudio.movies.Adapters.PagerAdapter
import com.mudio.movies.R

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(Bundle())
        setContentView(R.layout.main_activity)

        viewPager.adapter= PagerAdapter(supportFragmentManager)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        ActivityCreated.instance.mainActivityCreated = true
        recreate()
    }

}
