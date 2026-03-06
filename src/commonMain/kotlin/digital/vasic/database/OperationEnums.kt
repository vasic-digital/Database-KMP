package digital.vasic.database

/**
 * Types of network operations.
 */
enum class OperationType {
    UPLOAD, DOWNLOAD, DELETE, CREATE_FOLDER, RENAME, MOVE, COPY, SYNC, SEARCH
}

/**
 * Status of network operations.
 */
enum class OperationStatus {
    PENDING, IN_PROGRESS, COMPLETED, FAILED, PAUSED, CANCELLED
}

/**
 * Types of documents supported by network storage.
 */
enum class DocumentType {
    FILE, FOLDER, UNKNOWN
}
