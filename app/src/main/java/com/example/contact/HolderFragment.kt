package com.example.contact

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.contact.maincontactscreen.di.CONTACTS_QUALIFIER
import com.example.contact.maincontactscreen.ui.ContactScreen
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class HolderFragment : Fragment(R.layout.fragment_main) {

    private val navigator: Navigator by lazy { createNavigator() }
    private val router: Router by inject(named(CONTACTS_QUALIFIER))
    private val navigatorHolder: NavigatorHolder by inject(named(CONTACTS_QUALIFIER))

    private fun createNavigator(): Navigator {
        return SupportAppNavigator(requireActivity(), childFragmentManager, R.id.fragmentHolder)
    }

    companion object {
        fun newInstance() = HolderFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router.navigateTo(ContactScreen())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}