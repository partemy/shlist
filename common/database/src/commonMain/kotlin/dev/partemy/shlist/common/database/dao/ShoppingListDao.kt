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

    @Query("DELETE FROM shopping_list WHERE id = :listId")
    suspend fun deleteList(listId: Int)

    @Delete
    suspend fun deleteListItem(item: ShoppingListItemDBO)

    @Query("SELECT * FROM shopping_list")
    fun getAllLists(): Flow<List<ShoppingListWithItemsDBO>>

    @Query("UPDATE list_items SET isCrossed = :isCrossed WHERE id = :id")
    suspend fun crossOutListItem(id: Int, isCrossed: Boolean)

    @Query("DELETE FROM shopping_list")
    suspend fun clearTable()

    @Transaction
    suspend fun replaceAllLists(newList: List<ShoppingListDBO>) {
        clearTable()
        insertLists(newList)
    }

}