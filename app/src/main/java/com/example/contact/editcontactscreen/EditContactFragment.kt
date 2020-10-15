package com.example.contact.editcontactscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.contact.R
import com.example.contact.base.IMAGE_TYPE
import com.example.contact.base.REQUEST_CODE
import com.example.contact.base.checkPermissionForReadFromStorage
import com.example.contact.base.requestPermissionForReadFromStorage
import com.example.contact.contact.ui.STATUS
import com.example.contact.contact.ui.UiEvent
import com.example.contact.contact.ui.ViewState
import com.example.contact.contact.ui.model.ContactModel
import com.example.contact.maincontactscreen.di.CONTACTS_QUALIFIER
import com.example.contact.maincontactscreen.ui.ContactScreen
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_create_contact.*
import kotlinx.android.synthetic.main.fragment_edit_contact.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router


class EditContactFragment(private val number: String) : Fragment(R.layout.fragment_edit_contact) {

    private var currentImagePath = ""
    private val viewModel: EditContactViewModel by viewModel()
    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        viewModel.processUiEvent(UiEvent.RequestContact(number))
        editImageView.setOnClickListener {
            if (checkPermissionForReadFromStorage()) {
                getImage()
            } else {
                requestPermissionForReadFromStorage()
            }
        }

        editContactButton.setOnClickListener {
            viewModel.processUiEvent(
                UiEvent.UpdateContact(
                    ContactModel(
                        currentImagePath,
                        nameEditText.text.toString(),
                        surnameEditText.text.toString(),
                        numberEditText.text.toString()
                    )
                )
            )
            router.navigateTo(ContactScreen())
        }
    }

    private fun getImage() {
        val intent = Intent()
        intent.type = IMAGE_TYPE
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Choose Image"), REQUEST_CODE
        )
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
                editImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_account_circle_24))
            }
            STATUS.CONTENT -> {
                val model = viewState.contactModel
                currentImagePath = model?.pathToImage ?: ""
                if(currentImagePath != "")
                Glide.with(this)
                    .load(currentImagePath)
                    .into(editImageView)
                nameEditText.setText(model?.name)
                surnameEditText.setText(model?.surname)
                numberEditText.setText(model?.number)
            }
            STATUS.ERROR -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                currentImagePath = uri.toString()
                Glide.with(this)
                    .load(currentImagePath)
                    .into(editImageView)
            }
        }
    }
}