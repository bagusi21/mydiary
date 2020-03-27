package org.d3if0060.mydiary.writediary

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.d3if0060.mydiary.R
import org.d3if0060.mydiary.database.DiaryDatabase
import org.d3if0060.mydiary.databinding.FragmentWriteBinding
import org.d3if0060.mydiary.fragmentlistdiary.DiaryViewModel
import org.d3if0060.mydiary.fragmentlistdiary.DiaryViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class WriteFragment : Fragment() {

    private lateinit var binding: FragmentWriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_icon -> {
                if (binding.edIsiDiary.text.isEmpty()){
                    Toast.makeText(activity, "Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show()
                }else{
                    val application = requireNotNull(this.activity).application

                    val dataSource = DiaryDatabase.getInstance(application).diaryDatabaseDao

                    val viewModelFactory = DiaryViewModelFactory(dataSource, application)

                    val diaryViewModel = ViewModelProviders.of(
                        this,viewModelFactory).get(DiaryViewModel::class.java)

                    diaryViewModel.onSaveDiary(binding.edIsiDiary.text.toString())
                    view?.findNavController()?.navigate(R.id.action_writeFragment_to_diaryFragment)

                   return true
                }
            }
        }
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }
}
