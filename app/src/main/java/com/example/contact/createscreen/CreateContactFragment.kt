package com.example.contact.createscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.contact.R
import com.example.contact.base.IMAGE_TYPE
import com.example.contact.base.RESULT_AMOUNT
import com.example.contact.base.checkPermissionForReadFromStorage
import com.example.contact.base.requestPermissionForReadFromStorage
import com.example.contact.editcontactscreen.ui.UiEvent
import com.example.contact.maincontactscreen.di.CONTACTS_QUALIFIER
import com.example.contact.maincontactscreen.ui.ContactScreen
import kotlinx.android.synthetic.main.fragment_create_contact.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class CreateContactFragment : Fragment(R.layout.fragment_create_contact) {

    companion object {
        fun newInstance() = CreateContactFragment()
    }

    private val currentImgPath = ""
    private val viewModel: CreateContactViewModel by viewModel()
    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(currentImgPath)
            .into(createImageView)

        createImageView.setOnClickListener {
            if (checkPermissionForReadFromStorage()) {
                getImage()
            } else {
                requestPermissionForReadFromStorage()
            }
        }

        createContactButton.setOnClickListener {
            viewModel.processUiEvent(
                UiEvent.OnCreateContact(
                    currentImgPath,
                    nameCreateText.text.toString(),
                    surnameCreateText.text.toString(),
                    numberCreateText.text.toString()
                )
            )
            router.backTo(ContactScreen())
        }

    }

    private fun getImage() {
        val intent = Intent()
        intent.type = IMAGE_TYPE
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Choose Image"), RESULT_AMOUNT
        )
    }

}