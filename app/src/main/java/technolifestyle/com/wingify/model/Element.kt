package technolifestyle.com.wingify.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "elements")
data class ElementModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val element: String,
    val count: Int = 1,
    val unsentFlag: Boolean = false
)