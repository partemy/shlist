package dev.partemy.shlist.common.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import dev.partemy.shlist.common.database.entity.ShoppingListItemDBO
import dev.partemy.shlist.common.database.entity.ShoppingListDBO


data class ShoppingListWithItemsDBO(
    @Embedded val list: ShoppingListDBO,
    @Relation(
        parentColumn = "id",
        entityColumn = "list_id"
    )
    val items: List<ShoppingListItemDBO>
)
