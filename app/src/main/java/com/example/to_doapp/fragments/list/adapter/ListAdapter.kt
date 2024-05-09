package com.example.to_doapp.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.to_doapp.data.model.ToDoData
import com.example.to_doapp.databinding.RowLayoutBinding

//import com.example.to_doapp.ListFragmentDirections

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

   var dataList = emptyList<ToDoData>()

    class MyViewHolder(private val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(toDoData: ToDoData){
            binding.toDodData = toDoData
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.itemView.findViewById<TextView>(R.id.title_txt).text = dataList[position].title
//        holder.itemView.findViewById<TextView>(R.id.description_txt).text = dataList[position].description
//        holder.itemView.findViewById<ConstraintLayout>(R.id.row_background).setOnClickListener{
//            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
//                holder.itemView.findNavController().navigate(action)
//            }
//
//        when(dataList[position].priority){
//            Priority.HIGH -> holder.itemView.findViewById<CardView>(R.id.priority_indicator).setCardBackgroundColor(
//                ContextCompat.getColor(
//                    holder.itemView.context,
//                    R.color.red
//                )
//            )
//            Priority.MEDIUM-> holder.itemView.findViewById<CardView>(R.id.priority_indicator).setCardBackgroundColor(
//                ContextCompat.getColor(
//                    holder.itemView.context,
//                    R.color.green
//                )
//            )
//            Priority.LOW-> holder.itemView.findViewById<CardView>(R.id.priority_indicator).setCardBackgroundColor(
//                ContextCompat.getColor(
//                    holder.itemView.context,
//                    R.color.yellow
//                )
//            )
//        }

        val currentItems = dataList[position]
        holder.bind(currentItems)
    }

    fun setData(toDoData: List<ToDoData>){
        val toDoDiffUtil = ToDoDiffUtil(dataList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = toDoData
//        notifyDataSetChanged()
        toDoDiffResult.dispatchUpdatesTo(this)
    }


}