package com.example.contact.editcontactscreen

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
import com.example.contact.editcontactscreen.ui.STATUS
import com.example.contact.editcontactscreen.ui.UiEvent
import com.example.contact.editcontactscreen.ui.ViewState
import com.example.contact.maincontactscreen.di.CONTACTS_QUALIFIER
import com.example.contact.maincontactscreen.ui.ContactScreen
import kotlinx.android.synthetic.main.fragment_create_contact.*
import kotlinx.android.synthetic.main.fragment_edit_contact.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router


class EditContactFragment : Fragment(R.layout.fragment_edit_contact) {

    companion object {
        private const val NUMBER = "NUMBER"
        fun newInstance(id: String): EditContactFragment {
            val bundle = Bundle()
            val fragment = EditContactFragment()
            bundle.putString(NUMBER, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var currentImagePath = ""
    private val viewModel: EditContactViewModel by viewModel()
    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setTitle(R.string.edit_contact_toolbar_title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        val selectedNumber = arguments?.getString(NUMBER)
        viewModel.processUiEvent(UiEvent.OnRequestContact(selectedNumber!!))

        editImageView.setOnClickListener {
            if (checkPermissionForReadFromStorage()) {
                getImage()
            } else {
                requestPermissionForReadFromStorage()
            }
        }

        editContactButton.setOnClickListener {
            viewModel.processUiEvent(
                UiEvent.OnUpdateContact(
                    currentImagePath,
                    nameEditText.text.toString(),
                    surnameEditText.text.toString(),
                    numberEditText.text.toString()
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
            Intent.createChooser(intent, "Choose Image"), REQUEST_CODE
        )
    }

    private fun render(viewState: ViewState){
        when(viewState.status){
            STATUS.LOAD -> {
            }
            STATUS.CONTENT -> {
                val model = viewState.contactModel
                currentImagePath = model?.pathToImage ?: ""
                if(currentImagePath == "")
                {
                    createImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_account_circle_24))
                }
                else Glide.with(this)
                    .load(currentImagePath)
                    .into(editImageView)
                nameEditText.setText((model?.name + " " + model?.surname))
                numberEditText.setText(model?.number)
            }
            STATUS.ERROR -> {
            }
        }
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