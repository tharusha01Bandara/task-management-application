package com.example.to_doapp.fragments
//
import android.content.Context
import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.to_doapp.R
import com.example.to_doapp.data.model.Priority
import com.example.to_doapp.data.model.ToDoData
import com.example.to_doapp.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
//
class BindingAdapter {
    companion object{

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate : Boolean){
            view.setOnClickListener{
                if (navigate){
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase:MutableLiveData<Boolean>){
             when(emptyDatabase.value){
                 true -> view.visibility = View.VISIBLE
                 false -> view.visibility = View.INVISIBLE
                 else -> {}
             }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view:Spinner, priority:Priority){
                 when(priority){
                     Priority.HIGH -> {view.setSelection(0)}
                     Priority.MEDIUM -> {view.setSelection(1)}
                     Priority.LOW -> {view.setSelection(2)}

                 }
        }
        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority){
            when(priority){
                Priority.HIGH -> {cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red))}
                Priority.MEDIUM -> {cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))}
                Priority.LOW -> {cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))}

            }
        }

        @BindingAdapter("android:sendDataTOUpdateFragment")
        @JvmStatic
        fun sendDataTOUpdateFragment(view:ConstraintLayout, currentItems: ToDoData){
             view.setOnClickListener{
                 val action =  ListFragmentDirections.actionListFragmentToUpdateFragment(currentItems)
                 view.findNavController().navigate(action)
             }
        }
    }
}