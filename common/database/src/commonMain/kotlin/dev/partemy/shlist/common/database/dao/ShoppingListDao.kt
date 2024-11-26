package dev.partemy.shlist.common.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.partemy.shlist.common.database.entity.ShoppingListItemDBO
import dev.partemy.shlist.common.database.entity.ShoppingListDBO
import dev.partemy.shlist.common.database.relation.ShoppingListWithItemsDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: ShoppingListDBO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLists(list: List<ShoppingListDBO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListItem(item: ShoppingListItemDBO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListItems(item: List<ShoppingListItemDBO>)

    @Query("DELETE FROM shopping_list")
    suspend fun clearLists()

    @Transaction
    suspend fun replaceAllLists(newList: List<ShoppingListDBO>) {
        clearLists()
        insertLists(newList)
    }

    @Query("DELETE FROM list_items WHERE list_id = :listId")
    suspend fun clearListItems(listId: Int)

    @Transaction
    suspend fun replaceListItems(items: List<ShoppingListItemDBO>, listId: Int) {
        clearListItems(listId)
        insertListItems(items)
    }

    @Query("DELETE FROM shopping_list WHERE id = :listId")
    suspend fun deleteList(listId: Int)

    @Delete
    suspend fun deleteListItem(item: ShoppingListItemDBO)

    @Query("SELECT * FROM shopping_list")
    fun getAllLists(): Flow<List<ShoppingListWithItemsDBO>>

    @Query("UPDATE list_items SET isCrossed = :isCrossed WHERE id = :id")
    suspend fun crossOutListItem(id: Int, isCrossed: Boolean)

    @Query("DELETE FROM list_items")
    suspend fun clearAllListItems()

    @Transaction
    suspend fun clearAll() {
        clearLists()
        clearAllListItems()
    }
}