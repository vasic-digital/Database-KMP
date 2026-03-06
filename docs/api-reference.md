# Database-KMP API Reference

## NetworkStorageDatabase (interface)

### Storage
- `initialize(): Result<Unit>`
- `close(): Result<Unit>`
- `insertStorage(storage: NetworkStorage): Result<Unit>`
- `updateStorage(storage: NetworkStorage): Result<Unit>`
- `getStorage(id: String): Result<NetworkStorage?>`
- `getAllStorage(): Result<List<NetworkStorage>>`
- `deleteStorage(id: String): Result<Unit>`

### Documents
- `insertDocument(document: NetworkDocument): Result<Unit>`
- `updateDocument(document: NetworkDocument): Result<Unit>`
- `getDocument(id: String): Result<NetworkDocument?>`
- `getDocumentsByStorage(storageId: String): Result<List<NetworkDocument>>`
- `getDocumentsByPath(path: String): Result<List<NetworkDocument>>`
- `deleteDocument(id: String): Result<Unit>`
- `observeDocumentsByStorage(storageId: String): Flow<List<NetworkDocument>>`

### Cache
- `insertCacheEntry(entry: CacheEntry): Result<Unit>`
- `updateCacheEntry(entry: CacheEntry): Result<Unit>`
- `getCacheEntry(id: String): Result<CacheEntry?>`
- `getCacheEntriesByDocument(documentId: String): Result<List<CacheEntry>>`
- `getAllCacheEntries(): Result<List<CacheEntry>>`
- `deleteCacheEntry(id: String): Result<Unit>`
- `deleteExpiredCacheEntries(): Result<Int>`
- `getCacheUsage(): Result<Long>`

### Operations
- `insertOperation(operation: NetworkOperation): Result<Unit>`
- `updateOperation(operation: NetworkOperation): Result<Unit>`
- `getOperation(id: Long): Result<NetworkOperation?>`
- `getActiveOperations(): Result<List<NetworkOperation>>`
- `deleteOperation(id: Long): Result<Unit>`
- `clearCompletedOperations(): Result<Int>`

### Sync Status
- `updateSyncStatus(remotePath: String, status: SyncStatus): Result<Unit>`
- `getSyncStatus(remotePath: String): Result<SyncStatus?>`
- `getAllSyncStatus(): Result<Map<String, SyncStatus>>`
- `deleteSyncStatus(remotePath: String): Result<Unit>`

### Cleanup
- `clearAll(): Result<Unit>`
- `vacuum(): Result<Unit>`

## Enums

| Enum | Entries |
|------|---------|
| StorageType | WEBDAV, FTP, SFTP, SMB, GOOGLE_DRIVE, DROPBOX, ONEDRIVE, GIT |
| SyncStatus | UNKNOWN, SYNCED, PENDING_UPLOAD, PENDING_DOWNLOAD, SYNCING, SYNC_ERROR, NOT_SYNCED, QUEUED, CONFLICT, UPLOADING, DOWNLOADING |
| ConflictResolution | LOCAL_WINS, REMOTE_WINS, KEEP_BOTH, MANUAL, SKIP |
| DocumentPermission | READ, WRITE, DELETE, CREATE, RENAME, MOVE, COPY, SHARE, MANAGE_PERMISSIONS, VIEW_METADATA, MODIFY_METADATA, EXECUTE, DOWNLOAD, UPLOAD, SYNC, ADMIN |
| OperationType | UPLOAD, DOWNLOAD, DELETE, CREATE_FOLDER, RENAME, MOVE, COPY, SYNC, SEARCH |
| OperationStatus | PENDING, IN_PROGRESS, COMPLETED, FAILED, PAUSED, CANCELLED |
| DocumentType | FILE, FOLDER, UNKNOWN |
