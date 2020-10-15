package com.example.contact.maincontactscreen.ui

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ContactScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return ContactFragment()
    }
}