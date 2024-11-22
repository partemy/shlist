package dev.partemy.shlist.common.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_items")
data class ShoppingListItemDBO(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "list_id") val listId: Int,
    val name: String,
    val isCrossed: Boolean,
    val created: String,
)