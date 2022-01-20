package com.taghavi.roomtest.fragments.add

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.taghavi.roomtest.R
import com.taghavi.roomtest.databinding.FragmentAddBinding
import com.taghavi.roomtest.model.Address
import com.taghavi.roomtest.model.User
import com.taghavi.roomtest.viewModel.UserViewModel


class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.addButton.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun insertDataToDatabase() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val age = binding.age.text
        val streetName = binding.streetName.text.toString()
        val streetNumber = binding.streetNumber.text

        val myDrawable = resources.getDrawable(R.mipmap.ic_launcher)
        val picture = myDrawable.toBitmap()

        if (inputCheck(firstName, lastName, age, streetName, streetNumber)) {
            val address = Address(streetName, streetNumber.toString().toInt())
            val user =
                User(0, firstName, lastName, age.toString().toInt(), address, picture)
            viewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
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
}