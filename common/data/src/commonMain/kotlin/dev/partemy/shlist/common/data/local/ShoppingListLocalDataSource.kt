package dev.partemy.shlist.common.data.local

import dev.partemy.shlist.common.database.dao.ShoppingListDao
import dev.partemy.shlist.common.database.mapper.toShoppingList
import dev.partemy.shlist.common.database.mapper.toShoppingListDBO
import dev.partemy.shlist.common.database.mapper.toShoppingListItemDBO
import dev.partemy.shlist.common.database.mapper.toShoppingListItems
import dev.partemy.shlist.common.database.mapper.toShoppingListsBDO
import dev.partemy.shlist.common.domain.model.ShoppingList
import dev.partemy.shlist.common.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ShoppingListLocalDataSource(
    private val dao: ShoppingListDao,
  //  private val preferences: Preferences,
) : IShoppingListLocalDataSource {
    override fun getKey() = flow { emit("") } //preferences.getString("key")

    override fun getAllLists(): Flow<List<ShoppingList>> =
        dao.getAllLists().map { it.map { it.list.toShoppingList() } }

    override fun getAllListItems(listId: Int): Flow<List<ShoppingListItem>> =
        dao.getAllLists().map { list ->
            list
                .filter { it.list.id == listId }
                .flatMap { it.items.toShoppingListItems() }
        }

    override suspend fun insertList(list: ShoppingList) = dao.insertList(list.toShoppingListDBO())

    override suspend fun insertLists(lists: List<ShoppingList>) = dao.insertLists(lists.toShoppingListsBDO())

    override suspend fun insertListItem(item: ShoppingListItem) =
        dao.insertListItem(item.toShoppingListItemDBO())

    override suspend fun deleteList(list: ShoppingList) = dao.deleteList(list.toShoppingListDBO())

    override suspend fun deleteListItem(item: ShoppingListItem) =
        dao.deleteListItem(item.toShoppingListItemDBO())

    override suspend fun crossOutListItem(id: Int, isCrossed: Boolean) =
        dao.crossOutListItem(id, isCrossed)

}