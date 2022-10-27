package com.machineries_pk.mrk.activities.Module2.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.Module2.Adapters.ChooseActionAdapter
import com.machineries_pk.mrk.activities.Module2.Adapters.MediaAdapter
import com.machineries_pk.mrk.activities.Module2.GoGreenActivity
import com.machineries_pk.mrk.activities.Module2.Model.ChooseModel
import com.machineries_pk.mrk.activities.Module2.Model.MediaModel
import com.machineries_pk.mrk.activities.NewCode.Alluser
import com.machineries_pk.mrk.activities.NewCode.CalculateResultActivity
import com.machineries_pk.mrk.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var adapter: MediaAdapter? = null
    private var c_adapter: ChooseActionAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var list: java.util.ArrayList<MediaModel> = java.util.ArrayList()
    var chooselist: java.util.ArrayList<ChooseModel> = java.util.ArrayList()

    var gender = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.verRec.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        list.add(MediaModel(R.drawable.choice1, false))
        list.add(MediaModel(R.drawable.choice1, false))
        list.add(MediaModel(R.drawable.choice1, false))
        list.add(MediaModel(R.drawable.choice1, false))
        list.add(MediaModel(R.drawable.choice1, false))
        list.add(MediaModel(R.drawable.choice1, false))
        list.add(MediaModel(R.drawable.choice1, false))
        list.add(MediaModel(R.drawable.choice1, false))

        adapter = MediaAdapter(
            requireActivity(),
            list
        )
        binding.verRec.adapter = adapter


        binding.joinBtn.setOnClickListener {
            val intent = Intent(activity, GoGreenActivity::class.java)
            startActivity(intent)
        }





        binding.apply {
            popBtn.setOnClickListener {

                tvPop.setTextColor(resources.getColor(R.color.red))
                linePop.setBackgroundResource(R.color.red)
                gender = false
                tvFav.setTextColor(resources.getColor(R.color.graytext))
                lineFav.setBackgroundResource(R.color.graytext)

            }
            favbtn.setOnClickListener {
                tvPop.setTextColor(resources.getColor(R.color.graytext))
                linePop.setBackgroundResource(R.color.graytext)
                gender = false
                tvFav.setTextColor(resources.getColor(R.color.red))
                lineFav.setBackgroundResource(R.color.red)
            }
        }


        binding.chooseRec.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        chooselist.add(ChooseModel(R.drawable.choice1,"Walk 6250steps to save  co2e", false))
        chooselist.add(ChooseModel(R.drawable.choice1,"Walk 6250steps to save  co2e", false))
        chooselist.add(ChooseModel(R.drawable.choice1,"Walk 6250steps to save  co2e", false))
        chooselist.add(ChooseModel(R.drawable.choice1,"Walk 6250steps to save  co2e", false))


        c_adapter = ChooseActionAdapter(
            requireActivity(),
            chooselist
        )
        binding.chooseRec.adapter = c_adapter




    return root
}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
}