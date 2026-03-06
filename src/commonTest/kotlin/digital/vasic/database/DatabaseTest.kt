package digital.vasic.database

import kotlin.test.*

class StorageTypeTest {
    @Test fun testAllStorageTypes() {
        assertEquals(8, StorageType.entries.size)
    }
    @Test fun testDisplayNames() {
        assertEquals("WebDAV", StorageType.WEBDAV.displayName)
        assertEquals("FTP", StorageType.FTP.displayName)
        assertEquals("SFTP", StorageType.SFTP.displayName)
        assertEquals("SMB/CIFS", StorageType.SMB.displayName)
        assertEquals("Google Drive", StorageType.GOOGLE_DRIVE.displayName)
        assertEquals("Dropbox", StorageType.DROPBOX.displayName)
        assertEquals("OneDrive", StorageType.ONEDRIVE.displayName)
        assertEquals("Git", StorageType.GIT.displayName)
    }
}

class SyncStatusTest {
    @Test fun testAllStatuses() {
        assertEquals(11, SyncStatus.entries.size)
    }
    @Test fun testConflictResolutions() {
        assertEquals(5, ConflictResolution.entries.size)
    }
}

class DocumentPermissionTest {
    @Test fun testAllPermissions() {
        assertEquals(16, DocumentPermission.entries.size)
    }
    @Test fun testReadPermissions() {
        assertTrue(DocumentPermission.READ.isReadPermission)
        assertTrue(DocumentPermission.VIEW_METADATA.isReadPermission)
        assertTrue(DocumentPermission.DOWNLOAD.isReadPermission)
        assertFalse(DocumentPermission.WRITE.isReadPermission)
    }
    @Test fun testWritePermissions() {
        assertTrue(DocumentPermission.WRITE.isWritePermission)
        assertTrue(DocumentPermission.CREATE.isWritePermission)
        assertTrue(DocumentPermission.UPLOAD.isWritePermission)
        assertFalse(DocumentPermission.READ.isWritePermission)
    }
    @Test fun testAdminPermissions() {
        assertTrue(DocumentPermission.ADMIN.isAdministrativePermission)
        assertTrue(DocumentPermission.DELETE.isAdministrativePermission)
        assertTrue(DocumentPermission.SYNC.isAdministrativePermission)
        assertFalse(DocumentPermission.READ.isAdministrativePermission)
    }
    @Test fun testDisplayNames() {
        assertEquals("Read", DocumentPermission.READ.displayName)
        assertEquals("Administrator", DocumentPermission.ADMIN.displayName)
        assertEquals("Manage Permissions", DocumentPermission.MANAGE_PERMISSIONS.displayName)
    }
    @Test fun testDefaultFolderPermissions() {
        assertEquals(10, DocumentPermission.DEFAULT_FOLDER_PERMISSIONS.size)
        assertTrue(DocumentPermission.DEFAULT_FOLDER_PERMISSIONS.contains(DocumentPermission.CREATE))
        assertTrue(DocumentPermission.DEFAULT_FOLDER_PERMISSIONS.contains(DocumentPermission.UPLOAD))
    }
    @Test fun testDefaultDocumentPermissions() {
        assertEquals(8, DocumentPermission.DEFAULT_DOCUMENT_PERMISSIONS.size)
        assertFalse(DocumentPermission.DEFAULT_DOCUMENT_PERMISSIONS.contains(DocumentPermission.CREATE))
    }
    @Test fun testReadOnlyPermissions() {
        assertEquals(3, DocumentPermission.READ_ONLY_PERMISSIONS.size)
    }
    @Test fun testHasPermissionWithAdmin() {
        val perms = setOf(DocumentPermission.ADMIN)
        assertTrue(DocumentPermission.hasPermission(perms, DocumentPermission.READ))
        assertTrue(DocumentPermission.hasPermission(perms, DocumentPermission.DELETE))
    }
    @Test fun testHasPermissionWithout() {
        val perms = setOf(DocumentPermission.READ)
        assertTrue(DocumentPermission.hasPermission(perms, DocumentPermission.READ))
        assertFalse(DocumentPermission.hasPermission(perms, DocumentPermission.DELETE))
    }
}

class OperationEnumsTest {
    @Test fun testOperationTypes() {
        assertEquals(9, OperationType.entries.size)
    }
    @Test fun testOperationStatuses() {
        assertEquals(6, OperationStatus.entries.size)
    }
    @Test fun testDocumentTypes() {
        assertEquals(3, DocumentType.entries.size)
    }
}

