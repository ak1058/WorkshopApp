package com.example.workshopapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workshopapp.databinding.ItemWorkshopBinding
import com.example.workshopapp.listener.Listener
import com.example.workshopapp.models.WorkshopListItem

class AppliedWorkshopAdapter(val listener: Listener): ListAdapter<WorkshopListItem, AppliedWorkshopAdapter.MyViewHolder> (diffUtil()){

    inner class MyViewHolder(val binding: ItemWorkshopBinding): RecyclerView.ViewHolder(binding.root)

    class diffUtil: DiffUtil.ItemCallback<WorkshopListItem>(){
        override fun areItemsTheSame(oldItem: WorkshopListItem, newItem: WorkshopListItem): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkshopListItem, newItem: WorkshopListItem): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemWorkshopBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            workshopName.text = currentItem.name
            dateText.text = currentItem.date
            time.text = currentItem.time
            instructorName.text = currentItem.instructor
            applyBtn.visibility = View.GONE
            appliedText.visibility = View.VISIBLE
            root.setOnClickListener {
                listener.onItemClickListener(position, currentItem)
            }

        }
    }


}