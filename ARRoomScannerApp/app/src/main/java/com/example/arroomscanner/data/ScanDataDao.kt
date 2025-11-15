
package com.example.arroomscanner.data

import androidx.room.*

@Dao
interface ScanDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scanData: ScanData)

    @Query("SELECT * FROM scan_data ORDER BY timestamp DESC")
    suspend fun getAllScans(): List<ScanData>

    @Query("SELECT * FROM scan_data WHERE id = :id")
    suspend fun getScanById(id: Int): ScanData?

    @Delete
    suspend fun delete(scanData: ScanData)
}
