package digital.vasic.database

/**
 * Represents a document or folder stored on a network storage location.
 */
data class NetworkDocument(
    val id: String,
    val name: String,
    val path: String = "",
    val isFolder: Boolean = false,
    val size: Long = 0L,
    val lastModified: Long? = null,
    val syncStatus: SyncStatus = SyncStatus.UNKNOWN,
    val documentId: String? = null,
    val contentType: String? = null,
    val extension: String = name.substringAfterLast('.', "").ifEmpty { "" },
    val parentPath: String = path.substringBeforeLast('/', "").ifEmpty { "/" },
    val isReadOnly: Boolean = false,
    val isHidden: Boolean = false,
    val metadata: Map<String, String> = emptyMap(),
    val tags: List<String> = emptyList(),
    val owner: String? = null,
    val permissions: Set<DocumentPermission> = emptySet(),
    val storageId: String = "",
    val author: String? = null
) {
    val isSyncing: Boolean get() = syncStatus == SyncStatus.SYNCING
    val hasPendingChanges: Boolean get() = syncStatus == SyncStatus.PENDING_UPLOAD
    val isAvailableOffline: Boolean get() = syncStatus == SyncStatus.SYNCED

    val isTextFile: Boolean get() = !isFolder && TEXT_EXTENSIONS.contains(extension.lowercase())
    val isImageFile: Boolean get() = !isFolder && IMAGE_EXTENSIONS.contains(extension.lowercase())
    val isPdfFile: Boolean get() = !isFolder && extension.lowercase() == "pdf"
    val isPreviewable: Boolean get() = isTextFile || isImageFile || isPdfFile
    val isEditable: Boolean get() = isTextFile && !isReadOnly

    val formattedSize: String get() = when {
        isFolder -> "—"
        size < 1024 -> "${size}B"
        size < 1024 * 1024 -> "${size / 1024}KB"
        size < 1024 * 1024 * 1024 -> "${size / (1024 * 1024)}MB"
        else -> "${size / (1024 * 1024 * 1024)}GB"
    }

    fun isInPath(path: String): Boolean {
        val normalizedPath = path.trimEnd('/')
        val normalizedDocPath = this.path.trimEnd('/')
        return normalizedDocPath.startsWith("$normalizedPath/")
    }

    fun isDirectChildOf(path: String): Boolean {
        val normalizedPath = path.trimEnd('/')
        val normalizedDocPath = this.path.trimEnd('/')
        return normalizedDocPath.substringBeforeLast('/', "") == normalizedPath
    }

    fun withSyncStatus(syncStatus: SyncStatus) = copy(syncStatus = syncStatus)
    fun withDocumentId(documentId: String?) = copy(documentId = documentId)
    fun withMetadata(metadata: Map<String, String>) = copy(metadata = metadata)
    fun withPermissions(permissions: Set<DocumentPermission>) = copy(permissions = permissions)

    companion object {
        private val TEXT_EXTENSIONS = setOf(
            "txt", "md", "markdown", "rst", "adoc", "org", "wiki", "tex",
            "json", "xml", "yaml", "yml", "toml", "ini", "cfg", "conf",
            "csv", "tsv", "log", "sql", "sh", "bat", "ps1", "py", "js",
            "ts", "kt", "java", "cpp", "c", "h", "hpp", "go", "rs",
            "php", "rb", "swift", "dart", "scala", "clj", "hs", "ml"
        )

        private val IMAGE_EXTENSIONS = setOf(
            "jpg", "jpeg", "png", "gif", "bmp", "webp", "svg", "ico",
            "tiff", "tif", "psd", "raw", "cr2", "nef", "arw"
        )
    }
}
