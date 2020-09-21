package com.example.contact.createscreen

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class CreateContactScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return CreateContactFragment()
    }
}