# Database-KMP User Guide

## Overview

Database-KMP provides the interface and entity types for network storage metadata persistence. Implement `NetworkStorageDatabase` to store storage locations, documents, cache entries, and operations.

## Entity Types

### NetworkStorage
Represents a storage location (WebDAV, FTP, Google Drive, etc.):
- Computed: `availableSpace`, `usagePercentage`, `isFull`, `isLowOnSpace`
- File filtering: `supportsExtension()`, `supportsFile()`
- Copy: `withOnlineStatus()`, `withSpaceInfo()`

### NetworkDocument
Represents a file or folder on network storage:
- File detection: `isTextFile`, `isImageFile`, `isPdfFile`, `isPreviewable`, `isEditable`
- Display: `formattedSize` (auto-formats B/KB/MB/GB)
- Path: `isInPath()`, `isDirectChildOf()`, `parentPath`
- Copy: `withSyncStatus()`, `withDocumentId()`, `withMetadata()`, `withPermissions()`

### CacheEntry
Represents a locally cached copy of a remote file:
- Eviction: `canBeEvicted`, `isExpired`
- Compression: `compressionRatio`
- Copy: `withAccess()`, `withNotInUse()`, `withExpiration()`, `withPinStatus()`

### NetworkOperation
Represents an in-progress network operation:
- Status: `isRunning`, `isCompleted`, `hasFailed`, `isPending`, `canRetry`
- Progress: `progressPercentage`, `duration`
- Copy: `withStatus()`, `withProgress()`, `withError()`, `withPauseStatus()`

## Database Interface

Implement `NetworkStorageDatabase` for your storage backend. All methods are suspend and return `Result<T>`. Document observation uses `Flow<List<NetworkDocument>>`.
