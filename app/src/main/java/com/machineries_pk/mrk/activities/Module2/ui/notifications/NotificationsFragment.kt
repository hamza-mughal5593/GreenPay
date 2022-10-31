package com.machineries_pk.mrk.activities.Module2.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.machineries_pk.mrk.activities.Utils.ProgressBarAnimation
import com.machineries_pk.mrk.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var animation_progress_bar: ProgressBarAnimation? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root




        animation_progress_bar = ProgressBarAnimation(
            activity,
            binding.circularProgressbar,
            binding.tv,
            0f,
           100.0.toFloat()
        )
        animation_progress_bar?.duration = 4000
        binding.circularProgressbar.animation = animation_progress_bar


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}