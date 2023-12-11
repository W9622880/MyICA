package com.example.myica.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.example.myica.model.service.AccountService
import com.example.myica.model.service.ConfigurationService
import com.example.myica.model.service.LogService
import com.example.myica.navigation.PLANS_SCREEN
import com.example.myica.navigation.SPLASH_SCREEN
import com.example.myica.screens.TodoListViewModel
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService
) : TodoListViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false
        if (accountService.hasUser) openAndPopUp(PLANS_SCREEN, SPLASH_SCREEN)
        else createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackbar = false) {
            try {
                accountService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
            openAndPopUp(PLANS_SCREEN, SPLASH_SCREEN)
        }
    }
}