class NetworkStorageTest {
    @Test fun testCreation() {
        val storage = NetworkStorage(id = "1", name = "Test", type = StorageType.WEBDAV, location = "https://dav.test.com")
        assertEquals("1", storage.id)
        assertEquals("Test", storage.name)
        assertEquals(StorageType.WEBDAV, storage.type)
        assertFalse(storage.isOnline)
        assertTrue(storage.isEnabled)
        assertEquals(100, storage.priority)
    }
    @Test fun testAvailableSpace() {
        val storage = NetworkStorage(id = "1", name = "T", type = StorageType.FTP, location = "ftp://t", totalSpace = 1000, usedSpace = 300)
        assertEquals(700L, storage.availableSpace)
    }
    @Test fun testAvailableSpaceNull() {
        val storage = NetworkStorage(id = "1", name = "T", type = StorageType.FTP, location = "ftp://t")
        assertNull(storage.availableSpace)
    }
    @Test fun testUsagePercentage() {
        val storage = NetworkStorage(id = "1", name = "T", type = StorageType.FTP, location = "ftp://t", totalSpace = 1000, usedSpace = 250)
        assertEquals(0.25, storage.usagePercentage)
    }
    @Test fun testIsFull() {
        val storage = NetworkStorage(id = "1", name = "T", type = StorageType.FTP, location = "ftp://t", totalSpace = 1000, usedSpace = 1000)
        assertTrue(storage.isFull)
    }
    @Test fun testIsLowOnSpace() {
        val storage = NetworkStorage(id = "1", name = "T", type = StorageType.FTP, location = "ftp://t", totalSpace = 1000, usedSpace = 950)
        assertTrue(storage.isLowOnSpace)
    }
    @Test fun testNotLowOnSpace() {
        val storage = NetworkStorage(id = "1", name = "T", type = StorageType.FTP, location = "ftp://t", totalSpace = 1000, usedSpace = 500)
        assertFalse(storage.isLowOnSpace)
    }
    @Test fun testSupportsExtension() {
        val storage = NetworkStorage(id = "1", name = "T", type = StorageType.FTP, location = "ftp://t", supportedExtensions = listOf("txt", "md"))
        assertTrue(storage.supportsExtension("txt"))
        assertTrue(storage.supportsExtension("TXT"))
        assertFalse(storage.supportsExtension("pdf"))
    }
    @Test fun testSupportsAllExtensions() {
        val storage = NetworkStorage(id = "1", name = "T", type = StorageType.FTP, location = "ftp://t")
        assertTrue(storage.supportsExtension("anything"))
    }
    @Test fun testWithOnlineStatus() {
        val storage = NetworkStorage(id = "1", name = "T", type = StorageType.FTP, location = "ftp://t")
        assertTrue(storage.withOnlineStatus(true).isOnline)
    }
    @Test fun testWithSpaceInfo() {
        val storage = NetworkStorage(id = "1", name = "T", type = StorageType.FTP, location = "ftp://t")
        val updated = storage.withSpaceInfo(2000, 500)
        assertEquals(2000L, updated.totalSpace)
        assertEquals(500L, updated.usedSpace)
    }
}

