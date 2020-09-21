package com.example.contact.editcontactscreen

import com.example.contact.base.BaseViewModel
import com.example.contact.base.Event
import com.example.contact.contact.data.ContactInteractor
import com.example.contact.editcontactscreen.ui.DataEvent
import com.example.contact.editcontactscreen.ui.STATUS
import com.example.contact.editcontactscreen.ui.UiEvent
import com.example.contact.editcontactscreen.ui.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditContactViewModel(private val interactor: ContactInteractor) : BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, null)

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnRequestContact -> {
                interactor
                    .getContact(event.number)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.OnSuccessContactsRequest(it))
                        },
                        {
                            it
                        }
                    )
            }
        }
        return null
    }
}