
package com.example.arroomscanner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_data")
data class ScanData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val scanName: String,
    val timestamp: Long,
    val damageLevel: String,
    val materialType: String,
    val notes: String
)
