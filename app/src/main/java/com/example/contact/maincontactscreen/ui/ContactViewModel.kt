package com.example.contact.maincontactscreen.ui

import com.example.contact.base.BaseViewModel
import com.example.contact.base.Event
import com.example.contact.contact.data.ContactInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactViewModel(private val interactor: ContactInteractor) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, emptyList())
    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.RequestContact -> {
                interactor
                    .getAllContacts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.SuccessContactsRequest(it))
                        },
                        {
                            it
                        }
                    )
            }
            is DataEvent.SuccessContactsRequest -> {
                return previousState.copy(
                    status = STATUS.CONTENT,
                    contactList = event.listContactsModel
                )
            }
        }
        return null
    }
}