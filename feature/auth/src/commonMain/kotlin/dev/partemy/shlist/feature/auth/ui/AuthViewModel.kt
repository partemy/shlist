package dev.partemy.shlist.feature.auth.ui

import androidx.lifecycle.viewModelScope
import dev.partemy.shlist.common.domain.usecase.CreateKeyUseCase
import dev.partemy.shlist.common.domain.usecase.VerifyKeyUseCase
import dev.partemy.shlist.ui.base.BaseViewModel
import dev.partemy.shlist.ui.base.IViewEvent
import kotlinx.coroutines.launch

class AuthViewModel(
    private val createKeyUseCase: CreateKeyUseCase,
    private val verifyKeyUseCase: VerifyKeyUseCase,
) : BaseViewModel<AuthViewState, AuthViewEvent>(
) {
    override fun createInitialState(): AuthViewState = AuthViewState

    override fun onTriggerEvent(event: AuthViewEvent) {
        when (event) {
            is AuthViewEvent.Auth -> auth(event.key)
            is AuthViewEvent.SnackBarError -> { }
            is AuthViewEvent.SuccessAuth -> {}
        }
    }

    private fun auth(key: String?) = viewModelScope.launch {
        val result = if (key == null) createKeyUseCase.invoke() else verifyKeyUseCase.invoke(key)
        if (result.isFailure) setEvent(
            AuthViewEvent.SnackBarError(
                message = result.exceptionOrNull().toString()
            )
        )
        if (result.isSuccess) setEvent(AuthViewEvent.SuccessAuth())
    }

}

sealed class AuthViewEvent() : IViewEvent {
    class Auth(val key: String?) : AuthViewEvent()
    class SnackBarError(val message: String) : AuthViewEvent()
    class SuccessAuth() : AuthViewEvent()
}