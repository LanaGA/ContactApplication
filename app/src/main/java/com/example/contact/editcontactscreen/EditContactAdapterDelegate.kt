package com.example.contact.editcontactscreen

import com.bumptech.glide.Glide
import com.example.contact.R
import com.example.contact.base.Item
import com.example.contact.contact.ui.model.ContactModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.fragment_edit_contact.*

fun editContactsAdapterDelegate(onClick: (Int) -> Unit): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ContactModel, Item>(
        R.layout.contact_view_item
    ) {

        containerView.setOnClickListener {
            onClick(adapterPosition)
        }

        bind {
            nameEditText.setText(item.name)
            surnameEditText.setText(item.surname)
            numberEditText.setText(item.number)
            Glide.with(containerView)
                .load(item.pathToImage)
                .into(editImageView)
        }
    }