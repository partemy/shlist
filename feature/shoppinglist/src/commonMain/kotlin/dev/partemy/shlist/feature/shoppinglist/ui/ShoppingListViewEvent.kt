package dev.partemy.shlist.feature.shoppinglist.ui

import dev.partemy.shlist.ui.base.IViewEvent

sealed class ShoppingListViewEvent : IViewEvent {
    class AddItem(val name: String, val count: Int) : ShoppingListViewEvent()
    class DeleteItem(val itemId: Int) : ShoppingListViewEvent()
    class CrossOutItem(val itemId: Int) : ShoppingListViewEvent()
    class SnackBackError(val message: String) : ShoppingListViewEvent()
}