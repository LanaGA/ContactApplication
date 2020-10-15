package com.example.contact.maincontactscreen.ui

import com.example.contact.base.BaseViewModel
import com.example.contact.base.Event
import com.example.contact.contact.data.ContactInteractor
import com.example.contact.contact.ui.DataEvent
import com.example.contact.contact.ui.STATUS
import com.example.contact.contact.ui.UiEvent
import com.example.contact.contact.ui.ViewState
import com.example.contact.editcontactscreen.EditContactScreen
import com.example.contact.maincontactscreen.di.CONTACTS_QUALIFIER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class ContactViewModel(private val interactor: ContactInteractor,  private val router: Router) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, null, null)
    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.RequestAllContacts -> {
                interactor
                    .getAllContacts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.SuccessAllContactsRequest(it))
                        },
                        {
                            it
                        }
                    )
            }
            is UiEvent.OpenEditContact -> {
                router.navigateTo(EditContactScreen(event.number))
            }
            is DataEvent.SuccessAllContactsRequest -> {
                return previousState.copy(
                    status = STATUS.CONTENT,
                    contactModel = null,
                    contactList = event.listContactsModel
                )
            }
        }
        return null
    }
}