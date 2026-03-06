package digital.vasic.database

/**
 * Synchronization status of a document or storage.
 */
enum class SyncStatus {
    UNKNOWN,
    SYNCED,
    PENDING_UPLOAD,
    PENDING_DOWNLOAD,
    SYNCING,
    SYNC_ERROR,
    NOT_SYNCED,
    QUEUED,
    CONFLICT,
    UPLOADING,
    DOWNLOADING
}

/**
 * Conflict resolution strategies.
 */
enum class ConflictResolution {
    LOCAL_WINS,
    REMOTE_WINS,
    KEEP_BOTH,
    MANUAL,
    SKIP
}
