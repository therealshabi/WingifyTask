package technolifestyle.com.wingify.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import technolifestyle.com.wingify.model.ElementModel

@Database(entities = [ElementModel::class], version = 1)
abstract class ElementDatabase : RoomDatabase() {

    abstract fun getElementDao(): ElementsDao

    companion object {
        private var INSTANCE: ElementDatabase? = null

        fun getDatabase(context: Context): ElementDatabase? {
            if (INSTANCE == null) {
                synchronized(ElementDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ElementDatabase::class.java,
                            "elementsDatabase"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}