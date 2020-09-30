package com.example.contact.maincontactscreen.ui

import com.bumptech.glide.Glide
import com.example.contact.R
import com.example.contact.base.Item
import com.example.contact.contact.ui.model.ContactModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.contact_view_item.*
import kotlinx.android.synthetic.main.fragment_main.*

fun listContactsAdapterDelegate(onClick: (Int) -> Unit): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ContactModel, Item>(
        R.layout.contact_view_item
    ) {
        bind {
            rvContact.setOnClickListener {
                onClick(adapterPosition)
            }
            textName.text = (item.name + " " + item.surname)
            textNumber.text = item.number
            Glide.with(containerView)
                .load(item.pathToImage)
                .into(itemImage)

        }
    }