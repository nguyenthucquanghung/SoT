package vnd.macro.sot.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_pop_up.*
import vnd.macro.sot.R


class PopUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up)

        val selectedText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)

        val adapter = PopUpViewPagerAdapter(supportFragmentManager, selectedText.toString())
        vp.adapter = adapter

        tv_search.setOnClickListener { vp.currentItem = 0 }
        tv_select.setOnClickListener { vp.currentItem = 1 }
        vp.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) { changeButtonState(position) }
            override fun onPageScrollStateChanged(state: Int) {}
        })

        initUI()
    }

    private fun initUI() {
        vp.currentItem = 1
        changeButtonState(0)
        changeButtonState(1)
    }

    private fun changeButtonState(tabOrder: Int) {
        if (tabOrder == 0) {
            tv_select.setBackgroundResource(R.drawable.white_thick_border_rectangle)
            tv_search.setBackgroundResource(R.drawable.yellow_rectangle)

        } else {
            tv_search.setBackgroundResource(R.drawable.yellow_thick_border_rectangle)
            tv_select.setBackgroundResource(R.drawable.white_rectangle)

        }
    }
}