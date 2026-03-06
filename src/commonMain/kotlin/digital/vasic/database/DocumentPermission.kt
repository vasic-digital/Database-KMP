package digital.vasic.database

/**
 * Permissions that can be granted on documents and folders.
 */
enum class DocumentPermission {
    READ, WRITE, DELETE, CREATE, RENAME, MOVE, COPY, SHARE,
    MANAGE_PERMISSIONS, VIEW_METADATA, MODIFY_METADATA,
    EXECUTE, DOWNLOAD, UPLOAD, SYNC, ADMIN;

    val isReadPermission: Boolean get() = this in setOf(READ, VIEW_METADATA, DOWNLOAD)
    val isWritePermission: Boolean get() = this in setOf(WRITE, CREATE, RENAME, MOVE, COPY, UPLOAD, MODIFY_METADATA)
    val isAdministrativePermission: Boolean get() = this in setOf(DELETE, SHARE, MANAGE_PERMISSIONS, EXECUTE, SYNC, ADMIN)

    val displayName: String get() = when (this) {
        READ -> "Read"; WRITE -> "Write"; DELETE -> "Delete"; CREATE -> "Create"
        RENAME -> "Rename"; MOVE -> "Move"; COPY -> "Copy"; SHARE -> "Share"
        MANAGE_PERMISSIONS -> "Manage Permissions"; VIEW_METADATA -> "View Metadata"
        MODIFY_METADATA -> "Modify Metadata"; EXECUTE -> "Execute"
        DOWNLOAD -> "Download"; UPLOAD -> "Upload"; SYNC -> "Sync"; ADMIN -> "Administrator"
    }

    companion object {
        val DEFAULT_FOLDER_PERMISSIONS = setOf(READ, WRITE, CREATE, RENAME, MOVE, COPY, DELETE, UPLOAD, DOWNLOAD, SYNC)
        val DEFAULT_DOCUMENT_PERMISSIONS = setOf(READ, WRITE, RENAME, MOVE, COPY, DELETE, DOWNLOAD, SYNC)
        val READ_ONLY_PERMISSIONS = setOf(READ, VIEW_METADATA, DOWNLOAD)

        fun hasPermission(permissions: Set<DocumentPermission>, permission: DocumentPermission): Boolean {
            return permissions.contains(permission) || permissions.contains(ADMIN)
        }
    }
}
