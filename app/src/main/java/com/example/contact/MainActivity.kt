package com.example.contact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contact.maincontactscreen.di.CONTACTS_QUALIFIER
import com.example.contact.navigation.HolderFragment
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class MainActivity : AppCompatActivity() {

    private val router: Router by inject(named(CONTACTS_QUALIFIER))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, HolderFragment.newInstance())
            .commit()
    }
    override fun onBackPressed() {
        router.exit()
    }
}