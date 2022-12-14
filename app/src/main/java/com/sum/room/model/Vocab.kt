package com.sum.room.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "vocab_table")
data class Vocab (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val vocabWord: String,
    val vocabDef: String
): Parcelable