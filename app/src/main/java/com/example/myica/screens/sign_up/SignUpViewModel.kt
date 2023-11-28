package com.example.myica.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import com.example.myica.common.ext.isValidEmail
import com.example.myica.common.ext.isValidPassword
import com.example.myica.common.ext.passwordMatches
import com.example.myica.common.snackbar.SnackbarManager
import com.example.myica.model.service.AccountService
import com.example.myica.model.service.LogService
import com.example.myica.navigation.LOGIN_SCREEN
import com.example.myica.navigation.SETTINGS_SCREEN
import com.example.myica.navigation.SIGN_UP_SCREEN
import com.example.myica.screens.TodoListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.myica.R.string as AppText


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : TodoListViewModel(logService) {

    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email

    private val passsword
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!passsword.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!passsword.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            accountService.linkAccount(email, passsword)
            openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
        }
    }

    fun backToLogin(openScreen: (String, String) -> Unit) {

        launchCatching {
            openScreen(LOGIN_SCREEN,SIGN_UP_SCREEN)
        }
    }

}