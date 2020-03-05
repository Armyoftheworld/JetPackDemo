package com.army.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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
            val bundle =
                FirstFragmentArgs("我是FirstFragment传过来的, ${currentTime()}").toBundle()
            Navigation.findNavController(it)
                .navigate(R.id.action_firstFragment_to_secondFragment, bundle)
        }
    }

}