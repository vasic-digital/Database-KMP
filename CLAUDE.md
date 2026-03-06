# Database-KMP

## Project Overview

Kotlin Multiplatform network storage database interfaces and entity types. Package: `digital.vasic.database`.

## Build Commands

```bash
./gradlew desktopTest    # Run tests
./gradlew build          # Build all targets
```

## Architecture

- `NetworkStorageDatabase.kt` - Interface for storage metadata persistence (Flow-based observation)
- `NetworkStorage.kt` - Storage location entity with computed space/usage properties
- `NetworkDocument.kt` - Document entity with file type detection, size formatting, path operations
- `CacheEntry.kt` - Cache entry entity with eviction logic, compression tracking
- `NetworkOperation.kt` - Operation entity with progress, retry, status tracking
- `StorageType.kt` - 8 storage protocol types
- `SyncStatus.kt` - 11 sync statuses + ConflictResolution enum
- `DocumentPermission.kt` - 16 permissions with role-based helpers
- `OperationEnums.kt` - OperationType, OperationStatus, DocumentType

## Key Patterns

- All entity types are immutable data classes with `with*()` copy methods
- Timestamps use Long (epoch millis) — no kotlinx-datetime dependency
- NetworkStorageDatabase uses kotlinx.coroutines.flow.Flow for observation
- No serialization annotations — pure domain types

## Dependencies

- kotlinx-coroutines-core (Flow in database interface)
