package com.machineries_pk.mrk.activities.Module2.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.Module2.Adapters.ChooseActionAdapter
import com.machineries_pk.mrk.activities.Module2.Adapters.MediaAdapter
import com.machineries_pk.mrk.activities.Module2.GoGreenActivity
import com.machineries_pk.mrk.activities.Module2.HomeActivity
import com.machineries_pk.mrk.activities.Module2.Model.ChooseModel
import com.machineries_pk.mrk.activities.Module2.Model.MediaModel
import com.machineries_pk.mrk.databinding.FragmentHomeBinding
import io.paperdb.Paper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var adapter: MediaAdapter? = null
    private var c_adapter: ChooseActionAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var listdf: java.util.ArrayList<MediaModel> = java.util.ArrayList()
    var chooselist: java.util.ArrayList<ChooseModel> = java.util.ArrayList()

    var gender = false


//    lateinit var  homeViewModel:HomeViewModel

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
        listdf.add(MediaModel(R.drawable.choice1, false))
        listdf.add(MediaModel(R.drawable.choice2, false))
        listdf.add(MediaModel(R.drawable.choice3, false))
        listdf.add(MediaModel(R.drawable.choice4, false))
        listdf.add(MediaModel(R.drawable.choice5, false))


        adapter = MediaAdapter(
            "pop",
            requireActivity(),
            listdf
        ){ position: Int -> mediaclickclick( position) }
        binding.verRec.adapter = adapter



        binding.apply {
            popBtn.setOnClickListener {

                tvPop.setTextColor(resources.getColor(R.color.red))
                linePop.setBackgroundResource(R.color.red)
                gender = false
                tvFav.setTextColor(resources.getColor(R.color.graytext))
                lineFav.setBackgroundResource(R.color.graytext)

                adapter = MediaAdapter(
                    "pop",
                    requireActivity(),
                    listdf
                ){ position: Int -> mediaclickclick( position) }
                binding.verRec.adapter = adapter

            }
            favbtn.setOnClickListener {
                tvPop.setTextColor(resources.getColor(R.color.graytext))
                linePop.setBackgroundResource(R.color.graytext)
                gender = false
                tvFav.setTextColor(resources.getColor(R.color.red))
                lineFav.setBackgroundResource(R.color.red)


                adapter = MediaAdapter(
                    "fav",
                    requireActivity(),
                    listdf
                ){ position: Int -> mediaclickclick( position) }
                binding.verRec.adapter = adapter

            }
        }




        if (Paper.book().read("isMember","")== "true"){
            binding.joinBtn.visibility = View.INVISIBLE
        }

        binding.joinBtn.setOnClickListener {
            if (Paper.book().read("isMember","")== "true"){
                Toast.makeText(activity, "Already Subscribed.", Toast.LENGTH_SHORT).show()
            }else{

                val intent = Intent(activity, GoGreenActivity::class.java)
                startActivity(intent)
            }
        }


        binding.chooseRec.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        chooselist.add(ChooseModel(R.drawable.action1, "10% off vegan lunch","Walk 6250steps to save  co2" ,false))
        chooselist.add(ChooseModel(R.drawable.action2, "10% off vegan lunch","Once vegan per week" ,false))
        chooselist.add(ChooseModel(R.drawable.action3, "Renting electric car","Green Mobility" ,false))
        chooselist.add(ChooseModel(R.drawable.action4, "Easy everyday action","Wash clothes in lower Temperature" ,false))
        chooselist.add(ChooseModel(R.drawable.action5, "Easy everyday action","Reduce pup’s carbon print" ,false))
        chooselist.add(ChooseModel(R.drawable.action6, "Easy everyday action","Skip Stand-by mode, turn off electronics" ,false))
        chooselist.add(ChooseModel(R.drawable.action7, "Easy everyday action","Reduce Shower time by 1 minute" ,false))
        chooselist.add(ChooseModel(R.drawable.action8, "Mobility","Car Free Day" ,false))
        chooselist.add(ChooseModel(R.drawable.action9, "Travel","Stay Vacation" ,false))



//        binding.chooseRec.enableVersticleScroll(false)

        c_adapter = ChooseActionAdapter(
            requireActivity(),
            chooselist
        ) { count: Int,position: Int -> chooseitemclick(count, position) }
        binding.chooseRec.adapter = c_adapter




        return root
    }

    fun chooseitemclick(count: Int,position: Int) {
//        chooselist[position].check = !chooselist[position].check

        (activity as HomeActivity?)?.notificationsViewModel?.updateddata(count)
    }
    fun mediaclickclick(position: Int) {


//        listdf[position].fav = !listdf[position].fav

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}