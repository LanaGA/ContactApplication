package com.example.contact.contact.data

import com.example.contact.contact.ui.model.ContactModel

class ContactInteractor(private val repository: ContactRepository) {
    fun createContact(contactModel: ContactModel) = repository.createContact(contactModel)
    fun getContact(number: String) = repository.getContact(number)
    fun getAllContacts() = repository.getAllContacts()
    fun updateContact(oldContactModel: ContactModel, newContactModel: ContactModel) =
        repository.updateContact(oldContactModel, newContactModel)
    fun deleteContact(contactModel: ContactModel) = repository.deleteContact(contactModel)
}