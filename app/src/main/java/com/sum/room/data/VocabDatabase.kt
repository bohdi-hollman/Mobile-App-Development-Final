package com.sum.room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sum.room.model.Vocab

@Database(entities = [Vocab::class], version = 1, exportSchema = false)
abstract class VocabDatabase: RoomDatabase() {

    abstract fun vocabDao(): VocabDao

    companion object{
        @Volatile
        private var INSTANCE: VocabDatabase? = null

        fun getDatabase(context: Context): VocabDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VocabDatabase::class.java,
                    "vocab_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}