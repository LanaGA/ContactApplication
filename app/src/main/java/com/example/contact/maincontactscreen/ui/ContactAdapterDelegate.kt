package com.example.contact.maincontactscreen.ui

import com.bumptech.glide.Glide
import com.example.contact.R
import com.example.contact.base.Item
import com.example.contact.contact.ui.model.ContactModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.contact_view_item.*

fun listContactsAdapterDelegate(onClick: (ContactModel) -> Unit): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ContactModel, Item>(
        R.layout.contact_view_item
    ) {

        containerView.setOnClickListener {
            onClick(item)
        }

        bind {
            textName.text = (item.name + " " + item.surname)
            textNumber.text = item.number
            Glide.with(containerView)
                .load(item.pathToImage)
                .into(itemImage)
        }
    }

