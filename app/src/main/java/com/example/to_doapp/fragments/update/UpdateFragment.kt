package com.example.to_doapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.to_doapp.R
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoData
import com.example.to_doapp.data.viewModel.ToDoViewModel
import com.example.to_doapp.databinding.FragmentUpdateBinding
import com.example.to_doapp.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedPreferences: SharedViewModel by viewModels ()
    private val mToDoViewModel: ToDoViewModel by viewModels ()

    private var binding: FragmentUpdateBinding? = null
    private val FragmentBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // DataBinding
       binding = FragmentUpdateBinding.inflate(inflater, container, false)
       binding!!.args = args

        setHasOptionsMenu(true)
        FragmentBinding.updateSpinner.onItemSelectedListener= mSharedPreferences.listener

        return FragmentBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.save_menu->updateItem()
            R.id.delete_menu->confirmItemRemoval()
            android.R.id.home -> {
                // Handle the back button press here
                requireActivity().onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = view?.findViewById<EditText>(R.id.updateTitle)?.text.toString()
        val description = view?.findViewById<EditText>(R.id.updateTextMultiLine)?.text.toString()
        val getPriority = view?.findViewById<Spinner>(R.id.updateSpinner)?.selectedItem.toString()

        val validation = mSharedPreferences.verifyDataFromUser(title, description)
        if (validation){
            val updateItem = ToDoData(
                args.currentItems.id,
                title,
                mSharedPreferences.parsePriority(getPriority),
                description
            )
            mToDoViewModel.updateData(updateItem)
            Toast.makeText(requireContext(), "Successfully Updated!!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please Fill All Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mToDoViewModel.deleteData(args.currentItems)
            Toast.makeText(requireContext(), "Successfully Removed: ${args.currentItems.title}",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete ${args.currentItems.title}?")
        builder.setMessage("Are you sure you want to remove this ${args.currentItems.title}")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}