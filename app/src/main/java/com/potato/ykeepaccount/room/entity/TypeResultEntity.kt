package com.potato.ykeepaccount.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TypeResultEntity(
    @Embedded
    val type : TypeEntity,
    @Relation(
        parentColumn = "type_id",
        entityColumn = "primary_id"
    )
    val primaryType: TypeEntity
)