package com.example.to_doapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.to_doapp.R
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoData
import com.example.to_doapp.data.viewModel.ToDoViewModel
import com.example.to_doapp.fragments.SharedViewModel
import org.w3c.dom.Text

class AddFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels ()
    private  val mSharedViewModel: SharedViewModel by viewModels ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        val spinner = view.findViewById<Spinner>(R.id.addSpinner)
        spinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.add_items){
//            insertDataToDb()
//        }
        when (item.itemId) {
            R.id.add_items -> insertDataToDb()
            android.R.id.home -> {
                // Handle the back button press here
                requireActivity().onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitleEditText = view?.findViewById<EditText>(R.id.addTitle)
        val mTitle = mTitleEditText?.text.toString()

        val mPrioritySpinner = view?.findViewById<Spinner>(R.id.addSpinner)
        val mPriority = mPrioritySpinner?.selectedItem.toString()

        val mDescriptionView = view?.findViewById<EditText>(R.id.addTextMultiLine)
        val mDescription = mDescriptionView?.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation){
            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully Added!!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please Fill All Data", Toast.LENGTH_SHORT).show()
        }
    }
}
