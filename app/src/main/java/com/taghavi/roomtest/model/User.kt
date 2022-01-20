package com.taghavi.roomtest.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    @Embedded
    val address: Address,
    val picture: Bitmap
) : Parcelable

@Parcelize
data class Address(
    val streetName: String,
    val streetNumber: Int,
) : Parcelable