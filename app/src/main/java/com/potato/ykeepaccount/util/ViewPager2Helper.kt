package com.potato.ykeepaccount.util

import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.MagicIndicator

fun viewPager2Helper(indicator: MagicIndicator, viewPager2: ViewPager2){
    viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            indicator.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            indicator.onPageSelected(position)
        }
    })
}

