package com.army.jetpack.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_bottom_nav.*

class BottomNavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        val navController = Navigation.findNavController(this, R.id.bottom_fragment)
        // 底部导航的配置
        // menu里的item的id必须要和graph里fragment的id一样，并且id要是唯一的，之前其他布局里面没有定义过
        // 需要有actionBar，可以在style里面把actionBarSize的设置成1px，相当于隐藏actionBar
        // 或者考虑用toolbar，toolbar里面放一个空布局（这只是个想法，并没有验证是否可以）
        val configuration = AppBarConfiguration.Builder(bottom_nav.menu).build()
        // 关联控制器和底部导航的配置
        NavigationUI.setupActionBarWithNavController(this, navController, configuration)
        // 关联底部BottomNavigationView和控制器
        NavigationUI.setupWithNavController(bottom_nav, navController)
    }
}
