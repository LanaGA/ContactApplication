package com.example.contact.contact.data

import com.example.contact.contact.data.local.ContactEntity
import com.example.contact.contact.ui.model.ContactModel

fun ContactEntity.mapToUiModel(): ContactModel {
    return ContactModel(pathToImage, name, surname, number)
}

fun ContactModel.mapToEntityModel(): ContactEntity {
    return ContactEntity(pathToImage, name, surname, number)
}