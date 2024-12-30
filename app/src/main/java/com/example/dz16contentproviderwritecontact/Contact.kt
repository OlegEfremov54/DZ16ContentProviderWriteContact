package com.example.dz16contentproviderwritecontact
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey val name: String,
    val surname: String,
    val phone: String,
    val timeStamp: String
) {
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0
}
