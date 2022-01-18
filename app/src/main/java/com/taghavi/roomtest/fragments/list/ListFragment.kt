package com.taghavi.roomtest.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.taghavi.roomtest.R
import com.taghavi.roomtest.viewModel.UserViewModel
import com.taghavi.roomtest.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.readAllData.observe(viewLifecycleOwner, Observer { users ->
            binding.recyclerView.adapter = ListAdapter(users)
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToAddFragment())
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAllUsers()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }
}