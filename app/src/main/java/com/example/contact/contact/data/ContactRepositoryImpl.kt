package com.example.contact.contact.data

import com.example.contact.contact.data.local.ContactDao
import com.example.contact.contact.ui.model.ContactModel
import io.reactivex.Single

class ContactRepositoryImpl(private val contactDao: ContactDao) : ContactRepository {
    override fun createContact(entity: ContactModel): Single<Unit> =
        contactDao.create(entity.mapToEntityModel())

    override fun updateContact(entity: ContactModel): Single<Unit> =
        contactDao.update(entity.mapToEntityModel())

    override fun deleteContact(entity: ContactModel): Single<Unit> =
        contactDao.delete(entity.mapToEntityModel())

    override fun getAllContacts(): Single<List<ContactModel>> =
        contactDao.read().map { list -> list.map { it.mapToUiModel() } }

    override fun getContact(number: String): Single<ContactModel> =
        contactDao.readByNumber(number).map { it.mapToUiModel() }

}