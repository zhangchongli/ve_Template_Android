package com.volcengine.mars.demo.immersed

import android.R.layout
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.SwitchCompat
import com.volcengine.mars.immersed.ImmersedActivity
import com.volcengine.mars.immersed.ImmersedStatusBarHelper
import com.volcengine.mars.immersed.ImmersedStatusBarHelper.ImmersedStatusBarConfig
import kotlinx.android.synthetic.main.activity_immersed_demo.list

/**
 * created by zhouchentao on 2020/3/30.
 */
class ImmersedDemoActivity : ImmersedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immersed_demo)
        addImmersedDemo()
    }

    override fun getImmersedStatusBarConfig(): ImmersedStatusBarHelper.ImmersedStatusBarConfig {
        if (config == null) {
            return super.getImmersedStatusBarConfig()
        } else {
            return config!!
        }
    }

    private fun getPositonForColor(color: Int): Int {
        STATUS_BAR_COLOR_RESOURCE.forEachIndexed { index, it ->
            if (it == color || resources.getColor(it) == color) {
                return index
            }
        }
        return 0
    }

    private fun simpleSwitch(onText: String, offText: String): SwitchCompat {
        val switch = SwitchCompat(this)
        switch.showText = true
        switch.textOn = onText
        switch.textOff = offText
        return switch
    }

    private fun addImmersedDemo() {
        supportActionBar?.hide()
        val statusBarColorSelector = AppCompatSpinner(this)
        statusBarColorSelector.adapter =
            ArrayAdapter(this, layout.simple_list_item_1, STATUS_BAR_COLORS)
        statusBarColorSelector.setSelection(if (config == null) {0} else {getPositonForColor(config!!.statusBarColor)})
        val statusBarTitle = TextView(this)
        statusBarTitle.text = "statusBarColor"
        val statusBarL = LinearLayout(this)
        statusBarL.orientation = LinearLayout.HORIZONTAL
        statusBarL.addView(statusBarTitle)
        statusBarL.addView(statusBarColorSelector)
        list.addView(statusBarL)
        val fitsSystemWindowsSwitch = simpleSwitch("fitsOn", "fitsOff")
        fitsSystemWindowsSwitch.isChecked = if (config == null) {true} else {config!!.isFitsSystemWindows}
        list.addView(fitsSystemWindowsSwitch)
        val lightSwitch = simpleSwitch("light", "night")
        lightSwitch.isChecked = if (config == null) {true} else {config!!.isUseLightStatusBar}
        list.addView(lightSwitch)
        val resetBtn = Button(this)
        resetBtn.text = "reset"
        resetBtn.setOnClickListener {
            immersedStatusBarHelper.setStatusBarColor(resources.getColor(STATUS_BAR_COLOR_RESOURCE[statusBarColorSelector.selectedItemPosition]))
            immersedStatusBarHelper.setFitsSystemWindows(fitsSystemWindowsSwitch.isChecked)
            immersedStatusBarHelper.setUseLightStatusBarInternal(lightSwitch.isChecked)
        }
        list.addView(resetBtn)
    }

    companion object {

        var config: ImmersedStatusBarConfig? = null

        val STATUS_BAR_COLORS = listOf(
            "status_bar_color_white",
            "status_bar_color_black",
            "status_bar_color_gallery",
            "status_bar_color_red",
            "status_bar_color_transparent",
            "status_bar_color_purple_200",
            "status_bar_color_purple_500",
            "status_bar_color_purple_700",
            "status_bar_color_teal_200",
            "status_bar_color_teal_700"
        )
        val STATUS_BAR_COLOR_RESOURCE = listOf(
            R.color.status_bar_color_white,
            R.color.status_bar_color_black,
            R.color.status_bar_color_gallery,
            R.color.status_bar_color_red,
            R.color.status_bar_color_transparent,
            R.color.status_bar_color_purple_200,
            R.color.status_bar_color_purple_500,
            R.color.status_bar_color_purple_700,
            R.color.status_bar_color_teal_200,
            R.color.status_bar_color_teal_700
        )
    }

}