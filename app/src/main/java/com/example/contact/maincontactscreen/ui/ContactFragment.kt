package com.example.contact.maincontactscreen.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.R
import com.example.contact.createscreen.CreateContactScreen
import com.example.contact.editcontactscreen.EditContactScreen
import com.example.contact.editcontactscreen.ui.STATUS
import com.example.contact.editcontactscreen.ui.UiEvent
import com.example.contact.editcontactscreen.ui.ViewState
import com.example.contact.maincontactscreen.di.CONTACTS_QUALIFIER
import com.example.contact.setAdapterAndCleanupOnDetachFromWindow
import com.example.contact.setData
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class ContactFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: ContactViewModel by viewModel()
    private val adapter = ListDelegationAdapter(listContactsAdapterDelegate {
        router.navigateTo(EditContactScreen())
    })
    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        rvContact.layoutManager = LinearLayoutManager(requireContext())
        rvContact.setAdapterAndCleanupOnDetachFromWindow(adapter)
        goToCreateContactButton.setOnClickListener {
            router.navigateTo(CreateContactScreen())
        }
        viewModel.processUiEvent(UiEvent.RequestAllContacts)
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
            }
            STATUS.CONTENT -> adapter.setData(viewState.contactList)
            STATUS.ERROR -> {
            }
        }
    }


}