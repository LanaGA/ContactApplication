package com.example.contact.createscreen

import android.util.Log
import com.example.contact.base.BaseViewModel
import com.example.contact.base.Event
import com.example.contact.contact.data.ContactInteractor
import com.example.contact.contact.ui.DataEvent
import com.example.contact.contact.ui.STATUS
import com.example.contact.contact.ui.UiEvent
import com.example.contact.contact.ui.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreateContactViewModel(private val interactor: ContactInteractor) :
    BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, null, null)

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.CreateContact -> {
                interactor.createContact(
                    event.ContactsModel
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.OnContactSaved)
                        },
                        {
                            Log.d("DEBUG", it.toString())
                        }
                    )
            }

        }
        return null
    }

}