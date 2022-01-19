package com.taghavi.roomtest.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.taghavi.roomtest.R
import com.taghavi.roomtest.databinding.FragmentUpdateBinding
import com.taghavi.roomtest.model.Address
import com.taghavi.roomtest.model.User
import com.taghavi.roomtest.viewModel.UserViewModel

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var viewModel: UserViewModel
    private val args: UpdateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.firstName.setText(args.currentUser.firstName)
        binding.lastName.setText(args.currentUser.lastName)
        binding.age.setText(args.currentUser.age.toString())

        binding.updateButton.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateItem() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val age = binding.age.text
        val streetName = binding.streetName.text.toString()
        val streetNumber = binding.streetNumber.text

        if (inputCheck(firstName, lastName, age, streetName, streetNumber)) {
            val address = Address(streetName, streetNumber.toString().toInt())
            val updatedUser =
                User(args.currentUser.id, firstName, lastName, age.toString().toInt(), address)
            viewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Updated successfully!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(
        firstName: String,
        lastName: String,
        age: Editable,
        streetName: String,
        streetNumber: Editable
    ): Boolean {
        return !(
                TextUtils.isEmpty(firstName) &&
                        TextUtils.isEmpty(lastName) &&
                        age.isEmpty() &&
                        TextUtils.isEmpty(streetName) &&
                        streetNumber.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentUser.firstName}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
        builder.create().show()
    }
}