package com.example.contact.contact.ui.model

import com.example.contact.base.Item

data class ContactModel(
    val pathToImage: String,
    val name: String,
    val surname: String,
    val number: String
) : Item