package com.example.contact.createscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.contact.R
import com.example.contact.base.IMAGE_TYPE
import com.example.contact.base.REQUEST_CODE
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

    private var currentImagePath = ""
    private val viewModel: CreateContactViewModel by viewModel()
    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
                router.backTo(ContactScreen())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setTitle(R.string.create_contact_toolbar_title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_account_circle_24))
        createImageView.setOnClickListener {
            if (!checkPermissionForReadFromStorage()) {
                requestPermissionForReadFromStorage()
            }
            if(checkPermissionForReadFromStorage()){
                getImage()
            }
        }

        createContactButton.setOnClickListener {
            viewModel.processUiEvent(
                UiEvent.OnCreateContact(
                    currentImagePath,
                    nameCreateText.text.toString(),
                    surnameCreateText.text.toString(),
                    numberCreateText.text.toString()
                )
            )
            (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
                data?.data?.let {
                    uri -> currentImagePath = uri.toString()
                    Glide.with(this)
                        .load(currentImagePath)
                        .into(createImageView)
                }
        }
    }

}