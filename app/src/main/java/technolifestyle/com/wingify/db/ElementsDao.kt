package technolifestyle.com.wingify.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import technolifestyle.com.wingify.model.ElementModel

@Dao
interface ElementsDao {
    @Insert
    fun addElement(element: ElementModel)

    @Query("Select id from elements where element = :element")
    fun getElement(element: String): Int?

    @Query("Update elements Set count = 0 And unsentFlag = 0 where element = :element")
    fun resetElementCount(element: String): Int

    @Query("Update elements Set count = count+1 where element = :element")
    fun updateElementCount(element: String): Int

    @Query("Select * from elements where unsentFlag = 1")
    fun getUnsentElements(): List<ElementModel>
}