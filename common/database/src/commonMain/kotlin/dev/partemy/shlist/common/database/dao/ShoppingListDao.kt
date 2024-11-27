package dev.partemy.shlist.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.partemy.shlist.common.database.entity.ShoppingListDBO
import dev.partemy.shlist.common.database.entity.ShoppingListItemDBO
import dev.partemy.shlist.common.database.relation.ShoppingListWithItemsDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shopping_list")
    fun getAllLists(): Flow<List<ShoppingListWithItemsDBO>>

    @Query("DELETE FROM shopping_list WHERE id = :listId")
    suspend fun deleteList(listId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: ShoppingListDBO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLists(list: List<ShoppingListDBO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListItems(item: List<ShoppingListItemDBO>)

    @Query("DELETE FROM shopping_list")
    suspend fun clearLists()

    @Query("DELETE FROM list_items WHERE list_id = :listId")
    suspend fun clearListItems(listId: Int)

    @Query("DELETE FROM list_items")
    suspend fun clearAllListItems()

    @Transaction
    suspend fun replaceAllLists(newList: List<ShoppingListDBO>) {
        clearLists()
        insertLists(newList)
    }

    @Transaction
    suspend fun replaceListItems(items: List<ShoppingListItemDBO>, listId: Int) {
        clearListItems(listId)
        insertListItems(items)
    }

    @Transaction
    suspend fun clearAll() {
        clearLists()
        clearAllListItems()
    }
}