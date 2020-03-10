package com.army.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * @author daijun
 * @date 2020/2/21
 * @description
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_go_to_second.setOnClickListener {
            //            val bundle = Bundle().apply {
//                putString("title", "我是FirstFragment传过来的")
//            }
            // 两种方式跳转
//            val bundle =
//                FirstFragmentArgs("我是FirstFragment传过来的, ${currentTime()}").toBundle()
//            Navigation.findNavController(it)
//                .navigate(R.id.action_firstFragment_to_secondFragment, bundle)

            // 至于为什么至于First -> Second会生成FirstFragmentDirections.actionFirstFragmentToSecondFragment类似这样带参数的方法，
            // 猜测是只有第一个fragment才会生成，这个猜测并没有实践去证明
            val direction = FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                "我是FirstFragment传过来的, ${currentTime()}"
            )
            it.findNavController().navigate(direction)
        }
    }

}