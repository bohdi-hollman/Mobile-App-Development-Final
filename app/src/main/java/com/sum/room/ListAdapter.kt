package com.sum.room

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sum.room.databinding.CustomRowBinding
import com.sum.room.fragments.list.ListFragment
import com.sum.room.fragments.list.ListFragmentDirections
import com.sum.room.model.Vocab

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListHolder>() {
    private var vocabList = emptyList<Vocab>()

    class ListHolder(private val binding: CustomRowBinding) : RecyclerView.ViewHolder(binding.root) {


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val currentItem = vocabList[position]
        val textNumber = holder.itemView.findViewById<TextView>(R.id.textViewNumber)
        val customWord = holder.itemView.findViewById<TextView>(R.id.textCustomWord)
        val customDef = holder.itemView.findViewById<TextView>(R.id.textCustomDefinition)
        val rowLayout = holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout)

        textNumber.text = currentItem.id.toString()
        customWord.text = currentItem.vocabWord
        customDef.text = currentItem.vocabDef

        rowLayout.setOnClickListener {
            val action =  ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }


    }
    fun setData(user:List<Vocab>){
        this.vocabList = user
        notifyDataSetChanged()

    }


    override fun getItemCount(): Int {
        return vocabList.size
    }
}