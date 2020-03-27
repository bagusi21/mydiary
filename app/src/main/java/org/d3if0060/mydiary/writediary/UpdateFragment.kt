package org.d3if0060.mydiary.writediary


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.d3if0060.mydiary.MainActivity
import org.d3if0060.mydiary.R
import org.d3if0060.mydiary.database.DataDiary
import org.d3if0060.mydiary.database.DiaryDatabase
import org.d3if0060.mydiary.databinding.FragmentUpdateBinding
import org.d3if0060.mydiary.fragmentlistdiary.DiaryViewModel
import org.d3if0060.mydiary.fragmentlistdiary.DiaryViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_update, container, false)

        val titleBar = activity!! as MainActivity
        titleBar.supportActionBar?.title = "Update Cerita"

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_del, menu)
        inflater.inflate(R.menu.save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val application = requireNotNull(this.activity).application

        val dataSource = DiaryDatabase.getInstance(application).diaryDatabaseDao

        val viewModelFactory = DiaryViewModelFactory(dataSource, application)

        val diaryViewModel = ViewModelProviders.of(
            this,viewModelFactory).get(DiaryViewModel::class.java)

        when(item.itemId){
            R.id.save_icon -> {
                if (isValid(arguments!!.getLong("id"), diaryViewModel)) {
                    requireView().findNavController().popBackStack()
                    Toast.makeText(requireContext(), "Success To Update !", Toast.LENGTH_SHORT).show()
                    return true
                }else{
                    Toast.makeText(requireContext(), "Invalid !!", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.del_all -> {
                diaryViewModel.onDelete(arguments!!.getLong("id"))
                Toast.makeText(requireContext(), "Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                requireView().findNavController().popBackStack()
            }
        }
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController())
                || super.onOptionsItemSelected(item)

//        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){
//            val id = arguments!!.getLong("id")
            val isidiary = arguments!!.getString("diarymsg")


            binding.edUpdateDiary.setText(isidiary)
        }
    }

    fun update(idDiary: Long, diaryViewModel: DiaryViewModel){
        val date = System.currentTimeMillis()
        val diarymsg = DataDiary(idDiary, binding.edUpdateDiary.text.toString(),date)
        diaryViewModel.onUpdate(diarymsg)
    }

    private fun isValid(id: Long, diaryViewModel: DiaryViewModel): Boolean {
        return when {
            binding.edUpdateDiary.text.trim().isEmpty() -> false

            else -> {
                update(id, diaryViewModel)
                true
            }
        }
    }
}