class NetworkDocumentTest {
    @Test fun testCreation() {
        val doc = NetworkDocument(id = "d1", name = "test.txt", path = "/docs/test.txt", size = 1024)
        assertEquals("d1", doc.id)
        assertEquals("test.txt", doc.name)
        assertEquals("txt", doc.extension)
        assertEquals("/docs", doc.parentPath)
        assertFalse(doc.isFolder)
    }
    @Test fun testFolder() {
        val doc = NetworkDocument(id = "d1", name = "docs", path = "/docs", isFolder = true)
        assertTrue(doc.isFolder)
        assertEquals("—", doc.formattedSize)
    }
    @Test fun testSyncStatusFlags() {
        val syncing = NetworkDocument(id = "d1", name = "t.txt", syncStatus = SyncStatus.SYNCING)
        assertTrue(syncing.isSyncing)
        assertFalse(syncing.hasPendingChanges)

        val pending = NetworkDocument(id = "d2", name = "t.txt", syncStatus = SyncStatus.PENDING_UPLOAD)
        assertTrue(pending.hasPendingChanges)

        val synced = NetworkDocument(id = "d3", name = "t.txt", syncStatus = SyncStatus.SYNCED)
        assertTrue(synced.isAvailableOffline)
    }
    @Test fun testTextFile() {
        val doc = NetworkDocument(id = "d1", name = "readme.md")
        assertTrue(doc.isTextFile)
        assertTrue(doc.isPreviewable)
        assertTrue(doc.isEditable)
    }
    @Test fun testImageFile() {
        val doc = NetworkDocument(id = "d1", name = "photo.jpg")
        assertTrue(doc.isImageFile)
        assertTrue(doc.isPreviewable)
        assertFalse(doc.isEditable)
    }
    @Test fun testPdfFile() {
        val doc = NetworkDocument(id = "d1", name = "report.pdf")
        assertTrue(doc.isPdfFile)
        assertTrue(doc.isPreviewable)
    }
    @Test fun testReadOnly() {
        val doc = NetworkDocument(id = "d1", name = "readme.md", isReadOnly = true)
        assertTrue(doc.isTextFile)
        assertFalse(doc.isEditable)
    }
    @Test fun testFormattedSize() {
        assertEquals("512B", NetworkDocument(id = "1", name = "a", size = 512).formattedSize)
        assertEquals("1KB", NetworkDocument(id = "1", name = "a", size = 1024).formattedSize)
        assertEquals("1MB", NetworkDocument(id = "1", name = "a", size = 1024 * 1024).formattedSize)
        assertEquals("1GB", NetworkDocument(id = "1", name = "a", size = 1024L * 1024 * 1024).formattedSize)
    }
    @Test fun testIsInPath() {
        val doc = NetworkDocument(id = "d1", name = "test.txt", path = "/docs/sub/test.txt")
        assertTrue(doc.isInPath("/docs"))
        assertTrue(doc.isInPath("/docs/sub"))
        assertFalse(doc.isInPath("/other"))
    }
    @Test fun testIsDirectChildOf() {
        val doc = NetworkDocument(id = "d1", name = "test.txt", path = "/docs/test.txt")
        assertTrue(doc.isDirectChildOf("/docs"))
        assertFalse(doc.isDirectChildOf("/docs/sub"))
    }
    @Test fun testWithSyncStatus() {
        val doc = NetworkDocument(id = "d1", name = "t.txt")
        assertEquals(SyncStatus.SYNCED, doc.withSyncStatus(SyncStatus.SYNCED).syncStatus)
    }
    @Test fun testWithDocumentId() {
        val doc = NetworkDocument(id = "d1", name = "t.txt")
        assertEquals("local1", doc.withDocumentId("local1").documentId)
    }
    @Test fun testWithMetadata() {
        val doc = NetworkDocument(id = "d1", name = "t.txt")
        assertEquals("v", doc.withMetadata(mapOf("k" to "v")).metadata["k"])
    }
    @Test fun testWithPermissions() {
        val doc = NetworkDocument(id = "d1", name = "t.txt")
        val perms = setOf(DocumentPermission.READ, DocumentPermission.WRITE)
        assertEquals(perms, doc.withPermissions(perms).permissions)
    }
}

