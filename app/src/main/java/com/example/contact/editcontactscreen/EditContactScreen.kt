package com.example.contact.editcontactscreen

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class EditContactScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return EditContactFragment()
    }
}