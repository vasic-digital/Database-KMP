package digital.vasic.database

/**
 * Represents a cached file entry in the local cache.
 */
data class CacheEntry(
    val id: String,
    val remoteDocumentId: String,
    val localPath: String,
    val remotePath: String,
    val size: Long,
    val createdAt: Long,
    val lastAccessed: Long,
    val lastModified: Long,
    val expiresAt: Long? = null,
    val isValid: Boolean = true,
    val isPinned: Boolean = false,
    val isInUse: Boolean = false,
    val accessCount: Int = 0,
    val contentType: String? = null,
    val checksum: String? = null,
    val compression: String? = null,
    val originalSize: Long? = null,
    val priority: Int = 100,
    val metadata: Map<String, String> = emptyMap(),
    val tags: List<String> = emptyList()
) {
    val isExpired: Boolean get() = !isValid

    val canBeEvicted: Boolean get() = !isPinned && !isInUse

    val compressionRatio: Double?
        get() = if (compression != null && originalSize != null && size > 0) {
            size.toDouble() / originalSize.toDouble()
        } else null

    fun withAccess(nowMillis: Long) = copy(
        lastAccessed = nowMillis,
        accessCount = accessCount + 1,
        isInUse = true
    )

    fun withNotInUse() = copy(isInUse = false)

    fun withExpiration(expiresAt: Long?, nowMillis: Long) = copy(
        expiresAt = expiresAt,
        isValid = expiresAt?.let { it > nowMillis } ?: true
    )

    fun withContent(size: Long, checksum: String?, lastModified: Long) = copy(
        size = size,
        checksum = checksum,
        lastModified = lastModified
    )

    fun withPinStatus(isPinned: Boolean) = copy(isPinned = isPinned)
    fun withPriority(priority: Int) = copy(priority = priority)
}
