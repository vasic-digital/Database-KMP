# Database-KMP

Kotlin Multiplatform network storage database interfaces and entity types.

## Features

- **NetworkStorageDatabase** - Interface for storage metadata persistence (CRUD, sync, cache, operations)
- **Entity Types** - NetworkStorage, NetworkDocument, CacheEntry, NetworkOperation
- **Enums** - StorageType, SyncStatus, DocumentPermission, OperationType, OperationStatus, DocumentType, ConflictResolution
- **Computed Properties** - Available space, usage percentage, file type detection, formatted sizes

## Platforms

- Android
- Desktop (JVM)
- iOS (x64, arm64, simulator)
- Web (Wasm/JS)

## Installation

```bash
git submodule add git@github.com:vasic-digital/Database-KMP.git
```

Then in your `settings.gradle.kts`:

```kotlin
includeBuild("Database-KMP")
```

## Usage

```kotlin
import digital.vasic.database.*

// Entity creation
val storage = NetworkStorage(
    id = "s1",
    name = "My WebDAV",
    type = StorageType.WEBDAV,
    location = "https://dav.example.com"
)

val doc = NetworkDocument(
    id = "d1",
    name = "readme.md",
    path = "/docs/readme.md",
    size = 1024,
    storageId = "s1"
)

// Computed properties
storage.availableSpace     // total - used
storage.usagePercentage    // 0.0..1.0
doc.isTextFile             // true for .md
doc.formattedSize          // "1KB"

// Immutable modifications
val online = storage.withOnlineStatus(true)
val synced = doc.withSyncStatus(SyncStatus.SYNCED)
```

## License

Apache-2.0
