package com.example.contact.editcontactscreen.ui

import com.example.contact.base.Event
import com.example.contact.contact.ui.model.ContactModel

data class ViewState(
    val status: STATUS,
    val contactModel: ContactModel?
)

sealed class UiEvent : Event {
    data class OnCreateContact(
        val image: String,
        val name: String,
        val surname: String,
        val number: String
    ) : UiEvent()

    data class OnUpdateContact(
        val image: String,
        val name: String,
        val surname: String,
        val number: String
    ) : UiEvent()

    data class OnRequestContact(
        val number: String
    ) : UiEvent()
}

sealed class DataEvent : Event {
    data class OnSuccessContactsRequest(val ContactsModel: ContactModel) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}