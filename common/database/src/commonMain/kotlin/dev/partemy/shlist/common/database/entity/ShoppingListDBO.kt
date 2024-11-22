package dev.partemy.shlist.common.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingListDBO(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val name: String,
    val created: String,
)
