package com.example.contact.createscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.contact.R
import com.example.contact.base.IMAGE_TYPE
import com.example.contact.base.REQUEST_CODE
import com.example.contact.base.checkPermissionForReadFromStorage
import com.example.contact.base.requestPermissionForReadFromStorage
import com.example.contact.contact.ui.model.ContactModel
import com.example.contact.editcontactscreen.ui.STATUS
import com.example.contact.editcontactscreen.ui.UiEvent
import com.example.contact.editcontactscreen.ui.ViewState
import com.example.contact.maincontactscreen.di.CONTACTS_QUALIFIER
import com.example.contact.maincontactscreen.ui.ContactScreen
import com.example.contact.maincontactscreen.ui.contactAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_create_contact.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class CreateContactFragment : Fragment(R.layout.fragment_create_contact) {

    private var currentImagePath = ""
    private val viewModel: CreateContactViewModel by viewModel()
    private val adapter = ListDelegationAdapter(
        contactAdapterDelegate {
            viewModel.processUiEvent(
                UiEvent.CreateContact(it)
            )
        }
    )
    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
                    false
                )
                router.backTo(ContactScreen())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        createImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_account_circle_24))
        createImageView.setOnClickListener {
            if (!checkPermissionForReadFromStorage()) {
                requestPermissionForReadFromStorage()
            }
            if (checkPermissionForReadFromStorage()) {
                getImage()
            }
        }

        createContactButton.setOnClickListener {
            viewModel.processUiEvent(
                UiEvent.CreateContact(
                    ContactModel(
                        currentImagePath,
                        nameCreateText.text.toString(),
                        surnameCreateText.text.toString(),
                        numberCreateText.text.toString()
                    )
                )
            )
            (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
                false
            )
            router.backTo(ContactScreen())
        }

    }

    private fun getImage() {
        val intent = Intent()
        intent.type = IMAGE_TYPE
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Choose Image"),
            REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                currentImagePath = uri.toString()
                Glide.with(this)
                    .load(currentImagePath)
                    .into(createImageView)
            }
        }
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
            }
            STATUS.CONTENT -> {

            }
            STATUS.ERROR -> {
            }
        }
    }

}