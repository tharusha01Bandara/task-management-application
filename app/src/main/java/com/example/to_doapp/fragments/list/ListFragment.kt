package com.example.to_doapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.to_doapp.R
import com.example.to_doapp.data.model.ToDoData
import com.example.to_doapp.data.viewModel.ToDoViewModel
import com.example.to_doapp.fragments.SharedViewModel
import com.example.to_doapp.databinding.FragmentListBinding
import com.example.to_doapp.fragments.list.adapter.ListAdapter
import com.example.to_doapp.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var fragmentBinding: FragmentListBinding? = null
    private val binding get() = fragmentBinding!!


    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        setupRecyclerview()

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })
        setHasOptionsMenu(true)

        hideKeyboard(requireActivity())

        return binding.root
    }

    private fun setupRecyclerview() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.dataList[viewHolder.adapterPosition]
                // Deletetems
                mToDoViewModel.deleteData(itemToDelete)
                adapter. notifyItemRemoved(viewHolder.adapterPosition)
//                Toast.makeText(requireContext(), "Successfully Removed: '${itemToDelete.title}'", Toast.LENGTH_SHORT).show()
                //RestoreDeletedData
                restoreDeletedData(viewHolder.itemView, itemToDelete)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItems: ToDoData){
           val snackBar = Snackbar.make(
               view, "Deleted '${deletedItems.title}'",
               Snackbar.LENGTH_LONG
           )
        snackBar.setAction("Undo"){
            mToDoViewModel.insertData(deletedItems)
            adapter.notifyDataSetChanged()
        }
        snackBar.show()
    }

//    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
//        if (emptyDatabase){
//            view?.findViewById<ImageView>(R.id.imageView)?.visibility = View.VISIBLE
//            view?.findViewById<TextView>(R.id.textView)?.visibility = View.VISIBLE
//        }else{
//            view?.findViewById<ImageView>(R.id.imageView)?.visibility = View.INVISIBLE
//            view?.findViewById<TextView>(R.id.textView)?.visibility = View.INVISIBLE
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.menu_delete_all){
//            confirmRemoval()
//        }
        when(item.itemId){
            R.id.menu_delete_all -> confirmRemoval()
            R.id.menu_priority_high -> mToDoViewModel.sortByHighPriority.observe(this, Observer { adapter.setData(it) })
            R.id.menu_priority_low -> mToDoViewModel.sortByLowPriority.observe(this, Observer { adapter.setData(it) })

        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mToDoViewModel. deleteAll()
            Toast.makeText(requireContext(), "Successfully Removed Everything",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
         var searchQuery:String = query
        searchQuery = "%$searchQuery"

        mToDoViewModel.searchDataBase(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(it)
            }
        })
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchThroughDatabase(query)
        }
        return true
    }
}
