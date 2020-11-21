package vnd.macro.sot.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pop_up.*
import vnd.macro.sot.R


class PopUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up)

        val selectedText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)

        val adapter = PopUpViewPagerAdapter(supportFragmentManager, selectedText.toString())
        vp.adapter = adapter

        changeTab(0)
        changeTab(1)

        tv_search.setOnClickListener {
            changeTab(0)
        }
        tv_select.setOnClickListener {
            changeTab(1)
        }

    }

    private fun changeTab(tabOrder: Int) {
        if (tabOrder == 0) {
            tv_select.setBackgroundResource(R.drawable.white_thick_border_rectangle)
            tv_search.setBackgroundResource(R.drawable.yellow_rectangle)
            vp.currentItem = 0
        } else {
            tv_search.setBackgroundResource(R.drawable.yellow_thick_border_rectangle)
            tv_select.setBackgroundResource(R.drawable.white_rectangle)
            vp.currentItem = 1
        }
    }
}