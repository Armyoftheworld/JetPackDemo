package com.army.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_third.*

/**
 * @author daijun
 * @date 2020/2/21
 * @description
 */
class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            title.text = "从上个页面传过来的数据：${arguments}"
        }

        btn_back_to_first.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_thirdFragment_to_firstFragment)
        }

        btn_back_to_second.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_thirdFragment_to_secondFragment)
        }
    }
}