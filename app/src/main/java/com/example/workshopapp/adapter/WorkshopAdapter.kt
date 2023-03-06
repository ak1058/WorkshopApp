package com.example.workshopapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workshopapp.dB.WorkshopDatabase
import com.example.workshopapp.databinding.ItemWorkshopBinding
import com.example.workshopapp.listener.Listener
import com.example.workshopapp.models.WorkshopListItem
import com.example.workshopapp.sharedPreference.SavedDataPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorkshopAdapter(val listener: Listener, val database: WorkshopDatabase, var savedDataPreference: SavedDataPreference, val context: Context): ListAdapter<WorkshopListItem, WorkshopAdapter.MyViewHolder> (diffUtil()){

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

//            if (currentItem.isApplied){
//                applyBtn.visibility = View.GONE
//                appliedText.visibility = View.VISIBLE
//                Log.d("Kkk", currentItem.isApplied.toString())
//            }else{
//                    applyBtn.visibility = View.VISIBLE
//                    appliedText.visibility = View.GONE
//                }

            val name = currentItem.name
            savedDataPreference = SavedDataPreference(context)
            currentItem.userId = savedDataPreference.getUserId()!!

            CoroutineScope(Dispatchers.IO).launch {

                if (database.workshopDao().searchItem(name, currentItem.userId) >= 1) {
                    withContext(Dispatchers.Main) {
                        applyBtn.post {
                            applyBtn.visibility = View.GONE
                        }
                        appliedText.post {
                            appliedText.visibility = View.VISIBLE
                        }


                    }
                }else{
                    applyBtn.visibility = View.VISIBLE
                    appliedText.visibility = View.GONE
                }
            }

            applyBtn.setOnClickListener {
                listener.onItemClickListener(position, currentItem)
            }


        }
    }


}