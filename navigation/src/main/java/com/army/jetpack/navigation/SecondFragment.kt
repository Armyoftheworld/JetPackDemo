package com.army.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * @author daijun
 * @date 2020/2/21
 * @description
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            title.text = "从上个页面传过来的数据：${FirstFragmentArgs.fromBundle(arguments!!).title}"
        }

        btn_go_to_third.setOnClickListener {
            //            val bundle = Bundle().apply {
//                putString("title", "我是SecondFragment传过来的")
//            }
            val bundle = SecondFragmentArgs("我是SecondFragment传过来的, ${currentTime()}").toBundle()
            Navigation.findNavController(it)
                .navigate(R.id.action_secondFragment_to_thirdFragment, bundle)
        }
    }
}