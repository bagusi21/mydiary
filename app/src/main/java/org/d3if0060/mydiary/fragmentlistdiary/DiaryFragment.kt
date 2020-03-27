package org.d3if0060.mydiary.fragmentlistdiary

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if0060.mydiary.MainActivity
import org.d3if0060.mydiary.R
import org.d3if0060.mydiary.database.DataDiary
import org.d3if0060.mydiary.database.DiaryDatabase
import org.d3if0060.mydiary.databinding.FragmentDiaryBinding

/**
 * A simple [Fragment] subclass.
 */
class DiaryFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var binding: FragmentDiaryBinding
    private lateinit var diaryViewModel: DiaryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val titleBar = activity!! as MainActivity
        titleBar.supportActionBar?.title = "My Diary"
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary, container, false)

        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application

        val dataSource = DiaryDatabase.getInstance(application).diaryDatabaseDao

        val viewModelFactory = DiaryViewModelFactory(dataSource,application)

        diaryViewModel = ViewModelProviders.of(
            this,viewModelFactory).get(DiaryViewModel::class.java)

        diaryViewModel.allDiary.observe(viewLifecycleOwner, Observer {
            val adapter = DiaryAdapter(it)

            val rv = binding.rvDiaryList
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(this.requireContext())

            adapter.listener  = this
        })

        binding.fab.setOnClickListener{
            it.findNavController().navigate(R.id.action_diaryFragment_to_writeFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_del, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.del_all -> {
                diaryViewModel.onClear()
                Toast.makeText(context,"Done", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return NavigationUI.onNavDestinationSelected(item,view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onRecyclerViewItemClicked(view: View, diary: DataDiary) {
        when(view.id){
            R.id.list_item -> {
                val sendBundle = Bundle()
                sendBundle.putLong("id",diary.diaryId)
                sendBundle.putString("diarymsg", diary.isi_diary)

                view.findNavController().navigate(R.id.action_diaryFragment_to_updateFragment,sendBundle)
            }
        }
    }
}

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, diary: DataDiary)
}
