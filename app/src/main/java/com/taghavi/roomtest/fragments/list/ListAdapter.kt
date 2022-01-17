package com.taghavi.roomtest.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.taghavi.roomtest.model.User
import com.taghavi.roomtest.databinding.ItemRowBinding

class ListAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ItemViewHolder
        with(viewHolder.viewBinding) {
            id.text = userList[position].id.toString()
            firstName.text = userList[position].firstName
            lastName.text = userList[position].lastName
            age.text = userList[position].age.toString()

            root.setOnClickListener { view ->
                view.findNavController()
                    .navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(userList[position]))
            }
        }
    }

    class ItemViewHolder(var viewBinding: ItemRowBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}