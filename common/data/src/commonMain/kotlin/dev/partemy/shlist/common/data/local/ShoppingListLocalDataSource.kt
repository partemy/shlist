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
import kotlinx.coroutines.flow.map

class ShoppingListLocalDataSource(
    private val dao: ShoppingListDao,
) : IShoppingListLocalDataSource {

    override fun getAllLists(): Flow<List<ShoppingList>> =
        dao.getAllLists().map { it.map { it.list.toShoppingList() } }

    override fun getAllListItems(listId: Int): Flow<List<ShoppingListItem>> =
        dao.getAllLists().map { list ->
            list
                .filter { it.list.id == listId }
                .flatMap { it.items.toShoppingListItems() }
        }

    override suspend fun insertList(list: ShoppingList) = dao.insertList(list.toShoppingListDBO())

    override suspend fun insertLists(lists: List<ShoppingList>) =
        dao.insertLists(lists.toShoppingListsBDO())

    override suspend fun insertListItem(item: ShoppingListItem) =
        dao.insertListItem(item.toShoppingListItemDBO())

    override suspend fun insertListItems(item: List<ShoppingListItem>) =
        dao.insertListItems(item.map { it.toShoppingListItemDBO() })

    override suspend fun replaceAllLists(lists: List<ShoppingList>) =
        dao.replaceAllLists(lists.toShoppingListsBDO())

    override suspend fun replaceListItems(items: List<ShoppingListItem>, listId: Int) =
        dao.replaceListItems(items.map { it.toShoppingListItemDBO() }, listId)

    override suspend fun deleteList(listId: Int) = dao.deleteList(listId)

    override suspend fun deleteListItem(item: ShoppingListItem) =
        dao.deleteListItem(item.toShoppingListItemDBO())

    override suspend fun crossOutListItem(id: Int, isCrossed: Boolean) =
        dao.crossOutListItem(id, isCrossed)

}