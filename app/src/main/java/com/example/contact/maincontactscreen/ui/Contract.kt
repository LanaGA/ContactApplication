package com.example.contact.maincontactscreen.ui

import com.example.contact.base.Event
import com.example.contact.contact.ui.model.ContactModel

data class ViewState(
    val status: STATUS,
    val contactList: List<ContactModel>
)

sealed class UiEvent : Event {
    data class RequestContact(
        val number: String
    ) : UiEvent()
}

sealed class DataEvent : Event {
    data class SuccessContactsRequest(val listContactsModel: List<ContactModel>) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}