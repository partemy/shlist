package dev.partemy.shlist.feature.main.ui

import dev.partemy.shlist.ui.base.IViewEvent

sealed class MainViewEvent : IViewEvent {
    class CreateList(val name: String) : MainViewEvent()
    class DeleteList(val listId: Int) : MainViewEvent()
    class LogOut() : MainViewEvent()
    class SnackBackError(val message: String) : MainViewEvent()
}