class CacheEntryTest {
    @Test fun testCreation() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/cache/t.txt",
            remotePath = "/t.txt", size = 1024, createdAt = 1000, lastAccessed = 2000, lastModified = 1500
        )
        assertEquals("c1", entry.id)
        assertTrue(entry.isValid)
        assertFalse(entry.isPinned)
        assertFalse(entry.isInUse)
        assertEquals(0, entry.accessCount)
    }
    @Test fun testIsExpired() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/c/t", remotePath = "/t",
            size = 100, createdAt = 1000, lastAccessed = 2000, lastModified = 1500, isValid = false
        )
        assertTrue(entry.isExpired)
    }
    @Test fun testCanBeEvicted() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/c/t", remotePath = "/t",
            size = 100, createdAt = 1000, lastAccessed = 2000, lastModified = 1500
        )
        assertTrue(entry.canBeEvicted)
    }
    @Test fun testCannotEvictPinned() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/c/t", remotePath = "/t",
            size = 100, createdAt = 1000, lastAccessed = 2000, lastModified = 1500, isPinned = true
        )
        assertFalse(entry.canBeEvicted)
    }
    @Test fun testCannotEvictInUse() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/c/t", remotePath = "/t",
            size = 100, createdAt = 1000, lastAccessed = 2000, lastModified = 1500, isInUse = true
        )
        assertFalse(entry.canBeEvicted)
    }
    @Test fun testCompressionRatio() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/c/t", remotePath = "/t",
            size = 50, createdAt = 1000, lastAccessed = 2000, lastModified = 1500,
            compression = "gzip", originalSize = 100
        )
        assertEquals(0.5, entry.compressionRatio)
    }
    @Test fun testCompressionRatioNull() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/c/t", remotePath = "/t",
            size = 100, createdAt = 1000, lastAccessed = 2000, lastModified = 1500
        )
        assertNull(entry.compressionRatio)
    }
    @Test fun testWithAccess() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/c/t", remotePath = "/t",
            size = 100, createdAt = 1000, lastAccessed = 2000, lastModified = 1500
        )
        val accessed = entry.withAccess(3000)
        assertEquals(3000L, accessed.lastAccessed)
        assertEquals(1, accessed.accessCount)
        assertTrue(accessed.isInUse)
    }
    @Test fun testWithNotInUse() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/c/t", remotePath = "/t",
            size = 100, createdAt = 1000, lastAccessed = 2000, lastModified = 1500, isInUse = true
        )
        assertFalse(entry.withNotInUse().isInUse)
    }
    @Test fun testWithExpiration() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/c/t", remotePath = "/t",
            size = 100, createdAt = 1000, lastAccessed = 2000, lastModified = 1500
        )
        val expired = entry.withExpiration(500, 1000)
        assertFalse(expired.isValid)
    }
    @Test fun testWithPinStatus() {
        val entry = CacheEntry(
            id = "c1", remoteDocumentId = "d1", localPath = "/c/t", remotePath = "/t",
            size = 100, createdAt = 1000, lastAccessed = 2000, lastModified = 1500
        )
        assertTrue(entry.withPinStatus(true).isPinned)
    }
}

class NetworkOperationTest {
    @Test fun testCreation() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/test.txt", createdAt = 1000)
        assertEquals(1L, op.id)
        assertEquals(OperationType.UPLOAD, op.type)
        assertEquals(OperationStatus.PENDING, op.status)
        assertTrue(op.isPending)
        assertFalse(op.isRunning)
    }
    @Test fun testIsRunning() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000, status = OperationStatus.IN_PROGRESS)
        assertTrue(op.isRunning)
    }
    @Test fun testIsPaused() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000, status = OperationStatus.IN_PROGRESS, isPaused = true)
        assertFalse(op.isRunning)
    }
    @Test fun testProgressPercentage() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000, progress = 0.75)
        assertEquals(75, op.progressPercentage)
    }
    @Test fun testCanRetry() {
        val failed = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000, status = OperationStatus.FAILED, retryCount = 1, maxRetries = 3)
        assertTrue(failed.canRetry)
    }
    @Test fun testCannotRetryExhausted() {
        val failed = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000, status = OperationStatus.FAILED, retryCount = 3, maxRetries = 3)
        assertFalse(failed.canRetry)
    }
    @Test fun testDuration() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000, startedAt = 2000, completedAt = 5000)
        assertEquals(3000L, op.duration)
    }
    @Test fun testDurationNull() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000)
        assertNull(op.duration)
    }
    @Test fun testWithStatus() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000)
        val started = op.withStatus(OperationStatus.IN_PROGRESS, nowMillis = 2000)
        assertEquals(OperationStatus.IN_PROGRESS, started.status)
        assertEquals(2000L, started.startedAt)
    }
    @Test fun testWithProgress() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000, totalSize = 1000)
        val updated = op.withProgress(0.5)
        assertEquals(0.5, updated.progress)
        assertEquals(500L, updated.bytesTransferred)
    }
    @Test fun testWithProgressClamped() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000)
        assertEquals(1.0, op.withProgress(1.5).progress)
        assertEquals(0.0, op.withProgress(-0.5).progress)
    }
    @Test fun testWithError() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000)
        val errored = op.withError("fail", 3000)
        assertEquals(OperationStatus.FAILED, errored.status)
        assertEquals("fail", errored.error)
        assertEquals(1, errored.retryCount)
        assertEquals(3000L, errored.completedAt)
    }
    @Test fun testWithPauseStatus() {
        val op = NetworkOperation(id = 1, type = OperationType.UPLOAD, remotePath = "/t", createdAt = 1000)
        assertTrue(op.withPauseStatus(true).isPaused)
    }
}
