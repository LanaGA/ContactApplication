package com.example.contact.createscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import com.example.contact.contact.ui.STATUS
import com.example.contact.contact.ui.UiEvent
import com.example.contact.contact.ui.ViewState
import com.example.contact.contact.ui.model.ContactModel
import com.example.contact.maincontactscreen.di.CONTACTS_QUALIFIER
import com.example.contact.maincontactscreen.ui.ContactScreen
import kotlinx.android.synthetic.main.fragment_create_contact.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class CreateContactFragment : Fragment(R.layout.fragment_create_contact) {

    private var currentImagePath = ""
    private val viewModel: CreateContactViewModel by viewModel()
    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        createImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_account_circle_24))
        createImageView.setOnClickListener {
            if (checkPermissionForReadFromStorage()) {
                getImage()
            } else {
                requestPermissionForReadFromStorage()
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
            router.navigateTo(ContactScreen())
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (checkPermissionForReadFromStorage()) {
            getImage()
        }
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