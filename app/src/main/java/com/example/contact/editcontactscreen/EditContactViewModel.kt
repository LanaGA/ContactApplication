package com.example.contact.editcontactscreen

import com.example.contact.base.BaseViewModel
import com.example.contact.base.Event
import com.example.contact.contact.data.ContactInteractor
import com.example.contact.contact.ui.DataEvent
import com.example.contact.contact.ui.STATUS
import com.example.contact.contact.ui.UiEvent
import com.example.contact.contact.ui.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditContactViewModel(private val interactor: ContactInteractor) : BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, null, null)

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.RequestContact -> {
                interactor
                    .getContact(event.number)
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
            is UiEvent.UpdateContact -> {
                interactor
                    .updateContact(event.ContactsModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.OnContactSaved)
                        },
                        {
                            it
                        }
                    )
            }
            is DataEvent.SuccessContactsRequest -> {
                return previousState.copy(
                    status = STATUS.CONTENT,
                    contactModel = event.contactsModel
                )
            }
            is DataEvent.OnContactSaved -> {
                return previousState.copy(
                    status = STATUS.CONTENT)
            }

        }
        return null
    }
}