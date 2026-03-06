# Database-KMP Architecture

## Design Principles

1. **Interface-First** - `NetworkStorageDatabase` is an interface; implementations are in consuming projects
2. **Pure Domain Types** - No serialization annotations, no platform dependencies beyond coroutines
3. **Epoch Millis** - Timestamps use `Long` to avoid kotlinx-datetime dependency
4. **Immutable Entities** - All entity modifications via `with*()` copy methods

## Type Hierarchy

```
NetworkStorageDatabase (interface)
  ├── operates on: NetworkStorage (storage locations)
  ├── operates on: NetworkDocument (files/folders)
  ├── operates on: CacheEntry (local cache)
  ├── operates on: NetworkOperation (async operations)
  └── uses: SyncStatus, StorageType (enums)

DocumentPermission (enum)
  ├── isReadPermission
  ├── isWritePermission
  └── isAdministrativePermission
```

## Data Flow

1. Storage locations registered via `insertStorage()`
2. Documents listed/synced and tracked via `insert/updateDocument()`
3. Downloaded files cached via `insertCacheEntry()`
4. All async work tracked via `insert/updateOperation()`
5. Sync state tracked via `updateSyncStatus()`
