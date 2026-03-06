package digital.vasic.database

/**
 * Storage protocol types for network storage.
 */
enum class StorageType {
    WEBDAV, FTP, SFTP, SMB, GOOGLE_DRIVE, DROPBOX, ONEDRIVE, GIT;

    val displayName: String get() = when (this) {
        WEBDAV -> "WebDAV"; FTP -> "FTP"; SFTP -> "SFTP"; SMB -> "SMB/CIFS"
        GOOGLE_DRIVE -> "Google Drive"; DROPBOX -> "Dropbox"; ONEDRIVE -> "OneDrive"; GIT -> "Git"
    }
}
