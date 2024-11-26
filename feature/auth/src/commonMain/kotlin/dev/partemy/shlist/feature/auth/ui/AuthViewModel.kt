package dev.partemy.shlist.feature.auth.ui

import androidx.lifecycle.viewModelScope
import dev.partemy.shlist.common.domain.repository.IAuthRepository
import dev.partemy.shlist.common.domain.usecase.CreateKeyUseCase
import dev.partemy.shlist.common.domain.usecase.VerifyKeyUseCase
import dev.partemy.shlist.ui.base.BaseViewModel
import dev.partemy.shlist.ui.base.IViewEvent
import kotlinx.coroutines.flow.MutableStateFlow
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
        }
    }

    private fun auth(key: String?) = viewModelScope.launch {
        val result = if (key == null) createKeyUseCase.invoke() else verifyKeyUseCase.invoke(key)
        if (result.isFailure) text.value = result.exceptionOrNull().toString()
    }

    var text = MutableStateFlow("")
}

sealed class AuthViewEvent() : IViewEvent {
    class Auth(val key: String?) : AuthViewEvent()
}