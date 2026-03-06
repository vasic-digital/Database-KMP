package digital.vasic.database

/**
 * Represents a network operation (upload, download, delete, etc.).
 */
data class NetworkOperation(
    val id: Long,
    val type: OperationType,
    val remotePath: String,
    val localPath: String? = null,
    val status: OperationStatus = OperationStatus.PENDING,
    val progress: Double = 0.0,
    val totalSize: Long = 0L,
    val bytesTransferred: Long = 0L,
    val createdAt: Long,
    val startedAt: Long? = null,
    val completedAt: Long? = null,
    val error: String? = null,
    val retryCount: Int = 0,
    val maxRetries: Int = 3,
    val priority: Int = 100,
    val canPause: Boolean = true,
    val canCancel: Boolean = true,
    val isPaused: Boolean = false,
    val estimatedTimeRemaining: Long? = null,
    val transferSpeed: Long? = null,
    val metadata: Map<String, String> = emptyMap()
) {
    val isRunning: Boolean get() = status == OperationStatus.IN_PROGRESS && !isPaused
    val isCompleted: Boolean get() = status == OperationStatus.COMPLETED
    val hasFailed: Boolean get() = status == OperationStatus.FAILED
    val canRetry: Boolean get() = hasFailed && retryCount < maxRetries
    val isPending: Boolean get() = status == OperationStatus.PENDING
    val progressPercentage: Int get() = (progress * 100).toInt()

    val duration: Long?
        get() = when {
            completedAt != null && startedAt != null -> completedAt - startedAt
            else -> null
        }

    fun withStatus(status: OperationStatus, error: String? = null, nowMillis: Long) = copy(
        status = status,
        error = error,
        startedAt = if (status == OperationStatus.IN_PROGRESS && startedAt == null) nowMillis else startedAt,
        completedAt = if (status == OperationStatus.COMPLETED || status == OperationStatus.FAILED) nowMillis else completedAt
    )

    fun withProgress(
        progress: Double,
        bytesTransferred: Long? = null,
        transferSpeed: Long? = null,
        estimatedTimeRemaining: Long? = null
    ) = copy(
        progress = progress.coerceIn(0.0, 1.0),
        bytesTransferred = bytesTransferred ?: (totalSize * progress).toLong(),
        transferSpeed = transferSpeed,
        estimatedTimeRemaining = estimatedTimeRemaining
    )

    fun withError(error: String, nowMillis: Long) = copy(
        status = OperationStatus.FAILED,
        error = error,
        retryCount = retryCount + 1,
        completedAt = nowMillis
    )

    fun withPauseStatus(isPaused: Boolean) = copy(isPaused = isPaused)
    fun withPriority(priority: Int) = copy(priority = priority)
}
