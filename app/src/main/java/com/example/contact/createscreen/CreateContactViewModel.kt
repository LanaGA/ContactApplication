package com.example.contact.createscreen

import android.util.Log
import com.example.contact.base.BaseViewModel
import com.example.contact.base.Event
import com.example.contact.contact.data.ContactInteractor
import com.example.contact.contact.ui.model.ContactModel
import com.example.contact.editcontactscreen.ui.STATUS
import com.example.contact.editcontactscreen.ui.UiEvent
import com.example.contact.editcontactscreen.ui.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreateContactViewModel(private val interactor: ContactInteractor) : BaseViewModel<ViewState>(){
    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, null)

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when(event){
            is UiEvent.OnCreateContact -> {
                interactor.createContact(ContactModel(
                    event.image,
                    event.name,
                    event.surname,
                    event.number
                ))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            Log.d("DEBUG", "Success UiEvent.OnContactCreate")
                        },
                        {
                            Log.d("DEBUG", "Fail UiEvent.OnContactCreate")
                        }
                    )
            }

        }
        return null
    }

}