package com.example.contact.contact.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.contact.base.Item
import com.example.contact.maincontactscreen.di.CONTACT_TABLE

@Entity(tableName = CONTACT_TABLE)
data class ContactEntity(
    @ColumnInfo(name = "pathToImage")
    val pathToImage: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "surname")
    val surname: String,
    @PrimaryKey
    @ColumnInfo(name = "number")
    val number: String
): Item