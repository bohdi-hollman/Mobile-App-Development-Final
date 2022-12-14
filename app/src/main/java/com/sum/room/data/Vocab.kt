package com.sum.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "vocab_table")
data class Vocab (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val vocabWord: String,
    val vocabDef: String
    )