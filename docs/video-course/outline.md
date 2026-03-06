# Database-KMP Video Course Outline

## Module 1: Introduction (5 min)
- What Database-KMP provides
- Interface vs implementation separation

## Module 2: Entity Types (15 min)
- NetworkStorage: space tracking, extension filtering
- NetworkDocument: file detection, sync status, permissions
- CacheEntry: eviction, compression, pin status
- NetworkOperation: progress, retry, status lifecycle

## Module 3: Enums and Permissions (10 min)
- StorageType, SyncStatus, OperationType/Status
- DocumentPermission with role helpers
- ConflictResolution strategies

## Module 4: Database Interface (10 min)
- CRUD operations for each entity
- Flow-based document observation
- Sync status management
- Cache and cleanup operations

## Module 5: Implementation Guide (15 min)
- Implementing NetworkStorageDatabase
- SQLite-based implementation
- In-memory implementation for testing

## Module 6: Integration (10 min)
- Adding to a KMP project
- Using with Storage-KMP and Config-KMP
