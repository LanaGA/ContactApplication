package com.example.contact.contact.data

import com.example.contact.contact.ui.model.ContactModel
import io.reactivex.Single

interface ContactRepository {
    fun createContact(entity: ContactModel): Single<Unit>

    fun updateContact(oldEntity: ContactModel, newEntity: ContactModel): Single<Unit>

    fun deleteContact(entity: ContactModel): Single<Unit>

    fun getAllContacts(): Single<List<ContactModel>>

    fun getContact(number: String): Single<ContactModel>
}