package com.example.contact.contact.ui

import com.example.contact.base.Event
import com.example.contact.contact.ui.model.ContactModel

data class ViewState(
    val status: STATUS,
    val contactModel: ContactModel?,
    val contactList: List<ContactModel>?
)

sealed class UiEvent : Event {
    data class CreateContact(
        val ContactsModel: ContactModel
    ) : UiEvent()

    data class UpdateContact(
        val ContactsModel: ContactModel
    ) : UiEvent()

    data class RequestContact(
        val number: String
    ) : UiEvent()

    data class OpenEditContact(
        val number: String
    ): UiEvent()
    object RequestAllContacts : UiEvent()
}

sealed class DataEvent : Event {
    data class SuccessContactsRequest(val contactsModel: ContactModel) : DataEvent()
    data class SuccessAllContactsRequest(val listContactsModel: List<ContactModel>) : DataEvent()
    object OnContactSaved : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}