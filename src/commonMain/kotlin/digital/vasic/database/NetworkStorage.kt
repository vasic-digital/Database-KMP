package digital.vasic.database

/**
 * Represents a network storage location with metadata and status.
 */
data class NetworkStorage(
    val id: String,
    val name: String,
    val type: StorageType,
    val location: String,
    val totalSpace: Long? = null,
    val usedSpace: Long? = null,
    val isOnline: Boolean = false,
    val lastSync: Long? = null,
    val metadata: Map<String, String> = emptyMap(),
    val isEnabled: Boolean = true,
    val priority: Int = 100,
    val supportsFolders: Boolean = true,
    val supportsMetadata: Boolean = true,
    val maxFileSize: Long? = null,
    val supportedExtensions: List<String> = emptyList()
) {
    val availableSpace: Long?
        get() = if (totalSpace != null && usedSpace != null) totalSpace - usedSpace else null

    val usagePercentage: Double?
        get() = if (totalSpace != null && usedSpace != null && totalSpace > 0) {
            usedSpace.toDouble() / totalSpace.toDouble()
        } else null

    val isFull: Boolean get() = availableSpace == 0L

    val isLowOnSpace: Boolean get() = usagePercentage?.let { it > 0.9 } ?: false

    fun supportsExtension(extension: String): Boolean {
        return supportedExtensions.isEmpty() ||
            supportedExtensions.any { it.equals(extension, ignoreCase = true) }
    }

    fun withOnlineStatus(isOnline: Boolean) = copy(isOnline = isOnline)
    fun withSpaceInfo(totalSpace: Long?, usedSpace: Long?) = copy(totalSpace = totalSpace, usedSpace = usedSpace)
}
