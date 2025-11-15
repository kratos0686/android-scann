# Copilot Instructions: `android-scann`
Focused, update-on-change guidance for AI coding agents (target ≤50 lines).

## Architecture Snapshot
Single module `ARRoomScannerApp/app`. Compose UI (`ScannerScreen.kt`) hosted by `MainActivity.kt`. View state + business logic intended in `ScannerViewModel.kt` (MVVM pattern). Persistence via Room (`AppDatabase.kt`, `ScanDataDao.kt`, `ScanData.kt`) storing scan metadata (name, timestamp, damageLevel, materialType, notes). Future (placeholder) integrations: ARCore Depth, Firebase Realtime DB, ML Kit, RxJava – only implement after real deps appear in `build.gradle`.

## Key Files
`AndroidManifest.xml`: camera + network perms; add `<uses-feature android:name="android.hardware.camera.ar" />` when Depth used.
`AppDatabase.kt`: thread-safe singleton, name `scan_database`.
`ScanData.kt`: extend by bumping DB version + migration (no destructive drops).
`ScanDataDao.kt`: suspend CRUD; ordering by `timestamp DESC`.

## Data Flow (Current)
Compose -> ViewModel -> DAO suspend ops -> Room -> recomposition.
Planned: ARCore frame -> processing/ML -> create `ScanData` -> optional Firebase sync.

## Dependencies Snapshot (placeholder)
Add actual versions once present, e.g.: Room, Compose BOM, ARCore, Firebase Database, ML Kit, RxJava3. Keep versions centralized (catalog or constants).

## Threading & State
DAO methods are suspend; lightweight inserts fine on main if Room optimizes, but heavy pre-processing (image/depth) must switch to `Dispatchers.IO`. Example ViewModel exposure:
```kotlin
val scans: StateFlow<List<ScanData>> // collected by Compose
```
Map entities to UI models before exposure if transformation needed.

## Migrations Template
```kotlin
val MIGRATION_1_2 = object: Migration(1,2) { override fun migrate(db: SupportSQLiteDatabase) {
	db.execSQL("ALTER TABLE scan_data ADD COLUMN confidence REAL NOT NULL DEFAULT 0.0")
}}
```
Register via `Room.databaseBuilder(...).addMigrations(MIGRATION_1_2)`.

## ARCore & Firebase TODOs
Before adding code: grep for `TODO(ARCore)` / `TODO(Firebase)` / `TODO(Depth)` to avoid duplicate scaffolding. Avoid uploading raw depth arrays; persist summaries + store raw in Cloud Storage later.

## Build & Run (PowerShell)
```powershell
./gradlew clean assembleDebug
./gradlew :ARRoomScannerApp:app:installDebug
adb shell am start -n com.example.arroomscanner/.MainActivity
```
Adjust module notation if structure changes.

## Testing (future)
Unit tests: `app/src/test` for ViewModel/database logic (use in-memory Room). Instrumented: `app/src/androidTest` for DAO + UI. Add migration test opening pre-populated v1 then upgrading to v2.

## PR Guidance
Match existing patterns (Compose + MVVM + Room). Keep changes minimal. Add migrations with defaults; never drop tables for schema changes. Document assumptions when introducing ARCore/Firebase.

---
Maintain this file instead of creating new agent docs; update when dependencies or architecture shift.
