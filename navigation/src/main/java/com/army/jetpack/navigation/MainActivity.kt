package com.army.jetpack.navigation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToSecondActivity(view: View) {
        val navController = NavController(this)
        val navigatorProvider = navController.navigatorProvider
        val navGraph = NavGraph(NavGraphNavigator(navigatorProvider))
        val activityNavigator = navigatorProvider.getNavigator(ActivityNavigator::class.java)
        val destination = activityNavigator.createDestination()
        // id可以随便一个资源id
        destination.id = R.id.nav_controller_view_tag
        destination.setComponentName(
            ComponentName(
                this,
                "com.army.jetpack.navigation.SecondActivity"
            )
        )
        navGraph.addDestination(destination)
        navGraph.startDestination = destination.id
        navController.graph = navGraph
    }

    fun showDeeplinkNotification(view: View) {
        val bundle = Bundle()
        bundle.putString("userName", "大海")
        // 两种方法来创建一个深层链接的PendingIntent
//        val pendingIntent = NavDeepLinkBuilder(this)
//            .setArguments(bundle)
//            .setGraph(R.navigation.nav_graph)
//            .setDestination(R.id.thirdFragment)
//            .createPendingIntent()
        val pendingIntent =
            Navigation.findNavController(this, R.id.fragment).createDeepLink()
                .setDestination(R.id.thirdFragment)
                .setArguments(bundle)
                .createPendingIntent()
        val notificationManager = NotificationManagerCompat.from(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "deeplink",
                    "deepLinkName",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        val builder = NotificationCompat.Builder(this, "deeplink")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("测试deepLink")
            .setContentText("哈哈哈")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationManager.notify(999, builder.build())
    }

    fun goToBottomNavigationActivity(view: View) {
        startActivity(Intent(this, BottomNavActivity::class.java))
    }
}
