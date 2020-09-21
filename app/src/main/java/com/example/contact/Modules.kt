package com.example.contact

import androidx.room.Room
import com.example.contact.contact.data.ContactInteractor
import com.example.contact.contact.data.ContactRepository
import com.example.contact.contact.data.ContactRepositoryImpl
import com.example.contact.contact.data.local.ContactDao
import com.example.contact.contact.data.local.ContactDatabase
import com.example.contact.createscreen.CreateContactViewModel
import com.example.contact.editcontactscreen.EditContactViewModel
import com.example.contact.maincontactscreen.di.CONTACTS_QUALIFIER
import com.example.contact.maincontactscreen.di.CONTACT_TABLE
import com.example.contact.maincontactscreen.ui.ContactViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

val appModule = module {

    single<ContactDatabase> {
        Room.databaseBuilder(
            androidContext(),
            ContactDatabase::class.java,
            CONTACT_TABLE
        ).build()
    }

    single<ContactDao> {
        get<ContactDatabase>().contactDao()
    }

    single<ContactRepository> {
        ContactRepositoryImpl(get())
    }
    single {
        ContactInteractor(get())
    }
}

val navModule = module {

    single<Cicerone<Router>>(named(CONTACTS_QUALIFIER)) {
        Cicerone.create()
    }

    single<NavigatorHolder>(named(CONTACTS_QUALIFIER)) {
        get<Cicerone<Router>>(named(CONTACTS_QUALIFIER)).navigatorHolder
    }

    single<Router>(named(CONTACTS_QUALIFIER)) {
        get<Cicerone<Router>>(named(CONTACTS_QUALIFIER)).router
    }
}

val viewModelModule = module {
    viewModel {
        ContactViewModel(get())
    }
    viewModel {
        CreateContactViewModel(get())
    }
    viewModel {
        EditContactViewModel(get())
    }
}